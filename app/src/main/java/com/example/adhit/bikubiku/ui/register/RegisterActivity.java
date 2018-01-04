package com.example.adhit.bikubiku.ui.register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.presenter.RegisterPresenter;
import com.example.adhit.bikubiku.util.ShowAlert;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener, RegisterView {
    private FloatingActionButton fabLogin;
    private CardView cvRegister;
    private Button btnDaftarLine, btnRegister;
    private CoordinatorLayout coordinatorLayout;
    private EditText etUsername, etPassword, etEmail, etName;
    private RadioGroup rgGender;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab_login){
            animateRevealClose();
        }
        if(view.getId() == R.id.bt_daftar_line){
            ShowAlert.showSnackBar(coordinatorLayout, getResources().getString(R.string.text_feature_not_available_now));
        }
        if(view.getId() == R.id.btn_register){
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String name = etName.getText().toString().trim();
            rgGender.getCheckedRadioButtonId();
            if(username.isEmpty()) {
                etUsername.setError(getResources().getString(R.string.text_email_empty));
            }else if(password.isEmpty()){
                etPassword.setError(getResources().getString(R.string.text_password_empty));
            }else if(email.isEmpty()) {
                etEmail.setError(getResources().getString(R.string.text_user_name_empty));
            }else if(name.isEmpty()){
                etName.setError(getResources().getString(R.string.text_name_empty));
            }else if(rgGender.getCheckedRadioButtonId()== -1){
                ShowAlert.showSnackBar(coordinatorLayout, getResources().getString(R.string.text_gender_empty));
            }else {
                registerPresenter = new RegisterPresenter(this);
                registerPresenter.register(this, name, username, password, email, "belajar");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", etEmail.getText().toString().trim());
        outState.putString("username", etUsername.getText().toString().trim());
        outState.putString("password", etPassword.getText().toString().trim());
        outState.putString("name",etName.getText().toString().trim() );
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    public void showMessage(String string) {
        ShowAlert.showSnackBar(coordinatorLayout, string);

    }

    private void initView(){
        fabLogin = findViewById(R.id.fab_login);
        cvRegister = findViewById(R.id.cv_register);
        btnDaftarLine = findViewById(R.id.bt_daftar_line);
        btnRegister = findViewById(R.id.btn_register);
        coordinatorLayout = findViewById(R.id.coordinator);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        rgGender = findViewById(R.id.rg_gender);
        fabLogin.setOnClickListener(this);
        btnDaftarLine.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RegisterAnimation.ShowEnterAnimation(this, cvRegister, fabLogin, btnDaftarLine);
        }
    }

    public void animateRevealClose() {
        Animator mAnimator = null;
        Animator mAnimator2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(cvRegister,cvRegister.getWidth()/2,0, cvRegister.getHeight(), fabLogin.getWidth() / 2);
            mAnimator2 = ViewAnimationUtils.createCircularReveal(btnDaftarLine,0,0, btnDaftarLine.getWidth(), btnDaftarLine.getHeight());
        }
        mAnimator.setDuration(500);
        mAnimator2.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator2.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvRegister.setVisibility(View.INVISIBLE);
                btnDaftarLine.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fabLogin.setImageResource(R.drawable.ic_signup);
                RegisterActivity.super.onBackPressed();
            }
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
        mAnimator2.start();
    }



}
