package com.netgame.netgame.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.netgame.netgame.R;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.UserCabin;

import org.json.JSONObject;

import static com.netgame.netgame.network.NetGameApiService.PUT_LOCATION_CABIN_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationCabinFragment extends Fragment implements OnMapReadyCallback {

    private UserCabin userCabin;

    private GoogleMap mMap;
    private MapView mapView;
    private GoogleApiClient googleApiClient;
    private MarkerOptions marker;
    private String tag;
    Gson gson;
    private ProgressDialog progressDialog;

    public static LocationCabinFragment newInstance(UserCabin userCabin) {
        Bundle bundle = userCabin.toBundle();
        LocationCabinFragment fragment = new LocationCabinFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View context = inflater.inflate(R.layout.fragment_location_cabin, container, false);
        gson = new Gson();
        userCabin = UserCabin.from(getArguments());
        tag = getResources().getString(R.string.app_name);
        createProgressDialog();
        return context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.map);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mMap = googleMap;

        marker = new MarkerOptions()
                .position(new LatLng(userCabin.getLatitude(), userCabin.getLongitude()))
                .title("Posicion")
                .draggable(true);

        mMap.addMarker(marker);

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                // Toast.makeText(getActivity(), "onMarkerDragStart latitude: " + String.valueOf(marker.getPosition().latitude), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // Toast.makeText(getActivity(), "onMarkerDrag", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // Toast.makeText(getActivity(), "onMarkerDragEnd latitude: " + String.valueOf(marker.getPosition().latitude), Toast.LENGTH_SHORT).show();
                userCabin.setLatitude(marker.getPosition().latitude);
                userCabin.setLongitude(marker.getPosition().longitude);
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));


    }

    private void createProgressDialog(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void putLocationCabin() {
        progressDialog.show();
        AndroidNetworking
                .put(PUT_LOCATION_CABIN_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(getActivity(), "token", ""))
                .addBodyParameter("latitude", String.valueOf(userCabin.getLatitude()))
                .addBodyParameter("longitude", String.valueOf(userCabin.getLongitude()))
                .setTag(tag)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (responseObject.getStatusBody().getCode().equals("0")) {
                            Toast.makeText(getActivity(), responseObject.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(tag, responseObject.getStatusBody().getMessage());
                            // Toast.makeText(getApplicationContext(), responseObject.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.d(tag, anError.getMessage());
                    }
                });
    }

    /*
    */

}
