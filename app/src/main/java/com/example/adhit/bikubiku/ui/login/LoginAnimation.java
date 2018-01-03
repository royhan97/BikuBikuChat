package com.example.adhit.bikubiku.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.example.adhit.bikubiku.ui.register.RegisterActivity;

import static com.example.adhit.bikubiku.ui.login.LoginActivity.REQUEST_CODE_REGISTER;

/**
 * Created by adhit on 03/01/2018.
 */

public class LoginAnimation {
    @SuppressLint("RestrictedApi")
    public static void animationRegister(AppCompatActivity appCompatActivity, FloatingActionButton floatingActionButton) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appCompatActivity.getWindow().setExitTransition(null);
            appCompatActivity.getWindow().setEnterTransition(null);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(appCompatActivity, floatingActionButton, floatingActionButton.getTransitionName());
            appCompatActivity.startActivityForResult(new Intent(appCompatActivity, RegisterActivity.class), REQUEST_CODE_REGISTER, options.toBundle());
        } else {
            appCompatActivity.startActivityForResult(new Intent(appCompatActivity, RegisterActivity.class), REQUEST_CODE_REGISTER);
        }
    }
}
