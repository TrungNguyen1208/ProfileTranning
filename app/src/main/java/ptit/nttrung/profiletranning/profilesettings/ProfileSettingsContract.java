package ptit.nttrung.profiletranning.profilesettings;

import ptit.nttrung.profiletranning.base.BasePresenter;
import ptit.nttrung.profiletranning.base.BaseView;

/**
 * Created by TrungNguyen on 9/3/2017.
 */

public class ProfileSettingsContract {
    public interface View extends BaseView<Presenter> {
        void startLogInActivity();

        void showAuthCard(boolean show);

        void showProgressIndicator(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onDeleteAccountPress();

        void onDeleteAccountConfirmed(String password);
    }
}
