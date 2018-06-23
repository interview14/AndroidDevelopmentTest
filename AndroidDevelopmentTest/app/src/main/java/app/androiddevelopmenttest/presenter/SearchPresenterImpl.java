package app.androiddevelopmenttest.presenter;

import android.text.TextUtils;

import org.json.JSONObject;

import app.androiddevelopmenttest.model.SearchDetailModel;
import app.androiddevelopmenttest.net.CommunicationManager;
import app.androiddevelopmenttest.net.HttpAsyncTask;
import app.androiddevelopmenttest.util.Constants;

/**
 * Created by DELL on 6/22/2018.
 */

public class SearchPresenterImpl {
    SearchPresenter searchPresenter;

    public SearchPresenterImpl(SearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

    public void doSearch(String userId) {
        if (TextUtils.isEmpty(userId)) {
            searchPresenter.showError(1);
        } else {
            new HttpAsyncTask(new CommunicationManager<SearchDetailModel>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        SearchDetailModel searchDetailModel = new SearchDetailModel();
                        searchDetailModel.setAvatar_url(jsonObject.getString(Constants.WebServiceKeys.AVATAR_URL));
                        searchDetailModel.setLogin(jsonObject.getString(Constants.WebServiceKeys.LOGIN));
                        searchDetailModel.setName(jsonObject.getString(Constants.WebServiceKeys.NAME));
                        searchDetailModel.setEmail(jsonObject.getString(Constants.WebServiceKeys.EMAIL));
                        searchDetailModel.setFollowers_url(jsonObject.getString(Constants.WebServiceKeys.FOLLOWERS_URL));
                        onProcessNext(searchDetailModel);
                    } catch (Exception e) {
                        searchPresenter.showError(0);
                    }
                }

                @Override
                public void onProcessNext(SearchDetailModel searchDetailModel) {
                    searchPresenter.showDetail(searchDetailModel);
                }

                @Override
                public void onError() {
                    searchPresenter.showError(2);
                }
            }).execute(Constants.BASE_URL + userId);
        }
    }
}
