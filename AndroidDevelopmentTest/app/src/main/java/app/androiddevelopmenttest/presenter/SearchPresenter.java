package app.androiddevelopmenttest.presenter;

import app.androiddevelopmenttest.model.SearchDetailModel;

/**
 * Created by DELL on 6/22/2018.
 */

public interface SearchPresenter {
    void showDetail(SearchDetailModel searchDetailModel);

    void showError(int error);
}
