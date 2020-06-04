package main.home.datasoure;

import java.util.List;

import commom.model.Database;
import commom.model.Feed;
import commom.presenter.Presenter;

public class HomeLocalDataSource implements HomeDataSource {

    @Override
    public void findFeed(Presenter<List<Feed>> presenter) {
        Database db = Database.getInstance();
        db.findFeed(db.getUser().getUUID())
                .addOnSuccessListener(presenter::onSuccess)
                .addOnFailureListener(pe -> presenter.onError(pe.getMessage()))
                .addOnCompleteListener(presenter::onComplete);

    }

}
