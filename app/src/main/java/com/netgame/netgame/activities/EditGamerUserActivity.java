package com.netgame.netgame.activities;

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
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netgame.netgame.R;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.UserGamer;

import org.json.JSONObject;

import static com.netgame.netgame.network.NetGameApiService.GAMER_URL;
import static com.netgame.netgame.network.NetGameApiService.PUT_INFO_GAMER_URL;

public class EditGamerUserActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton actionFloatingActionButton;
    private EditText nameEditText;
    private EditText lastNameEditText;
    private EditText nickNameEditText;
    private UserGamer userGamer;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBackToolbar();

        nameEditText = findViewById(R.id.nameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        nickNameEditText = findViewById(R.id.nickNameEditText);

        actionFloatingActionButton = findViewById(R.id.actionFloatingActionButton);
        actionFloatingActionButton.setOnClickListener(this);

        getInfoUserGamer();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_gamer_user;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK, new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        putInfoUserGamer();
    }

    private void setDataUserGamer() {
        nameEditText.setText(userGamer.getName());
        lastNameEditText.setText(userGamer.getLastName());
        nickNameEditText.setText(userGamer.getNickName());
    }

    private void getInfoUserGamer() {
        showProgressDialog();
        AndroidNetworking
                .get(GAMER_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(this, "token", ""))
                .setTag(tag)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Base<UserGamer> objUserGamer = gson.fromJson(response.toString(), new TypeToken<Base<UserGamer>>() {
                        }.getType());

                        if (objUserGamer.getStatusBody().getCode().equals("0")) {
                            userGamer = objUserGamer.getData();

                            setDataUserGamer();

                        } else {
                            Log.d(tag, objUserGamer.getStatusBody().getMessage());
                        }
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(tag, anError.getMessage());
                        dismissProgressDialog();
                    }
                });
    }

    private void putInfoUserGamer() {
        showProgressDialog();
        AndroidNetworking
                .put(PUT_INFO_GAMER_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(this, "token", ""))
                .addBodyParameter("name", nameEditText.getText().toString())
                .addBodyParameter("lastName", lastNameEditText.getText().toString())
                .addBodyParameter("nickName", nickNameEditText.getText().toString())
                .setTag(tag)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissProgressDialog();

                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (responseObject.getStatusBody().getCode().equals("0")) {
                            Toast.makeText(getApplicationContext(), responseObject.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
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
