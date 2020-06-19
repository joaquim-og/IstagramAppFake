package main.search.datasource;

import java.util.List;

import commom.model.Database;
import commom.model.User;
import commom.presenter.Presenter;

public class SearchLocalDataSource implements SearchDataSource {

    @Override
    public void findUser(String query, Presenter<List<User>> presenter) {
        Database db = Database.getInstance();
        db.findUsers(db.getUser().getUUID(), query)
                .addOnSuccessListener(presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }
}
