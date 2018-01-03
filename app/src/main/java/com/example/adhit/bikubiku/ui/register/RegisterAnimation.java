package com.example.adhit.bikubiku.ui.register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import com.example.adhit.bikubiku.R;

/**
 * Created by adhit on 03/01/2018.
 */

public class RegisterAnimation {
    public  static void ShowEnterAnimation(final AppCompatActivity appCompatActivity, final CardView cardView, final FloatingActionButton fabLogin, final Button btnDaftarLine) {
        Transition transition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition = TransitionInflater.from(appCompatActivity).inflateTransition(R.transition.fab_transition);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               appCompatActivity.getWindow().setSharedElementEnterTransition(transition);
            }

            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    cardView.setVisibility(View.GONE);
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        transition.removeListener(this);
                    }
                    animateRevealShow(cardView, fabLogin, btnDaftarLine);
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }

    public static void animateRevealShow(final CardView cvRegister, FloatingActionButton fabLogin, Button btnDaftarLine) {
        Animator mAnimator = null;
        Animator mAnimator2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(cvRegister, cvRegister.getWidth()/2,0, fabLogin.getWidth() / 2, cvRegister.getHeight());
            mAnimator2 = ViewAnimationUtils.createCircularReveal(btnDaftarLine,0,0, btnDaftarLine.getHeight(), btnDaftarLine.getWidth());
        }
        mAnimator.setDuration(500);
        mAnimator2.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator2.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvRegister.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
        mAnimator2.start();
    }

}
