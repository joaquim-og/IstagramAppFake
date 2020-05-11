package com.joaquim.instagramfake.login.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.joaquim.instagramfake.R;

import commom.view.LoadingButton;

public class LoginActivity extends AppCompatActivity {

    private LoadingButton buttonEnter;

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().isEmpty()) {
                buttonEnter.setEnabled(true);
            } else {
                buttonEnter.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

        buttonEnter = findViewById(R.id.login_button_enter);
        final EditText editTextMail = findViewById(R.id.login_edit_text_email);
        final EditText editTextPassword = findViewById(R.id.login_edit_text_password);

        editTextMail.addTextChangedListener(watcher);
        editTextPassword.addTextChangedListener(watcher);

        buttonEnter.setOnClickListener(v -> {
            buttonEnter.showProgress(true);

            new Handler().postDelayed(() -> {
                buttonEnter.showProgress(false);

                TextInputLayout inputLayouttext = findViewById(R.id.login_edit_text_email_input);
                inputLayouttext.setError("Invalido e-mail xablau");
                editTextMail.setBackground(ContextCompat.getDrawable(LoginActivity.this,
                        R.drawable.edit_text_error));

                TextInputLayout inputLayoutTesteP = findViewById(R.id.login_edit_text_password_input);
                inputLayoutTesteP.setError("Senha incorreta xablau");
                editTextPassword.setBackground(ContextCompat.getDrawable(LoginActivity.this,
                        R.drawable.edit_text_error));

            }, 4000);
        });

    }


}
