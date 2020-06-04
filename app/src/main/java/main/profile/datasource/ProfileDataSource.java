package main.profile.datasource;

import commom.model.UserProfile;
import commom.presenter.Presenter;
import main.profile.presentation.ProfilePresenter;

public interface ProfileDataSource {

    void findUser(Presenter<UserProfile> presenter);
}
