package main.search.presentation;

import java.util.List;

import commom.model.User;
import commom.presenter.Presenter;
import main.presentation.MainView;
import main.search.datasource.SearchDataSource;

public class SearchPresenter implements Presenter<List<User>> {

    private final SearchDataSource dataSource;
    private MainView.SearchView view;

    public SearchPresenter(SearchDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.SearchView view) {
        this.view = view;
    }

    @Override
    public void onSuccess(List<User> response) {
        view.showUsers(response);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onComplete() {

    }

    public void findUser(String newText) {
        dataSource.findUser(newText, this);
    }
}
