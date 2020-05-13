package commom.presenter;

public interface Presenter {

    void onSuccess();

    void onError(String message);

    void onComplete();

}
