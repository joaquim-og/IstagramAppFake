package main.camera.presentation;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joaquim.instagramfake.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import commom.view.AbstractFragment;

public class GalleryFragment extends AbstractFragment<GalleryPresenter> implements  GalleryView {

    @BindView(R.id.main_gallery_scrool_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.main_gallery_top)
    ImageView imageViewMain;

    @BindView(R.id.main_gallery_recycler_view)
    RecyclerView recyclerView;

    private PictureAdapter pictureAdapter;
    private AddView addView;
    private Uri uri;

    public static GalleryFragment newInstance(AddView addView, GalleryPresenter presenter) {
        GalleryFragment galleryFragment = new GalleryFragment();
        presenter.setView(galleryFragment);
        galleryFragment.setPresenter(presenter);
        galleryFragment.addView(addView);
        return galleryFragment;
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

        pictureAdapter = new PictureAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(pictureAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

        presenter.findPictures(getContext());
    }

    @Override
    public void onPicturesLoaded(List<Uri> uris) {
        if (uris.size() > 0) {
            imageViewMain.setImageURI(uris.get(0));
            this.uri = uris.get(0);
        }

        pictureAdapter.sePictures(uris, uri1 -> {
            this.uri = uri1;
            imageViewMain.setImageURI(uri1);
            nestedScrollView.smoothScrollTo(0,0);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gallery, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_go) {
            addView.onImageLoaded(uri);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addView(AddView addView) {
        this.addView = addView;
    }

    interface GalleryItemClickListener {
        void onClick(Uri uri);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_gallery;
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagePost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.profile_image_grid);
        }

        void bind(Uri uri) {
            this.imagePost.setImageURI(uri);
        }
    }

    private class PictureAdapter extends RecyclerView.Adapter<PostViewHolder> {

        private GalleryItemClickListener listener;
        private List<Uri> uris = new ArrayList<>();

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.item_profile_grid, viewGroup, false);
            return new PostViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position) {
            postViewHolder.bind(uris.get(position));
            postViewHolder.imagePost.setOnClickListener(v -> {
                Uri uri = uris.get(position);
                listener.onClick(uri);
            });
        }

        public void sePictures(List<Uri> uri, GalleryItemClickListener galleryItemClickListener) {
            this.uris = uris;
            this.listener = listener;
        }

        @Override
        public int getItemCount() {
            return uris.size();
        }
    }

}



