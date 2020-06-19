package main.search.datasource;

import java.util.List;

import commom.model.User;
import commom.presenter.Presenter;

public interface SearchDataSource {

    void findUser(String query, Presenter<List<User>> presenter);

}
