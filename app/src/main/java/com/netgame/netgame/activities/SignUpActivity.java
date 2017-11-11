package com.netgame.netgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.netgame.netgame.R;
import com.netgame.netgame.models.Base;

import org.json.JSONObject;

import static com.netgame.netgame.network.NetGameApiService.CREATE_USER_URL;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;

    private String tag;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addToolbar();

        tag = getResources().getString(R.string.app_name);
        gson = new Gson();

        userNameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK, new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validateInputs() {
        if (userNameEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Ingrese el nombre del usuario", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Ingrese el contraseña del usuario", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
            Toast.makeText(this, "Las contraseñas ingresadas son diferentes", Toast.LENGTH_SHORT).show();
            return;
        }

        createUser();
    }

    private void createUser() {
        AndroidNetworking
                .post(CREATE_USER_URL)
                .addHeaders("token", getResources().getString(R.string.token))
                .addBodyParameter("userName", userNameEditText.getText().toString())
                .addBodyParameter("password", passwordEditText.getText().toString())
                .addBodyParameter("userType", "1")
                .setTag(tag)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (responseObject.getStatusBody().getCode().equals("0")){
                            Toast.makeText(getApplicationContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK, new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), responseObject.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(tag, anError.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        validateInputs();
    }
}
