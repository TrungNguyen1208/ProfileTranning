package ptit.nttrung.profiletranning.login;

import android.support.annotation.StringRes;

import ptit.nttrung.profiletranning.base.BasePresenter;
import ptit.nttrung.profiletranning.base.BaseView;

/**
 * Created by TrungNguyen on 8/28/2017.
 */

public interface LoginContract {
    interface View extends BaseView<LoginContract.Presenter> {
        void makeToast(@StringRes int stringId);

        void makeToast(String message);

        String getEmail();

        String getPassword();

        void startProfileActivity();

        void startCreateAccountActivity();

        void setPresenter(LoginContract.Presenter presenter);

        void showProgressIndicator(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onLogInClick();

        void onCreateClick();
    }
}
