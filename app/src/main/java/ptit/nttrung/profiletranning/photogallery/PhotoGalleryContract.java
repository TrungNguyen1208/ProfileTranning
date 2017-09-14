package ptit.nttrung.profiletranning.photogallery;

import android.app.Activity;
import android.support.annotation.StringRes;

import java.util.List;

import ptit.nttrung.profiletranning.base.BasePresenter;
import ptit.nttrung.profiletranning.base.BaseView;
import ptit.nttrung.profiletranning.data.photos.Photo;

/**
 * Created by TrungNguyen on 9/3/2017.
 */

public interface PhotoGalleryContract {
    interface View extends BaseView<Presenter> {
        void setAdapterData(List<Photo> photos);

        void setNoListDataFound();

        Activity getActivityContext();

        void makeToast(@StringRes int message);

        void setPresenter(Presenter presenter);

        void startPhotoDetailActivity(String photoURL);

        void showProgressIndicator(boolean show);

    }

    interface Presenter extends BasePresenter {
        void onPhotoItemClick(int itemPosition);
    }
}
