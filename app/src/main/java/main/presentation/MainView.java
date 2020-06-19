package main.presentation;

import android.net.Uri;

import java.util.List;

import commom.model.Feed;
import commom.model.Post;
import commom.model.User;
import commom.view.View;

public interface MainView extends View {

    void showProfile(String user);

    void scrollToolbarEnabled(boolean enabled);

    public interface ProfileView extends View {
        void showPhoto(Uri photo);

        void showData(String name, String following, String followers, String posts, boolean editProfile);

        void showPosts(List<Post> posts);
    }

    public interface HomeView extends View {

        void showFeed(List<Feed> response);
    }

    public interface SearchView {

        void showUsers(List<User> users);

    }

}
