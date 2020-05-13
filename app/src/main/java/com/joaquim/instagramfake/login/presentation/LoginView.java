package com.joaquim.instagramfake.login.presentation;

import commom.view.View;

public interface LoginView extends View {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();

}
