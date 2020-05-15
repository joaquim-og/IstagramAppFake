package register.presentation;

import android.content.Context;

import commom.view.View;

public interface RegisterView {

    void showNextView(RegisterSteps step);

    interface EmailView {

        Context getContext();

        void onFailureForm(String emailError);
    }

    interface NamePasswordView extends View {

        Context getContext();

        void onFailureForm(String nameError, String passwordError);

        void onFailureCreateuser(String message);
    }

    interface WelcomeView {

    }

}
