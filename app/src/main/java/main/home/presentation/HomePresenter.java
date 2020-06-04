package main.home.presentation;

import java.util.List;

import commom.model.Feed;
import commom.presenter.Presenter;
import main.home.datasoure.HomeDataSource;
import main.presentation.MainView;

public class HomePresenter implements Presenter<List<Feed>> {

    private final HomeDataSource dataSource;

    private MainView.HomeView view;

    public HomePresenter(HomeDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.HomeView view) {
        this.view = view;
    }

    public void findFeed(){
        view.showProgressBar();
        dataSource.findFeed(this);
    }

    @Override
    public void onSuccess(List<Feed> response) {
        view.showFeed(response);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
