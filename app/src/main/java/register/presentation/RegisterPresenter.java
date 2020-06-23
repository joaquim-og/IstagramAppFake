package register.presentation;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.joaquim.instagramfake.R;

import commom.model.UserAuth;
import commom.presenter.Presenter;
import commom.util.Strings;
import register.datasource.RegisterDataSource;
import register.datasource.RegisterLocalDataSource;

public class RegisterPresenter implements Presenter<FirebaseUser> {

    private final RegisterDataSource dataSource;
    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView setNamePasswordView;
    private RegisterView registerView;
    private RegisterView.PhotoView photoView;

    private String email;
    private String name;
    private Uri uri;

    public RegisterPresenter(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    public void setNamePasswordView(RegisterView.NamePasswordView namePasswordView) {
        this.setNamePasswordView = namePasswordView;
    }

    public void setEmail(String email){
        if (!Strings.emailValid(email)) {
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }

        this.email = email;
        registerView.showNextView(RegisterSteps.NAME_PASSWORD);
    }

    public void setNameAndPassword(String name, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            setNamePasswordView.onFailureForm(null, setNamePasswordView.getContext().getString(R.string.password_not_equal));
            return;
        }

        this.name = name;

        setNamePasswordView.showProgressBar();
        dataSource.createUser(name.toLowerCase(), email, password, this);
    }

    public void setPhotoView(RegisterView.PhotoView photoView) {
        this.photoView = photoView;
    }

    public String getName() {
        return name;
    }

    public void setUri(Uri uri){
        this.uri = uri;
        if (photoView != null) {
            photoView.onImageCropped(uri);
            photoView.showProgressBar();

            dataSource.addPhoto(uri, new UpdatePhotoCallback());
        }

    }

    public RegisterView.EmailView getEmailView() {
        return emailView;
    }

    public void showPhotoView() {
        registerView.showNextView(RegisterSteps.PHOTO);
    }

    public void showCamera() {
        registerView.showCamera();
    }

    public void showGallery() {
        registerView.showGallery();
    }

    public void jumpRegistration() {
        registerView.onUserCreated();
    }

    @Override
    public void onSuccess(FirebaseUser response) {
        registerView.showNextView(RegisterSteps.WELCOME);
    }

    @Override
    public void onError(String message) {
        setNamePasswordView.onFailureCreateuser(message);
    }

    @Override
    public void onComplete() {
        setNamePasswordView.hideProgressBar();
    }

    private class UpdatePhotoCallback implements Presenter<Boolean> {

        @Override
        public void onSuccess(Boolean response) {
            registerView.onUserCreated();
        }

        @Override
        public void onError(String message) {

        }

        @Override
        public void onComplete() {

        }
    }
}
