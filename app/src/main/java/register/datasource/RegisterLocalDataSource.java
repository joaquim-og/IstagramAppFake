package register.datasource;

import commom.model.Database;
import commom.model.UserAuth;
import commom.presenter.Presenter;

public class RegisterLocalDataSource implements RegisterDataSource {

    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        Database.getInstance().createUser(name, email, password)
                .addOnSuccessListener((Database.OnSuccessListener<UserAuth>) presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

}
