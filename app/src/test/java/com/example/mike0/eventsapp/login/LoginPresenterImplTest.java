package com.example.mike0.eventsapp.login;

import com.example.mike0.eventsapp.data.model.FingerPrintAuth;
import com.example.mike0.eventsapp.data.model.FingerPrintAuth.OnAuthResponseListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    private ArgumentCaptor<OnAuthResponseListener> listener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        listener = ArgumentCaptor.forClass(OnAuthResponseListener.class);
    }

    @Test
    public void startActivity_shouldStartActivity() {
        presenter.startMainActivity();
        verify(view).showMainActivity();
    }

    @Test
    public void startPresenterInit_shouldGetAuthStatus() {
        presenter.init();
        verify(auth).setOnAuthResponseListener(listener.capture());

        listener.getValue().onAuthStatus(anyString());
        verify(view).showAuthStatus(anyString());
    }
}