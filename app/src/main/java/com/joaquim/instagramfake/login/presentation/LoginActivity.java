package com.joaquim.instagramfake.login.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.joaquim.instagramfake.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout inputLayouttext = findViewById(R.id.login_edit_text_password_input);
        inputLayouttext.setError("Invalido xablau");

        EditText editText = findViewById(R.id.login_edit_text_password);
        editText.setBackground(ContextCompat.getDrawable(LoginActivity.this,
                R.drawable.edit_text_error));
    }
}
