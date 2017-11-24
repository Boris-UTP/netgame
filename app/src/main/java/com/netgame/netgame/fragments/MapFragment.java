package com.netgame.netgame.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netgame.netgame.R;
import com.netgame.netgame.activities.MainActivity;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.UserCabin;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.netgame.netgame.network.NetGameApiService.CABINS_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final int REQUEST_CHECK_SETTINGS = 1000;
    private static final int PERMISSIONS_ACCESS_LOCATION_TASK = 1001;

    private GoogleMap mMap;
    private MapView mapView;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    CameraUpdate cameraUpdate;

    private String tag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* userCabins = new ArrayList<>();
        userCabins.add(new UserCabin("Union Gaming", -12.064771, -77.037831));
        userCabins.add(new UserCabin("Gaming Factory", -12.066670, -77.035835));
        userCabins.add(new UserCabin("LAN Center", -12.064939, -77.033861));
        userCabins.add(new UserCabin("Internet Amistad", -12.063554, -77.033743));
        userCabins.add(new UserCabin("Cainatrix", -12.065694, -77.036618));*/


        View context = inflater.inflate(R.layout.fragment_map, container, false);

        PreferencesEditor.savePreference(getActivity(),"change_location_zoom", true);
        tag = getResources().getString(R.string.app_name);

        return context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.map);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        if (!googleApiClient.isConnected())
            googleApiClient.connect();

        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        getCabins();
    }

    private void getCabins() {
        AndroidNetworking
                .get(CABINS_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(getActivity(), "token", ""))
                .setTag(tag)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Base<List<UserCabin>> userCabins = gson.fromJson(response.toString(), new TypeToken<Base<List<UserCabin>>>() {
                        }.getType());
                        if (userCabins.getStatusBody().getCode().equalsIgnoreCase("0")) {

                            for (int i = 0; i < userCabins.getData().size(); i++) {

                                UserCabin userCabin = userCabins.getData().get(i);
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(userCabin.getLatitude(), userCabin.getLongitude()))
                                        .title(userCabin.getName())
                                );

                            }

                        } else {
                            Toast.makeText(getActivity(), userCabins.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showQuestionLocation() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.setAlwaysShow(true);
        builder.addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                Status status = result.getStatus();
                //final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    getActivity(),
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (validatePermissionLocation()) {
            if (validateEnableGps()) {
                updateLocationRequest();
            } else {
                locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setNumUpdates(1);
                showQuestionLocation();
            }
        }

    }

    @SuppressLint("MissingPermission")
    private void updateLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000)
                .setFastestInterval(500)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private boolean validateEnableGps() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return true;
        else
            return false;
    }

    private boolean validatePermissionLocation() {
        boolean status = true;
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Solicitar permisos de Localización",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSIONS_ACCESS_LOCATION_TASK);
                            }
                        })
                        .show();
                status = false;
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_ACCESS_LOCATION_TASK);
                status = false;
            }
        }
        return status;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            if (PreferencesEditor.getBooleanPreference(getActivity(), "change_location_zoom", true)){
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
                mMap.animateCamera(cameraUpdate);
                PreferencesEditor.savePreference(getActivity(),"change_location_zoom", false);
            }

        }
    }
}
