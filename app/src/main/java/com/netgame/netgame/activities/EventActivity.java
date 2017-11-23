package com.netgame.netgame.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.netgame.netgame.R;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.Event;

import org.json.JSONObject;

import static com.netgame.netgame.network.NetGameApiService.ASSISTANCE_USER_URL;

public class EventActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {

    private Event event;

    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView dateEventTextView;
    private TextView inscriptionStartDateEventTextView;
    private TextView inscriptionEndDateEventTextView;

    private FloatingActionButton actionFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBackToolbar();
        event = Event.from(getIntent().getExtras());

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateEventTextView = findViewById(R.id.dateEventTextView);
        inscriptionStartDateEventTextView = findViewById(R.id.inscriptionStartDateEventTextView);
        inscriptionEndDateEventTextView = findViewById(R.id.inscriptionEndDateEventTextView);

        actionFloatingActionButton = findViewById(R.id.actionFloatingActionButton);

        actionFloatingActionButton.setOnClickListener(this);

        if (event.getFlag() == 1) {
            actionFloatingActionButton.setVisibility(View.GONE);
        } else {
            actionFloatingActionButton.setVisibility(View.VISIBLE);
        }

        titleTextView.setText(event.getTitle());
        descriptionTextView.setText(event.getDescription());
        dateEventTextView.setText(event.getDateEvent());
        inscriptionStartDateEventTextView.setText(event.getDateStart());
        inscriptionEndDateEventTextView.setText(event.getDateEnd());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event;
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng sydney = new LatLng(event.getLatitude(), event.getLongitude());

        // map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

        map.addMarker(new MarkerOptions()
                .title(event.getName())
                .snippet(event.getDescription())
                .position(sydney));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED, new Intent(getApplicationContext(), PublicationActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        String message = "¿Desea asistir al evento?";
        if (event.getAssistance() == 1) {
            message = "Dejar de asistir al evento";
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(EventActivity.this);
        alert.setMessage(message);
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (event.getAssistance() == 0) {
                    event.setAssistance(1);
                    postAssistance(event.getIdEvent(), "1");
                    Toast.makeText(getApplicationContext(), "Se registro su asistencia", Toast.LENGTH_SHORT).show();
                } else {
                    event.setAssistance(0);
                    postAssistance(event.getIdEvent(), "0");
                    Toast.makeText(getApplicationContext(), "Se registro su inasistencia", Toast.LENGTH_SHORT).show();
                }

            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void postAssistance(String idEvent, String state) {
        AndroidNetworking
                .post(ASSISTANCE_USER_URL)
                .addBodyParameter("idEvent", idEvent)
                .addBodyParameter("state", state)
                .addHeaders("token", PreferencesEditor.getStringPreference(getApplicationContext(), "token", ""))
                .setTag(getResources().getString(R.string.app_name))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (!responseObject.getStatusBody().getCode().equals("0")) {
                            Log.d(tag, responseObject.getStatusBody().getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(tag, anError.getMessage());
                    }
                });
    }
}
