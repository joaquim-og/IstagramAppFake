package main.profile.datasource;

import commom.model.UserProfile;
import commom.presenter.Presenter;

public interface ProfileDataSource {

    void findUser(String user, Presenter<UserProfile> presenter);
}
