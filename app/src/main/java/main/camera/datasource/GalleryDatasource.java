package main.camera.datasource;

import android.content.Context;

import commom.presenter.Presenter;

public interface GalleryDatasource {

    void findPictures(Context context, Presenter presenter);
}
