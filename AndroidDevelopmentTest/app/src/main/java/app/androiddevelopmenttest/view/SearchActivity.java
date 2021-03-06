package app.androiddevelopmenttest.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import app.androiddevelopmenttest.R;
import app.androiddevelopmenttest.model.SearchDetailModel;
import app.androiddevelopmenttest.presenter.SearchPresenter;
import app.androiddevelopmenttest.presenter.SearchPresenterImpl;
import app.androiddevelopmenttest.util.Constants;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    AppCompatEditText et_search;
    AppCompatImageButton ib_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getIds();
    }

    void getIds() {
        et_search = findViewById(R.id.et_search);
        ib_search = findViewById(R.id.ib_search);
        ib_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        SearchPresenterImpl searchPresenter = new SearchPresenterImpl(new SearchPresenter() {
            @Override
            public void showDetail(SearchDetailModel searchDetailModel) {
                hideLoading();
                moveToDetailScreen(searchDetailModel);
            }

            @Override
            public void showError(int errorCode) {
                hideLoading();

                String error;
                switch (errorCode) {
                    case 1:
                        error = getString(R.string.empty_search_field);
                        break;
                    case 2:
                        error = getString(R.string.user_not_found);
                        break;
                    default:
                        error = getString(R.string.service_unavailable);
                        break;
                }
                showAlertDialog(getString(R.string.title_alert), error, SearchActivity.this);
            }
        });

        if (haveInternet(SearchActivity.this)) {
            showLoading(getString(R.string.seaching));
            searchPresenter.doSearch(et_search.getText().toString());
        } else {
            showAlertDialog(getString(R.string.title_alert), getString(R.string.internet_problem), SearchActivity.this);
        }
    }

    void moveToDetailScreen(SearchDetailModel searchDetailModel) {
        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
        intent.putExtra(Constants.IntentKeys.SEARCH_DETAIL_MODEL, searchDetailModel);
        startActivityForResult(intent, 0);
    }

    void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        et_search.setText("");
    }
}
