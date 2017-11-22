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
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.netgame.netgame.R;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Base;
import com.netgame.netgame.models.Game;

import org.json.JSONObject;

import static com.netgame.netgame.network.NetGameApiService.CREATE_PUBLICATION_URL;

public class CreatePublicationActivity extends BaseActivity implements View.OnClickListener {

    private EditText titleEditText;
    private EditText descriptionEditText;

    private FloatingActionButton createFloatingActionButton;

    private String tag;
    private Gson gson;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addBackToolbar();

        tag = getResources().getString(R.string.app_name);
        gson = new Gson();

        game = Game.from(getIntent().getExtras());

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        createFloatingActionButton = findViewById(R.id.createFloatingActionButton);
        createFloatingActionButton.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED, new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createPost (){
        showProgressDialog();
        AndroidNetworking
                .post(CREATE_PUBLICATION_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(this, "token", ""))
                .addBodyParameter("Title", titleEditText.getText().toString())
                .addBodyParameter("Description", descriptionEditText.getText().toString())
                .addBodyParameter("idGame", String.valueOf(game.getId()))
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

    private void validateCreate(){
        if (titleEditText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Ingrese un titulo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (descriptionEditText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Ingrese una descripcion", Toast.LENGTH_SHORT).show();
            return;
        }
        createPost();
    }

    @Override
    public void onClick(View view) {
        validateCreate();
    }
}
