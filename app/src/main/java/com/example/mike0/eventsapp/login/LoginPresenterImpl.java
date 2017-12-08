package com.example.mike0.eventsapp.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.example.mike0.eventsapp.R;
import com.example.mike0.eventsapp.data.FingerprintHandler;
import com.example.mike0.eventsapp.data.model.FingerPrintAuth;
import com.example.mike0.eventsapp.main.MainActivity;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by mike0 on 12/5/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {

    LoginView loginView;

    FingerPrintAuth fingerPrintAuth;

    String status;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        this.loginView = loginView;
        fingerPrintAuth = new FingerPrintAuth(context);
    }

    public void init() {
        fingerPrintAuth.init();
        status = fingerPrintAuth.getAuthStatus();
        loginView.showAuthStatus(status);
    }

    public void startMainActivity() {
        loginView.showMainActivity();
    }
}
