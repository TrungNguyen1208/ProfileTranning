package ptit.nttrung.profiletranning.photodetail;

import android.support.annotation.StringRes;

import ptit.nttrung.profiletranning.BasePresenter;
import ptit.nttrung.profiletranning.BaseView;

/**
 * Created by TrungNguyen on 9/2/2017.
 */

public interface PhotoDetailContract {
    //You must specify Type of Presenter
    interface View extends BaseView<Presenter> {

        void setBitmap();

        void startProfilePageActivity();

        void startPhotoGalleryActivity();

        void makeToast(@StringRes int message);

        void setPresenter(Presenter presenter);

        void showProgressIndicator(boolean show);

        String getPhotoURL();
    }

    interface Presenter extends BasePresenter {
        void onBackButtonPress();

        void onDoneButtonPress();

        void onImageLoaded();

        void onImageLoadFailure();
    }
}
