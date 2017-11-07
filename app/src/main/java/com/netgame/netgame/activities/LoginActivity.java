package com.netgame.netgame.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.netgame.netgame.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signInButton;
    private LinearLayout optionSignUpLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        optionSignUpLinearLayout =  (LinearLayout) findViewById(R.id.optionSignUpLinearLayout);
        optionSignUpLinearLayout.setOnClickListener(this);

        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()){
            case R.id.signInButton:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                break;
            case R.id.optionSignUpLinearLayout:
                intent = new Intent(getApplicationContext(), SignUpActivity.class);
                break;
        }

        startActivityForResult(intent, 1);
    }
}
