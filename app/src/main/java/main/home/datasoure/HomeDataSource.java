package main.home.datasoure;

import java.util.List;

import commom.model.Feed;
import commom.presenter.Presenter;

public interface HomeDataSource {

    void findFeed(Presenter<List<Feed>> presenter);

}
