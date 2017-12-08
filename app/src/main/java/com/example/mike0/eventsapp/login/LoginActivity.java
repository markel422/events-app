package com.example.mike0.eventsapp.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mike0.eventsapp.R;
import com.example.mike0.eventsapp.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    LoginPresenterImpl presenter;

    Button loginBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = (TextView) findViewById(R.id.textview);

        loginBtn = (Button) findViewById(R.id.alt_login_btn);

        presenter = new LoginPresenterImpl(this, getApplicationContext());

        presenter.init();

        loginBtn.setOnClickListener(this);
    }

    @Override
    public void showAuthStatus(String text) {
        textView.setText(text);
    }

    @Override
    public void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alt_login_btn:
                presenter.startMainActivity();
                break;
        }
    }
}
