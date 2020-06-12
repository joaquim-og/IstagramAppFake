package main.camera.datasource;

import android.net.Uri;

import commom.presenter.Presenter;

public interface AddDataSource {

    void savePost(Uri uri, String caption, Presenter presenter);

}
