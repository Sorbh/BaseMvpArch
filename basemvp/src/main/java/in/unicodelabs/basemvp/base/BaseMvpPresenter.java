package in.unicodelabs.basemvp.base;

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.ref.WeakReference;

import in.unicodelabs.basemvp.mvp.MvpPresenter;
import in.unicodelabs.basemvp.mvp.MvpView;


public abstract class BaseMvpPresenter<V extends MvpView> implements MvpPresenter<V> {


    private WeakReference<V> mMvpView;

    public BaseMvpPresenter() {

    }

    public interface ViewAction<V> {

        /**
         * This method will be invoked to run the action. Implement this method to interact with the view.
         *
         * @param view The reference to the view. Not null.
         */
        void run(@NonNull V view);
    }

    @Override
    public void onAttach(V mMvpView) {

        this.mMvpView = new WeakReference<V>(mMvpView);
    }

    @Override
    public void onDetach() {

        if (mMvpView != null) {
            mMvpView.clear();
            mMvpView = null;
        }

    }

    @Override
    public void handleApiError() {

    }


    public V getMvpView() {
        return mMvpView == null ? null : mMvpView.get();
    }

    public boolean isViewAttached() {
        return mMvpView != null && mMvpView.get() != null;
    }

    protected final void ifViewAttached(boolean exceptionIfViewNotAttached, ViewAction<V> action) {
        final V view = mMvpView == null ? null : mMvpView.get();
        if (view != null) {
            action.run(view);
        } else if (exceptionIfViewNotAttached) {
            throw new IllegalStateException(
                    "No View attached to Presenter.");
        }else {
            Log.d("BaseMVP","No View attached to Presenter");
        }
    }

    /**
     * Calls {@link #ifViewAttached(boolean, ViewAction)} with false as first parameter (don't throw
     * exception if view not attached).
     *
     * @see #ifViewAttached(boolean, ViewAction)
     */
    protected final void ifViewAttached(ViewAction<V> action) {
        ifViewAttached(false, action);
    }
}
