package in.unicodelabs.basemvp.mvp;

import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by saurabh on 3/2/18.
 */

public interface MvpView {

    void showLoading();

    void showLoading(String laodingMessage);

    void showLoading(@StringRes int resId);

    void hideLoading();

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void showSnackbar(String message);

    void showSnackbar(@StringRes int resId);

    void onError(String message);

    void onError(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();


    //Content -load - Error view related methods
    View getContentView();
    View getLoadingView();
    View getErrorView();

    void showContentView();
    void showLoadingView();
    void showErrorView();
}
