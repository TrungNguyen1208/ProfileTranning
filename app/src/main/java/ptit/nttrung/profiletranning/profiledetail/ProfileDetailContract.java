package ptit.nttrung.profiletranning.profiledetail;

import android.support.annotation.StringRes;

import ptit.nttrung.profiletranning.base.BasePresenter;
import ptit.nttrung.profiletranning.base.BaseView;

/**
 * Created by TrungNguyen on 9/3/2017.
 */

public class ProfileDetailContract {
    public interface View extends BaseView<Presenter> {

        void setBioText(String bio);

        void setInterestsText(String interests);

        String getInterests();

        String getBio();

        void startProfilePageActivity();

        void setPresenter(Presenter presenter);

        void makeToast(@StringRes int message);
    }

    interface Presenter extends BasePresenter {
        void onBackButtonClick();

        void onDoneButtonClick();
    }
}
