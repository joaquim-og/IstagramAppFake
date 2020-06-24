package com.joaquim.instagramfake.login.presentation;

import com.google.firebase.auth.FirebaseUser;
import com.joaquim.instagramfake.R;
import com.joaquim.instagramfake.login.datasource.LoginDataSource;

import android.os.Handler;

import commom.model.UserAuth;
import commom.presenter.Presenter;
import commom.util.Strings;

class LoginPresenter implements Presenter<FirebaseUser> {

    private final LoginView view;
    private final LoginDataSource dataSource;

    LoginPresenter(LoginView view, LoginDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    void login(String email, String password) {

        if (!Strings.emailValid(email)) {
            view.onFailureForm(view.getContext().getString(R.string.invalid_email), null);
            return;
        }
        view.showProgressBar();
        dataSource.login(email, password, this);
    }

    @Override
    public void onSuccess(FirebaseUser userAuth) {
        view.onUserLogged();
    }

    @Override
    public void onError(String message) {
        view.onFailureForm(null, message);
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
