package main.profile.datasource;

import java.util.List;

import commom.model.Database;
import commom.model.Post;
import commom.model.User;
import commom.model.UserProfile;
import commom.presenter.Presenter;

public class ProfileLocalDataSource implements ProfileDataSource {

    @Override
    public void findUser(String uuid, Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(uuid)
                .addOnSuccessListener((Database.OnSuccessListener<User>) user1 ->{
                    db.findPosts(uuid)
                            .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                                    db.following(db.getUser().getUUID(), uuid)
                                            .addOnSuccessListener((Database.OnSuccessListener<Boolean>) following -> {
                                                presenter.onSuccess(new UserProfile(user1, posts, following));
                                                presenter.onComplete();
                                            });
                            });
                });

    }

}
