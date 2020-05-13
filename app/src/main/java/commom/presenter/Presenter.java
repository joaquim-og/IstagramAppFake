package commom.presenter;

import commom.model.UserAuth;

public interface Presenter {

    void onSuccess(UserAuth response);

    void onError(String message);

    void onComplete();

}
