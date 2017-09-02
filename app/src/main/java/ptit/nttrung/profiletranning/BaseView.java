package ptit.nttrung.profiletranning;

import android.support.annotation.StringRes;

/**
 * Created by TrungNguyen on 8/29/2017.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void makeToast(@StringRes int stringId);
    void makeToast(String message);
}
