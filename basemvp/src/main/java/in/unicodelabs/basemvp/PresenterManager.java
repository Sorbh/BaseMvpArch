package in.unicodelabs.basemvp;

import java.util.HashMap;

import in.unicodelabs.basemvp.mvp.MvpPresenter;

/**
 * Created by saurabh on 23/2/18.
 */

public class PresenterManager {
    private final static HashMap<String,MvpPresenter> presenterPool = new HashMap<>();

    public static void putPresenter(String activityId, MvpPresenter presenter){
        presenterPool.put(activityId,presenter);
    }

    public static <P> P getPresenter(String activityId){
        if (activityId == null) {
            throw new NullPointerException("View id is null in presenter manager");
        }

        P presenter = (P)presenterPool.get(activityId);
        return presenter == null ? null : presenter;
    }

    public static void remove(String activityId){
        if (activityId == null) {
            throw new NullPointerException("View id is null  in presenter manager");
        }
        presenterPool.remove(activityId);
    }
}
