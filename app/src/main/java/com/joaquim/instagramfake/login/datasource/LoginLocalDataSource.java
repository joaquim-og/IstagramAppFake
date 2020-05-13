package com.joaquim.instagramfake.login.datasource;

import commom.model.Database;
import commom.model.UserAuth;
import commom.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {

    @Override
    public void login(String email, String password, Presenter presenter) {
        Database.getInstance().login(email, password)
                .addOnSuccessListener((Database.OnSuccessListener<UserAuth>) response -> presenter.onSuccess(response))
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(() -> presenter.onComplete());
    }
}
