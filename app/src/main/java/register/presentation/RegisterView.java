package register.presentation;

import android.content.Context;
import android.net.Uri;

import commom.view.View;

public interface RegisterView {

    void showNextView(RegisterSteps step);

    void onUserCreated();

    void showCamera();

    void  showGallery();

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

    interface PhotoView {
        void onImageCropped(Uri uri);
    }

}
