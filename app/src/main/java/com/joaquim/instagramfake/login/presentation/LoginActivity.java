package com.joaquim.instagramfake.login.presentation;

import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.joaquim.instagramfake.R;
import com.joaquim.instagramfake.login.datasource.LoginDataSource;
import com.joaquim.instagramfake.login.datasource.LoginLocalDataSource;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import commom.view.AbstractActivity;
import commom.view.LoadingButton;
import main.presentation.MainActivity;

public class LoginActivity extends AbstractActivity implements LoginView {

    LoginPresenter presenter;

    @BindView(R.id.login_edit_text_email)
    EditText editTextMail;
    @BindView(R.id.login_edit_text_password)
    EditText editTextPassword;
    @BindView(R.id.login_edit_text_email_input)
    TextInputLayout inputLayouttext;
    @BindView(R.id.login_edit_text_password_input)
    TextInputLayout inputLayoutTesteP;

    @BindView(R.id.login_button_enter)
    LoadingButton buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

    }

    @Override
    protected void onInject() {
        LoginDataSource dataSource = new LoginLocalDataSource();
        presenter = new LoginPresenter(this, dataSource);
    }

    @Override
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            inputLayouttext.setError(emailError);
            editTextMail.setBackground(findDrawable(R.drawable.edit_text_error));
        }

        if (passwordError != null) {
            inputLayoutTesteP.setError(passwordError);
            editTextPassword.setBackground(findDrawable(R.drawable.edit_text_error));
        }
    }

    @Override
    public void onUserLogged() {
        MainActivity.launch(this);
    }

    @OnClick(R.id.login_button_enter)
    public void onButtonEnterClick() {
        presenter.login(editTextMail.getText().toString(), editTextPassword.getText().toString());
    }

    @OnTextChanged({R.id.login_edit_text_email, R.id.login_edit_text_password})
    public void onTextChanged(CharSequence s) {
        buttonEnter.setEnabled(
                !editTextMail.getText().toString().isEmpty() &&
                        !editTextPassword.getText().toString().isEmpty());

        if (s.hashCode() == editTextMail.getText().hashCode()) {
            editTextMail.setBackground(findDrawable(R.drawable.edit_text_background));
            inputLayouttext.setError(null);
            inputLayouttext.setErrorEnabled(false);
        } else if (s.hashCode() == editTextPassword.getText().hashCode()) {
            editTextPassword.setBackground(findDrawable(R.drawable.edit_text_background));
            inputLayoutTesteP.setError(null);
            inputLayoutTesteP.setErrorEnabled(false);
        }
    }

    @Override
    public void showProgressBar() {
        buttonEnter.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        buttonEnter.showProgress(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

}
