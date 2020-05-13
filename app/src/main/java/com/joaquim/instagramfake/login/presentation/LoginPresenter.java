package com.joaquim.instagramfake.login.presentation;

import com.joaquim.instagramfake.login.datasource.LoginDataSource;

import android.os.Handler;

class LoginPresenter {

    private final LoginView view;
    private final LoginDataSource dataSource;

    LoginPresenter(LoginView view, LoginDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    void login(String email, String password) {
        view.showProgressBar();

        new Handler().postDelayed(() -> {
            view.hideProgressBar();
            view.onFailureForm("Error1", "Erro2");
        }, 2000);
    }

}
