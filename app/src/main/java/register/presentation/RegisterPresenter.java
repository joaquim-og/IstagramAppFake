package register.presentation;

import com.joaquim.instagramfake.R;

import commom.model.UserAuth;
import commom.presenter.Presenter;
import commom.util.Strings;
import register.datasource.RegisterLocalDataSource;

public class RegisterPresenter implements Presenter<UserAuth> {

    private final RegisterLocalDataSource dataSource;
    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView setNamePasswordView;
    private RegisterView registerView;

    private String email;
    private String name;
    private String password;

    public RegisterPresenter(RegisterLocalDataSource dataSource) {
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
        this.password = password;

        setNamePasswordView.showProgressBar();
        dataSource.createUser(this.name, this.email, this.password, this);
    }

    public String getName() {
        return name;
    }

    public void showPhotoView() {
        registerView.showNextView(RegisterSteps.PHOTO);
    }

    public void jumpRegistration() {
        registerView.onUserCreated();
    }

    @Override
    public void onSuccess(UserAuth response) {
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
}
