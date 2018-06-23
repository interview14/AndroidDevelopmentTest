package app.androiddevelopmenttest.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;

import app.androiddevelopmenttest.R;
import app.androiddevelopmenttest.adapter.CustomFollowerDetailAdapter;
import app.androiddevelopmenttest.model.FollowerDetailModel;
import app.androiddevelopmenttest.model.SearchDetailModel;
import app.androiddevelopmenttest.presenter.DetailPresenter;
import app.androiddevelopmenttest.presenter.DetailPresenterImpl;
import app.androiddevelopmenttest.util.Constants;

public class DetailActivity extends BaseActivity {

    private AppCompatImageView iv_user_avatar;
    private AppCompatTextView tv_user_name, tv_user_email;
    private RecyclerView rv_followers_detail;
    private SearchDetailModel searchDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getIds();
        fetchIntent();
        setUserDetail();
        getFollowersDetail();
    }

    void getIds() {
        iv_user_avatar = findViewById(R.id.iv_user_avatar);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_email = findViewById(R.id.tv_user_email);
        rv_followers_detail = findViewById(R.id.rv_followers_detail);
    }

    void fetchIntent() {
        searchDetailModel = (SearchDetailModel) getIntent().getSerializableExtra(Constants.IntentKeys.SEARCH_DETAIL_MODEL);
    }

    void setUserDetail() {
        if (TextUtils.isEmpty(searchDetailModel.getName())
                || searchDetailModel.getName().equals("null")) {
            tv_user_name.setText("Name: " + searchDetailModel.getLogin());
        } else {
            tv_user_name.setText("Name: " + searchDetailModel.getName());
        }

        if (TextUtils.isEmpty(searchDetailModel.getEmail()) ||
                searchDetailModel.getEmail().equals("null")) {
            tv_user_email.setText("Email: - ");
        } else {
            tv_user_email.setText("Email: " + searchDetailModel.getEmail());
        }

        getUserAvatar();
    }

    void getFollowersDetail() {
        DetailPresenterImpl detailPresenter = new DetailPresenterImpl(new DetailPresenter<List<FollowerDetailModel>>() {
            @Override
            public void showData(List<FollowerDetailModel> followerDetailModelList) {
                hideLoading();
                setFollowersDetail(followerDetailModelList);
            }

            @Override
            public void showError(int errorCode) {
                hideLoading();

                String error;
                switch (errorCode) {
                    case 1:
                        error = getString(R.string.follower_not_found);
                        break;
                    default:
                        error = getString(R.string.service_unavailable);
                        break;
                }
                showAlertDialog(getString(R.string.title_alert), error, DetailActivity.this);
            }
        });

        if (haveInternet(DetailActivity.this)) {
            showLoading();
            detailPresenter.getFollowers(searchDetailModel.getFollowers_url());
        } else {
            showAlertDialog(getString(R.string.title_alert), getString(R.string.internet_problem), DetailActivity.this);
        }
    }

    void setFollowersDetail(List<FollowerDetailModel> followerDetailModelList) {
        CustomFollowerDetailAdapter adapter = new CustomFollowerDetailAdapter(followerDetailModelList, this);
        rv_followers_detail.setAdapter(adapter);
        rv_followers_detail.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rv_followers_detail.setItemAnimator(itemAnimator);
    }

    void getUserAvatar() {
        DetailPresenterImpl detailPresenter = new DetailPresenterImpl(new DetailPresenter<Bitmap>() {
            @Override
            public void showData(Bitmap bitmap) {
                hideLoading();
                iv_user_avatar.setImageBitmap(bitmap);
            }

            @Override
            public void showError(int errorCode) {
                hideLoading();

                String error;
                switch (errorCode) {
                    case 1:
                        error = getString(R.string.avatar_not_found);
                        break;
                    case 2:
                        error = getString(R.string.avatar_load_error);
                        break;
                    default:
                        error = getString(R.string.service_unavailable);
                        break;
                }
                showAlertDialog(getString(R.string.title_alert), error, DetailActivity.this);
            }
        });

        if (haveInternet(DetailActivity.this)) {
            showLoading();
            detailPresenter.getImage(searchDetailModel.getAvatar_url());
        } else {
            showAlertDialog(getString(R.string.title_alert), getString(R.string.internet_problem), DetailActivity.this);
        }
    }
}
