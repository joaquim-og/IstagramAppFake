package com.joaquim.instagramfake.login.datasource;

import commom.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {

    @Override
    public void login(String email, String password, Presenter presenter) {
        presenter.onError("Error 1");
        presenter.onComplete();
    }
}
