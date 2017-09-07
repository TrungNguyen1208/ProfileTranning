package ptit.nttrung.profiletranning.createaccount;

import android.support.annotation.StringRes;

import ptit.nttrung.profiletranning.base.BasePresenter;
import ptit.nttrung.profiletranning.base.BaseView;

/**
 * Created by TrungNguyen on 8/28/2017.
 */

public interface CreateAccountContract {
    interface View extends BaseView<Presenter> {
        void makeToast(@StringRes int stringId);

        String getEmail();

        String getPassword();

        String getPasswordConfirmation();

        String getName();

        //TODO: add name input to this component

        void startLoginActivity();

        void startProfilePageActivity();

        void setPresenter(CreateAccountContract.Presenter presenter);

        void showProgressIndicator(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onCreateAccountClick();

        void subscribe();

        void unsubscribe();
    }
}
