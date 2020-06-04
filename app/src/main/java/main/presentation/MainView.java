package main.presentation;

import android.net.Uri;

import java.util.List;

import commom.model.Post;
import commom.view.View;

public interface MainView extends View {

    void scrollToolbarEnabled(boolean enabled);

    public interface ProfileView extends View {
        void showPhoto(Uri photo);

        void showData(String name, String following, String followers, String posts);

        void showPosts(List<Post> posts);
    }

}
