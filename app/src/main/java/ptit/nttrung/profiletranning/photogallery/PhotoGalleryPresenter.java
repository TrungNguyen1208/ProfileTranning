package ptit.nttrung.profiletranning.photogallery;

import android.content.ContentResolver;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import ptit.nttrung.profiletranning.data.photos.Photo;
import ptit.nttrung.profiletranning.data.photos.PhotoSource;
import ptit.nttrung.profiletranning.util.BaseSchedulerProvider;

/**
 * Created by TrungNguyen on 9/3/2017.
 */

public class PhotoGalleryPresenter implements PhotoGalleryContract.Presenter {

    private BaseSchedulerProvider schedulerProvider;
    private PhotoSource photoSource;
    private PhotoGalleryContract.View view;
    private List<Photo> photos;
    private CompositeDisposable subscriptions;
    private ContentResolver resolver;

    public PhotoGalleryPresenter(PhotoGalleryContract.View view,
                                 PhotoSource photoSource,
                                 BaseSchedulerProvider schedulerProvider,
                                 ContentResolver resolver
    ) {
        this.photoSource = photoSource;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.subscriptions = new CompositeDisposable();
        this.resolver = resolver;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadImageURLsIfAvailable();
    }

    private void loadImageURLsIfAvailable() {
        view.showProgressIndicator(true);
        subscriptions.add(photoSource.getThumbnails(resolver)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<List<Photo>>() {
                    @Override
                    public void onSuccess(List<Photo> photos) {
                        view.showProgressIndicator(false);
                        PhotoGalleryPresenter.this.photos = photos;
                        view.setAdapterData(photos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.setNoListDataFound();
                    }

                })
        );
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }

    @Override
    public void onPhotoItemClick(int itemPosition) {
        Photo photo = photos.get(itemPosition);
        view.startPhotoDetailActivity(photo.getPhotoUri());
    }
}
