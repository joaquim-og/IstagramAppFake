package commom.presenter;

import commom.model.UserAuth;

public interface Presenter<T> {

    void onSuccess(T response);

    void onError(String message);

    void onComplete();

}
