package com.example.adhit.bikubiku.ui.login;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.util.ShowAlert;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {
    private FloatingActionButton fabRegister;
    public static int REQUEST_CODE_REGISTER = 2000;
    private Button btnDaftarLine, btnLogin;
    private CoordinatorLayout coordinatorLayout;
    private EditText etEmail, etUsername, etPassword;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        coordinatorLayout = findViewById(R.id.coordinator);
        fabRegister = findViewById(R.id.fab_register);
        btnDaftarLine = (Button) findViewById(R.id.btn_login_line);
        btnLogin = (Button) findViewById(R.id.btn_login);
        etEmail = findViewById(R.id.et_email);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        fabRegister.setOnClickListener(this);
        btnDaftarLine.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab_register){
            LoginAnimation.animationRegister(this, fabRegister);
        }
        if(view.getId() == R.id.btn_login_line){
            ShowAlert.showSnackBar(coordinatorLayout, getResources().getString(R.string.text_feature_not_available_now));
        }
        if(view.getId() == R.id.btn_login){
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if(email.isEmpty()) {
                etEmail.setError(getResources().getString(R.string.text_email_empty));
            }else if(username.isEmpty()){
                etUsername.setError(getResources().getString(R.string.text_user_name_empty));
            }else if(password.isEmpty()){
                etPassword.setError(getResources().getString(R.string.text_password_empty));
            } else {
                loginPresenter = new LoginPresenter(this);
                loginPresenter.Login(this, username, password);
            }
        }
    }


    @Override
    public void showMessage(String string) {
       ShowAlert.showToast(this, string);
    }

    @Override
    public void moveActivity(boolean b) {
        if(b){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void showMessageSnackbar(String message) {
        ShowAlert.showSnackBar(coordinatorLayout, message);
    }
}
