package register.presentation;

import com.joaquim.instagramfake.R;

import commom.util.Strings;

public class RegisterPresenter {

    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView setNamePasswordView;
    private RegisterView registerView;

    private String email;
    private String name;
    private String password;

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
    }
}
