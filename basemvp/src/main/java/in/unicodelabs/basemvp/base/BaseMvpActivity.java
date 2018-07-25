package in.unicodelabs.basemvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.UUID;

import in.unicodelabs.basemvp.PresenterManager;
import in.unicodelabs.basemvp.R;
import in.unicodelabs.basemvp.mvp.MvpPresenter;
import in.unicodelabs.basemvp.mvp.MvpView;
import in.unicodelabs.basemvp.utils.DialogUtils;
import in.unicodelabs.basemvp.utils.KeyboardUtils;


public abstract class BaseMvpActivity<P extends MvpPresenter> extends AppCompatActivity implements MvpView {

    private static final String KEY_ACTIVITY_ID = "in.unicode.acitivity.id";
    private String activityId = "";

    protected ProgressDialog mProgressDialog;
    public P mPresenter;
    public Context context;


    public abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        if (savedInstanceState != null) {
            //Activity recreated - get activity id from bundle and then presenter from presenter managet
            activityId = savedInstanceState.getString(KEY_ACTIVITY_ID);
            mPresenter = PresenterManager.getPresenter(activityId);

            if (activityId != null && mPresenter != null) {
                //Presenter Restored from cache
            } else {

                //Incase presenter or activityId is null, recreate both and save it in presenter manager
                mPresenter = createPresenter();
                activityId = UUID.randomUUID().toString();

                PresenterManager.putPresenter(activityId, mPresenter);
            }

        } else {

            //Activity is created - Create presenter and activity ID and save it in presenter manager
            mPresenter = createPresenter();
            activityId = UUID.randomUUID().toString();

            PresenterManager.putPresenter(activityId, mPresenter);
        }


        mPresenter.onAttach(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Check if onDestroy method is called because of orientation change, and activiy is not finishing. In that case keep the presenter
        //only detach the view from presenter mPresenter.onDetach()
        //keepPresenter = check if activity changing Configurations || check if activity is not finishing
        boolean keepPresenter = (isChangingConfigurations() || !isFinishing());

        if (mPresenter != null) mPresenter.onDetach();

        //Activity is destroying, destroy the presenter too
        if (!keepPresenter) {
            if (mPresenter != null) {
                mPresenter.onDestroy();
            }
            PresenterManager.remove(activityId);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState != null) {
            outState.putString(KEY_ACTIVITY_ID, activityId);
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        hideKeyboard();
        mProgressDialog = DialogUtils.showLoadingDialog(context);
    }

    @Override
    public void showLoading(String loading_message) {
        hideLoading();
        hideKeyboard();
        mProgressDialog = DialogUtils.showLoadingDialog(context, loading_message);
    }

    @Override
    public void showLoading(int resId) {
        showLoading(getString(resId));
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void showMessage(String message) {
        hideKeyboard();
        if (message != null)
            DialogUtils.infoPopup(context, message, getString(R.string.ok));
        else
            DialogUtils.infoPopup(context, getString(R.string.some_error), getString(R.string.ok));
    }

    @Override
    public void showMessage(int resId) {
        onError(getString(resId));
    }


    @Override
    public void onError(int resId) {
        hideKeyboard();
        DialogUtils.infoPopup(context, getString(resId), getString(R.string.ok));
    }

    @Override
    public void onError(String message) {
        hideKeyboard();
        if (message != null)
            DialogUtils.infoPopup(context, message, getString(R.string.ok));
        else
            DialogUtils.infoPopup(context, getString(R.string.some_error), getString(R.string.ok));
    }


    @Override
    public void showSnackbar(String message) {
        hideKeyboard();
        final Snackbar snack = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snack.setDuration(Snackbar.LENGTH_SHORT);
        snack.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snack.dismiss();
            }
        });
        snack.show();
    }

    @Override
    public void showSnackbar(int resId) {
        showSnackbar(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        //You logic the detects network connection
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideKeyboard(context);
    }


    //Content -load - Error view related methods
    @Override
    public View getContentView() {
        return findViewById(R.id.contentView);
    }

    @Override
    public View getLoadingView() {
        return findViewById(R.id.loadingView);
    }

    @Override
    public View getErrorView() {
        return findViewById(R.id.errorView);
    }

    @Override
    public void showContentView() {
        View contentView = getContentView();
        View loadingView = getLoadingView();
        View errorView = getErrorView();

        if (contentView == null || loadingView == null || errorView == null) {
            throw new NullPointerException("In Content,Loading or Error view, One of the view is null, Please check the xml for repective view ids");
        }

        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);

    }

    @Override
    public void showLoadingView() {
        View contentView = getContentView();
        View loadingView = getLoadingView();
        View errorView = getErrorView();

        if (contentView == null || loadingView == null || errorView == null) {
            throw new NullPointerException("In Content,Loading or Error view, One of the view is null, Please check the xml for repective view ids");
        }

        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        View contentView = getContentView();
        View loadingView = getLoadingView();
        View errorView = getErrorView();

        if (contentView == null || loadingView == null || errorView == null) {
            throw new NullPointerException("In Content,Loading or Error view, One of the view is null, Please check the xml for repective view ids");
        }

        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}
