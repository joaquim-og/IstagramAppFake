package main.home.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joaquim.instagramfake.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import commom.model.Feed;
import commom.model.User;
import commom.view.AbstractFragment;
import main.presentation.MainView;

public class HomeFragment extends AbstractFragment<HomePresenter> implements MainView.HomeView {

    private MainView mainView;
    private FeedAdapter feedAdapter;

    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;

    public static HomeFragment newInstance(MainView mainView, HomePresenter homePresenter){
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setMainView(mainView);
        homeFragment.setPresenter(homePresenter);
        homePresenter.setView(homeFragment);
        return homeFragment;
    }

    public HomeFragment() {}

    private void setMainView(MainView mainView){
        this.mainView= mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        feedAdapter = new FeedAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(feedAdapter);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findFeed();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_home;
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
    public void showFeed(List<Feed> response) {
        feedAdapter.setFeed(response);
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class FeedAdapter extends RecyclerView.Adapter<PostViewHolder> {

        private List<Feed> feed = new ArrayList<>();

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            return new PostViewHolder(getLayoutInflater().inflate(R.layout.item_post_list, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
            postViewHolder.bind(feed.get(i));
        }

        @Override
        public int getItemCount() {
            return feed.size();
        }

        public void setFeed(List<Feed> feed) {
            this.feed = feed;
        }
    }


    private static class PostViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imagePost;
        private final ImageView imageUser;
        private final TextView textViewCaption;
        private final TextView textViewUsername;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.profile_image_grid);
            imageUser = itemView.findViewById(R.id.home_container_user_photo);
            textViewCaption = itemView.findViewById(R.id.home_container_user_caption);
            textViewUsername = itemView.findViewById(R.id.home_container_username);

        }

        public void bind(Feed feed) {
            this.imagePost.setImageURI(feed.getUri());
            this.textViewCaption.setText(feed.getCaption());

            User user = feed.getPublisher();
            if (user != null) {
                this.imageUser.setImageURI(user.getUri());
                this.textViewUsername.setText(user.getName());
            }
        }
    }
}
