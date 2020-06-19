package main.profile.presentation;

import java.util.List;

import commom.model.Database;
import commom.model.Post;
import commom.model.User;
import commom.presenter.Presenter;
import commom.model.UserProfile;
import main.presentation.MainView;
import main.profile.datasource.ProfileDataSource;

public class ProfilePresenter implements Presenter<UserProfile> {
    private final ProfileDataSource dataSource;
    private MainView.ProfileView view;

    public ProfilePresenter(ProfileDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }

    public void findUsers(String user) {
        view.showProgressBar();
        dataSource.findUser(user, this);
    }

    @Override
    public void onSuccess(UserProfile userProfile) {
        User user = userProfile.getUser();
        List<Post> posts = userProfile.getPosts();

        boolean editProfile = user.getUuid().equals(Database.getInstance().getUser().getUUID());

        view.showData(
                user.getName(),
                String.valueOf(user.getFollowing()),
                String.valueOf(user.getFollowers()),
                String.valueOf(user.getPosts()),
                editProfile
        );

        view.showPosts(posts);

        if (user.getUri() != null)
            view.showPhoto(user.getUri());
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }

}
