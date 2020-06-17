package main.camera.presentation;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import commom.presenter.Presenter;
import main.camera.datasource.GalleryDatasource;

public class GalleryPresenter implements Presenter<List<String>> {

    private final GalleryDatasource datasource;
    private GalleryView view;

    public GalleryPresenter(GalleryDatasource datasource) {
        this.datasource = datasource;
    }

    public void setView(GalleryView view) {
        this.view = view;
    }

    public void findPictures(Context context) {
        view.showProgressBar();
        datasource.findPictures(context, this);
    }

    @Override
    public void onSuccess(List<String> response) {
        ArrayList<Uri> uris = new ArrayList<>();

        for (String res: response) {
            Uri uri = Uri.parse(res);
            uris.add(uri);
        }
        view.onPicturesLoaded(uris);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }

}
