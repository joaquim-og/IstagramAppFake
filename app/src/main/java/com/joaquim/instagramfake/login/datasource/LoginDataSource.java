package com.joaquim.instagramfake.login.datasource;

import commom.presenter.Presenter;

public interface LoginDataSource {

    void login(String email, String password, Presenter presenter);

}
