package in.unicodelabs.basemvp.mvp;

/**
 * Created by saurabh on 3/2/18.
 */


/**
 * Every presenter in the app must either implement this interface or extend BaseMvpPresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface  MvpPresenter<V extends MvpView> {

    void onAttach(V mMvpView);

    void onDetach();

    void onDestroy();

    void handleApiError();
}
