package app.androiddevelopmenttest.presenter;

/**
 * Created by DELL on 6/23/2018.
 */

public interface DetailPresenter<T> {
    void showData(T response);

    void showError(int errorCode);
}
