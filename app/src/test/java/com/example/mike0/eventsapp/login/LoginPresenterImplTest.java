package com.example.mike0.eventsapp.login;

import android.hardware.fingerprint.FingerprintManager;

import com.example.mike0.eventsapp.data.FingerprintHandler;
import com.example.mike0.eventsapp.data.model.FingerPrintAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.crypto.Cipher;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by mike0 on 12/6/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplTest {

    @InjectMocks
    LoginPresenterImpl presenter;

    @Mock
    LoginView view;

    @Mock
    FingerPrintAuth auth;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void startActivity_shouldStartActivity() {



        presenter.startMainActivity();
        verify(view).showMainActivity();
    }

}