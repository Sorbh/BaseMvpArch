package in.unicodelabs.basemvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.UUID;

import in.unicodelabs.basemvp.PresenterManager;
import in.unicodelabs.basemvp.R;
import in.unicodelabs.basemvp.mvp.MvpPresenter;
import in.unicodelabs.basemvp.mvp.MvpView;
import in.unicodelabs.basemvp.utils.DialogUtils;
import in.unicodelabs.basemvp.utils.KeyboardUtils;


public abstract class BaseMvpFragment<P extends MvpPresenter> extends Fragment implements MvpView {
    private static final String KEY_ACTIVITY_ID = "in.unicode.fragment.id";
    private String fragmentId = "";

    protected ProgressDialog mProgressDialog;
    public P mPresenter;
    public Context context;


    public abstract P createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();

        if (savedInstanceState != null) {
            //Activity recreated - get activity id from bundle and then presenter from presenter manager
            fragmentId = savedInstanceState.getString(KEY_ACTIVITY_ID);
            mPresenter = PresenterManager.getPresenter(fragmentId);

            if (fragmentId != null && mPresenter != null) {
                //Presenter Restored from cache
            } else {

                //Incase presenter or fragmentId is null, recreate both and save it in presenter manager
                mPresenter = createPresenter();
                fragmentId = UUID.randomUUID().toString();

                PresenterManager.putPresenter(fragmentId, mPresenter);
            }

        } else {

            //Activity is created - Create presenter and activity ID and save it in presenter managet
            mPresenter = createPresenter();
            fragmentId = UUID.randomUUID().toString();

            PresenterManager.putPresenter(fragmentId, mPresenter);
        }


        mPresenter.onAttach(this);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Check if activity is finishing or not
        //isChangingConfigurations() is called during orientation change, we want to save presenter in that case
        if (getActivity().isFinishing() && !getActivity().isChangingConfigurations()) {
            if (mPresenter != null) {
                mPresenter.onDetach();
            }

            PresenterManager.remove(fragmentId);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState != null) {
            outState.putString(KEY_ACTIVITY_ID, fragmentId);
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = DialogUtils.showLoadingDialog(context);
    }

    @Override
    public void showLoading(String loading_message) {
        hideLoading();
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
        DialogUtils.infoPopup(context, getString(resId), getString(R.string.ok));
    }

    @Override
    public void onError(String message) {
        if (message != null)
            DialogUtils.infoPopup(context, message, getString(R.string.ok));
        else
            DialogUtils.infoPopup(context, getString(R.string.some_error), getString(R.string.ok));
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snack.setDuration(R.integer.snack_duration);
        snack.show();
    }

    @Override
    public void showSnackbar(int resId) {
        showSnackbar(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideKeyboard(context);
    }

    @Override
    public void startActivity(Intent intent) {
        getActivity().startActivity(intent);
    }


    //Content -load - Error view related methods
    @Override
    public View getContentView() {
        return getView().findViewById(R.id.contentView);
    }

    @Override
    public View getLoadingView() {
        return getView().findViewById(R.id.loadingView);
    }

    @Override
    public View getErrorView() {
        return getView().findViewById(R.id.errorView);
    }

    @Override
    public void showContentView() {
        View contentView = getContentView();
        View loadingView = getLoadingView();
        View errorView = getErrorView();

        if(contentView ==null || loadingView == null || errorView ==null){
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

        if(contentView ==null || loadingView == null || errorView ==null){
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

        if(contentView ==null || loadingView == null || errorView ==null){
            throw new NullPointerException("In Content,Loading or Error view, One of the view is null, Please check the xml for repective view ids");
        }

        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}
