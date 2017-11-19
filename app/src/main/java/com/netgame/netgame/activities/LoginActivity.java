package com.netgame.netgame.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netgame.netgame.R;
import com.netgame.netgame.commons.PreferencesEditor;
import com.netgame.netgame.models.Authenticate;
import com.netgame.netgame.models.Base;

import org.json.JSONObject;

import java.util.List;

import static com.netgame.netgame.network.NetGameApiService.AUTHENTICATE_URL;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button signInButton;
    private LinearLayout optionSignUpLinearLayout;
    private String tag;

    private EditText userEditText;
    private EditText passwordEditText;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tag = getResources().getString(R.string.app_name);
        gson = new Gson();

        optionSignUpLinearLayout = findViewById(R.id.optionSignUpLinearLayout);
        optionSignUpLinearLayout.setOnClickListener(this);

        userEditText = findViewById(R.id.userEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInButton:
                authenticate();
                break;
            case R.id.optionSignUpLinearLayout:
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    private void authenticate (){
        showProgressDialog();
        AndroidNetworking
                .post(AUTHENTICATE_URL)
                .addBodyParameter("userName",userEditText.getText().toString())
                .addBodyParameter("password",passwordEditText.getText().toString())
                .setTag(tag)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Base<Authenticate> authenticate = gson.fromJson(response.toString(), new TypeToken<Base<Authenticate>>(){}.getType());
                        dismissProgressDialog();
                        if (authenticate.getStatusBody().getCode().equalsIgnoreCase("0")){

                            PreferencesEditor.savePreference(getApplicationContext(),"token", authenticate.getData().getToken());

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), authenticate.getStatusBody().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissProgressDialog();
                        Toast.makeText(getApplicationContext(),anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
