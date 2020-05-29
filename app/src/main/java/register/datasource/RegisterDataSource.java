package register.datasource;

import android.net.Uri;

import commom.presenter.Presenter;

public interface RegisterDataSource {

    void createUser(String name, String email, String password, Presenter presenter);

    void addPhoto(Uri uri, Presenter presenter);

}
