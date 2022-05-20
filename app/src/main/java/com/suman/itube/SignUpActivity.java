package com.suman.itube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.suman.itube.DB.UserDBHandler;
import com.suman.itube.DB.UserModel;

public class SignUpActivity extends AppCompatActivity {
    UserDBHandler userDBHandler;
    EditText fullname, username, confpass, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userDBHandler = new UserDBHandler(this);
        fullname = findViewById(R.id.ed_fullname);
        username = findViewById(R.id.ed_username);
        password = findViewById(R.id.ed_password);
        confpass = findViewById(R.id.conf_pass);
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signUp();
            }
        });
    }
    void signUp(){
        if (password.getText().toString().equals(confpass.getText().toString())) {
            UserModel userModel = new UserModel();
            userModel.setFullname(fullname.getText().toString());
            userModel.setUsername(username.getText().toString());
            userModel.setPassword(password.getText().toString());
            userDBHandler.addUser(userModel);
            Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(SignUpActivity.this, "Password are not same", Toast.LENGTH_SHORT).show();
        }
    }
}