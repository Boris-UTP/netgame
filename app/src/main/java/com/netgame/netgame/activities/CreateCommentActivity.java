package com.netgame.netgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.netgame.netgame.models.Publication;

import org.json.JSONObject;

import static com.netgame.netgame.network.NetGameApiService.CREATE_COMMENT_URL;

public class CreateCommentActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton addCommentFloatingActionButton;
    private EditText descriptionEditText;
    private Publication publication;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBackToolbar();

        gson = new Gson();
        publication = Publication.from(getIntent().getExtras());
        addCommentFloatingActionButton = findViewById(R.id.addCommentFloatingActionButton);

        addCommentFloatingActionButton.setOnClickListener(this);

        descriptionEditText = findViewById(R.id.descriptionEditText);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_comment;
    }


    @Override
    public void onClick(View view) {
        if (descriptionEditText.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Ingrese un comentario", Toast.LENGTH_SHORT).show();
            return;
        }
        addComment();
    }

    private void addComment() {
        showProgressDialog();
        AndroidNetworking
                .post(CREATE_COMMENT_URL)
                .addHeaders("token", PreferencesEditor.getStringPreference(this, "token", ""))
                .addBodyParameter("description", descriptionEditText.getText().toString())
                .addBodyParameter("idPublication", String.valueOf(publication.getId()))
                .setTag(tag)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissProgressDialog();
                        Base responseObject = gson.fromJson(response.toString(), Base.class);
                        if (responseObject.getStatusBody().getCode().equals("0")) {
                            // Toast.makeText(getApplicationContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK, new Intent(getApplicationContext(), DetailPublicationActivity.class));
                            finish();
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

}
