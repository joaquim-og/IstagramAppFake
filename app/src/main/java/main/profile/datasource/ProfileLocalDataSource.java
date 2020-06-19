package main.profile.datasource;

import java.util.List;

import commom.model.Database;
import commom.model.Post;
import commom.model.User;
import commom.model.UserProfile;
import commom.presenter.Presenter;

public class ProfileLocalDataSource implements ProfileDataSource {

    @Override
    public void findUser(String user, Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(user)
                .addOnSuccessListener((Database.OnSuccessListener<User>) user1 ->{
                    db.findPosts(user1.getUuid())
                            .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                                    presenter.onSuccess(new UserProfile(user1, posts));
                                    presenter.onComplete();
                            });
                });

    }

}
