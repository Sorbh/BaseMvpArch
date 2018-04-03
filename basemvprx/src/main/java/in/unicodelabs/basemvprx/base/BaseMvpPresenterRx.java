package in.unicodelabs.basemvprx.base;

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.ref.WeakReference;

import in.unicodelabs.basemvp.base.BaseMvpPresenter;
import in.unicodelabs.basemvp.mvp.MvpPresenter;
import in.unicodelabs.basemvp.mvp.MvpView;
import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseMvpPresenterRx<V extends MvpView> extends BaseMvpPresenter<V> {

    private CompositeDisposable mCompositeDisposable;

    public CompositeDisposable getCompositeDisposable() {
        if (mCompositeDisposable == null)
            mCompositeDisposable = new CompositeDisposable();
        return mCompositeDisposable;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        unsubscribe();
    }

    public void unsubscribe() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }

    @Override
    public void handleApiError() {

    }

}
