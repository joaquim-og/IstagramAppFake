package com.joaquim.instagramfake.login.datasource;

import com.google.firebase.auth.FirebaseAuth;

import commom.presenter.Presenter;

public class LoginFireDataSource implements LoginDataSource {

    @Override
    public void login(String email, String password, Presenter presenter) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> presenter.onSuccess(authResult.getUser()))
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(task -> presenter.onComplete());

    }

}
