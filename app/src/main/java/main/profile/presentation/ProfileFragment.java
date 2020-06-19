package main.profile.presentation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.joaquim.instagramfake.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import commom.model.Database;
import commom.model.Post;
import commom.view.AbstractFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import main.presentation.MainView;

public class ProfileFragment extends AbstractFragment<ProfilePresenter> implements MainView.ProfileView {

    private PostAdaper postAdaper;
    private MainView mainView;

    @BindView(R.id.profile_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.profile_image_icon)
    CircleImageView imageViewProfile;

    @BindView(R.id.profile_text_view_username)
    TextView txtUsername;

    @BindView(R.id.profile_text_view_following_count)
    TextView txtFollowingCount;

    @BindView(R.id.profile_text_view_followers_count)
    TextView txtFollowersCount;

    @BindView(R.id.profile_text_view_posts_count)
    TextView txtPostsCount;

    @BindView(R.id.profile_navigation_tabs)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.profile_button_edit_profile)
    Button button;

    public static ProfileFragment newInstance(MainView mainView, ProfilePresenter profilePresenter){
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setPresenter(profilePresenter);
        profileFragment.setMainView(mainView);
        profilePresenter.setView(profileFragment);
        return profileFragment;
    }

    public ProfileFragment() {}

    private void setMainView(MainView mainView){
        this.mainView= mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findUsers(Database.getInstance().getUser().getUUID());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_profile_grid:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    return  true;
                case R.id.menu_profile_list:
                    recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
                    return  true;
            }
            return false;
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        postAdaper = new PostAdaper();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(postAdaper);


        return view;
    }

    @Override
    public void showProgressBar() {
        mainView.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        mainView.hideProgressBar();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_profile;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showPhoto(Uri photo) {

        try {
            if (getContext() != null && getContext().getContentResolver() != null) {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photo);

                imageViewProfile.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            Log.e("tetes", e.getMessage(), e);
        }

    }

    @Override
    public void showData(String name, String following, String followers, String posts, boolean editProfile) {
        txtUsername.setText(name);
        txtFollowersCount.setText(followers);
        txtFollowingCount.setText(following);
        txtPostsCount.setText(posts);

        if (editProfile) {
            button.setText(R.string.edit_profile);
        } else {
            button.setText(R.string.follow);
        }
    }

    @Override
    public void showPosts(List<Post> posts) {
        postAdaper.setPosts(posts);
        postAdaper.notifyDataSetChanged();
    }


    private class PostAdaper extends RecyclerView.Adapter<PostViewHolder> {

        private List<Post> posts = new ArrayList<>();

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            return new PostViewHolder(getLayoutInflater().inflate(R.layout.item_profile_grid, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
            postViewHolder.bind(posts.get(i));
        }

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }


    private static class PostViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imagePost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.profileImage_grid);
        }

        public void bind(Post post){
            this.imagePost.setImageURI(post.getUri());
        }
    }
}
