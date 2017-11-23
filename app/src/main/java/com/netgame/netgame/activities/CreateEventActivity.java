package com.netgame.netgame.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.netgame.netgame.R;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.Game;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import static com.netgame.netgame.network.NetGameApiService.CREATE_EVENT_URL;

public class CreateEventActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private FloatingActionButton createEventFloatingActionButton;

    private ImageButton dateEventImageButton;
    private ImageButton dateStartImageButton;
    private ImageButton dateEndImageButton;

    private EditText dateEventEditText;
    private EditText dateStartEditText;
    private EditText dateEndEditText;

    private EditText titleEditText;
    private EditText descriptionEditText;

    DatePickerDialog datePickerDialog;

    private int control;

    private Gson gson;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBackToolbar();

        gson = new Gson();
        game = Game.from(getIntent().getExtras());

        setupDatePickerDialog();

        createEventFloatingActionButton = findViewById(R.id.createEventFloatingActionButton);
        createEventFloatingActionButton.setOnClickListener(this);

        dateEventImageButton = findViewById(R.id.dateEventImageButton);
        dateStartImageButton = findViewById(R.id.dateStartImageButton);
        dateEndImageButton = findViewById(R.id.dateEndImageButton);

        dateEventEditText = findViewById(R.id.dateEventEditText);
        dateStartEditText = findViewById(R.id.dateStartEditText);
        dateEndEditText = findViewById(R.id.dateEndEditText);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);

        dateEventImageButton.setOnClickListener(this);
        dateStartImageButton.setOnClickListener(this);
        dateEndImageButton.setOnClickListener(this);
    }

    private void setupDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_event;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK, new Intent(getApplicationContext(), PublicationActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        switch (control) {
            case 0:
                dateEventEditText.setText(String.format("%s-%s-%s", String.valueOf(i), String.valueOf(i1), String.valueOf(i2)));
                break;
            case 1:
                dateStartEditText.setText(String.format("%s-%s-%s", String.valueOf(i), String.valueOf(i1), String.valueOf(i2)));
                break;
            case 2:
                dateEndEditText.setText(String.format("%s-%s-%s", String.valueOf(i), String.valueOf(i1), String.valueOf(i2)));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dateEventImageButton:
                control = 0;
                datePickerDialog.show();
                break;
            case R.id.dateStartImageButton:
                control = 1;
                datePickerDialog.show();
                break;
            case R.id.dateEndImageButton:
                control = 2;
                datePickerDialog.show();
                break;
            case R.id.createEventFloatingActionButton:
                validateCreate();
                break;
        }
    }

    private void validateCreate (){
        if (titleEditText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Ingrese un titulo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (descriptionEditText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Ingrese una descripcion", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateEventEditText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Ingrese una fecha del evento", Toast.LENGTH_SHORT).show();
            return;
        }

        if(dateStartEditText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Ingrese una fecha de inicio de inscripcion", Toast.LENGTH_SHORT).show();
            return;
        }

        if(dateEndEditText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Ingrese una fecha de fin de inscripcion", Toast.LENGTH_SHORT).show();
            return;
        }

        createEvent();
    }

    private void createEvent (){
        showProgressDialog();
        AndroidNetworking
                .post(CREATE_EVENT_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(this, "token", ""))
                .addBodyParameter("title", titleEditText.getText().toString())
                .addBodyParameter("description", descriptionEditText.getText().toString())
                .addBodyParameter("dateEvent", dateEventEditText.getText().toString())
                .addBodyParameter("dateStart",dateStartEditText.getText().toString())
                .addBodyParameter("dateEnd",dateEndEditText.getText().toString())
                .addBodyParameter("idGame",String.valueOf(game.getId()))
                .setTag(tag)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissProgressDialog();
                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (responseObject.getStatusBody().getCode().equals("0")){
                            // Toast.makeText(getApplicationContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK, new Intent(getApplicationContext(), PublicationActivity.class));
                            finish();
                        }else{
                            Log.d(tag, responseObject.getStatusBody().getMessage());
                            // Toast.makeText(getApplicationContext(), responseObject.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissProgressDialog();
                        Log.d(tag, anError.getMessage());
                    }
                });
    }
}
