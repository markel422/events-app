package com.example.mike0.eventsapp.login;

import android.content.Context;

import com.example.mike0.eventsapp.data.model.FingerPrintAuth;

/**
 * Created by mike0 on 12/5/2017.
 */

public class LoginPresenterImpl implements LoginPresenter, FingerPrintAuth.OnAuthResponseListener {

    private LoginView loginView;

    private FingerPrintAuth fingerPrintAuth;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        this.loginView = loginView;
        fingerPrintAuth = new FingerPrintAuth(context);
    }

    public void init() {
        fingerPrintAuth.setOnAuthResponseListener(this);
        fingerPrintAuth.init();
    }

    public void startMainActivity() {
        loginView.showMainActivity();
    }

    @Override
    public void onAuthStatus(String status) {
        loginView.showAuthStatus(status);
    }
}
