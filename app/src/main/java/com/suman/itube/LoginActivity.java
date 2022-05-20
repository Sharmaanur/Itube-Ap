package com.suman.itube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.suman.itube.DB.UserDBHandler;
import com.suman.itube.DB.UserModel;

public class LoginActivity extends AppCompatActivity {
    UserDBHandler loginDBHandler;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginDBHandler = new UserDBHandler(this);
        username = findViewById(R.id.ed_login_username);
        password = findViewById(R.id.ed_login_password);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel =  new UserModel();
                userModel.setUsername(username.getText().toString());
                userModel.setPassword(password.getText().toString());
                boolean auth = loginDBHandler.validateUser(userModel);
                if (auth){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Invalid Username Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}