package main.profile.datasource;

import java.util.List;

import commom.model.Database;
import commom.model.Post;
import commom.model.User;
import commom.model.UserProfile;
import commom.presenter.Presenter;
import main.profile.presentation.ProfilePresenter;

public class ProfileLocalDataSource implements ProfileDataSource {

    @Override
    public void findUser(Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(db.getUser().getUUID())
                .addOnSuccessListener((Database.OnSuccessListener<User>) user ->{
                    db.findPosts(user.getUuid())
                            .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                                    presenter.onSuccess(new UserProfile(user, posts));
                                    presenter.onComplete();
                            });
                });

    }

}
