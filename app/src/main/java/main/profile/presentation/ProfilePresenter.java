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
    private final String user;
    private MainView.ProfileView view;

    public ProfilePresenter(ProfileDataSource dataSource) {
        this(dataSource, Database.getInstance().getUser().getUUID());
    }

    public ProfilePresenter(ProfileDataSource dataSource, String user) {
        this.dataSource = dataSource;
        this.user = user;
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }

    public void findUsers() {
        view.showProgressBar();
        dataSource.findUser(user, this);
    }

    public String getUser() {
        return user;
    }

    public void follow(boolean follow) {
        if (follow)
            dataSource.follow(user);
        else
            dataSource.unfollow(user);
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
                editProfile,
                userProfile.isFollowing()
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
