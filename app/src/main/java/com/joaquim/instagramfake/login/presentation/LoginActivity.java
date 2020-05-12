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

import butterknife.BindView;
import butterknife.ButterKnife;
import commom.view.AbstractActivity;
import commom.view.LoadingButton;

public class LoginActivity extends AbstractActivity implements LoginView {

    @BindView(R.id.login_edit_text_email)
    EditText editTextMail;
    @BindView(R.id.login_edit_text_password)
    EditText editTextPassword;
    @BindView(R.id.login_edit_text_email_input)
    TextInputLayout inputLayouttext;
    @BindView(R.id.login_edit_text_password_input)
    TextInputLayout inputLayoutTesteP;

    @BindView(R.id.login_button_enter) LoadingButton buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

        editTextMail.addTextChangedListener(watcher);
        editTextPassword.addTextChangedListener(watcher);

        buttonEnter.setOnClickListener(v -> {
            buttonEnter.showProgress(true);

            new Handler().postDelayed(() -> {
                buttonEnter.showProgress(false);


            }, 4000);
        });

    }

    @Override
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            inputLayouttext.setError(emailError);
            editTextMail.setBackground(findDrawable(R.drawable.edit_text_error));
        }
    }

    @Override
    public void onUserLogged() {
        // TODO ainda tem de implementar çábagaça
    }

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
    protected int getLayout() {
        return R.layout.activity_login;
    }

}
