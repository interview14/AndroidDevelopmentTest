package app.androiddevelopmenttest.presenter;

import android.graphics.Bitmap;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.androiddevelopmenttest.model.FollowerDetailModel;
import app.androiddevelopmenttest.net.CommunicationManager;
import app.androiddevelopmenttest.net.HttpAsyncTask;
import app.androiddevelopmenttest.net.LoadImageAsyncTask;
import app.androiddevelopmenttest.util.Constants;

/**
 * Created by DELL on 6/23/2018.
 */

public class DetailPresenterImpl {
    private DetailPresenter detailPresenter;

    public DetailPresenterImpl(DetailPresenter detailPresenter) {
        this.detailPresenter = detailPresenter;
    }

    public void getFollowers(String followersUrl) {
        if (TextUtils.isEmpty(followersUrl)) {
            detailPresenter.showError(1);
        } else {
            new HttpAsyncTask(new CommunicationManager<List<FollowerDetailModel>>() {
                @Override
                public void onResponse(String response) {
                    if (TextUtils.isEmpty(response)) {
                        detailPresenter.showError(0);
                    } else {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            List<FollowerDetailModel> followerDetailModelList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                final FollowerDetailModel followerDetailModel = new FollowerDetailModel();
                                followerDetailModel.setAvatar_url(jsonObject.getString(Constants.WebServiceKeys.AVATAR_URL));
                                followerDetailModel.setLogin(jsonObject.getString(Constants.WebServiceKeys.LOGIN));
                                getImage(followerDetailModel);
                                followerDetailModelList.add(followerDetailModel);
                            }
                            onProcessNext(followerDetailModelList);
                        } catch (Exception e) {
                            detailPresenter.showError(0);
                        }
                    }
                }

                @Override
                public void onProcessNext(List<FollowerDetailModel> followerDetailModelList) {
                    detailPresenter.showData(followerDetailModelList);
                }

                @Override
                public void onError() {
                    detailPresenter.showError(0);
                }
            }).execute(followersUrl);
        }
    }

    private void getImage(final FollowerDetailModel followerDetailModel) {
        String url = followerDetailModel.getAvatar_url();

        if (TextUtils.isEmpty(url)) {
            followerDetailModel.setAvatar(null);
        } else {
            new LoadImageAsyncTask(new CommunicationManager<Bitmap>() {
                @Override
                public void onResponse(String response) {

                }

                @Override
                public void onProcessNext(Bitmap response) {
                    followerDetailModel.setAvatar(response);
                }

                @Override
                public void onError() {
                    followerDetailModel.setAvatar(null);
                }
            }).execute(url);
        }
    }

    public void getImage(String url) {
        if (TextUtils.isEmpty(url)) {
            detailPresenter.showError(1);
        } else {
            new LoadImageAsyncTask(new CommunicationManager<Bitmap>() {
                @Override
                public void onResponse(String response) {

                }

                @Override
                public void onProcessNext(Bitmap response) {
                    detailPresenter.showData(response);
                }

                @Override
                public void onError() {
                    detailPresenter.showError(0);
                }
            }).execute(url);
        }
    }
}
