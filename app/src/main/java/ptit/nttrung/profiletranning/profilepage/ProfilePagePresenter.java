package ptit.nttrung.profiletranning.profilepage;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import ptit.nttrung.profiletranning.R;
import ptit.nttrung.profiletranning.data.auth.AuthSource;
import ptit.nttrung.profiletranning.data.auth.User;
import ptit.nttrung.profiletranning.data.database.DatabaseSource;
import ptit.nttrung.profiletranning.data.database.Profile;
import ptit.nttrung.profiletranning.util.BaseSchedulerProvider;

/**
 * Created by TrungNguyen on 8/28/2017.
 */

public class ProfilePagePresenter implements ProfilePageContract.Presenter{

    private DatabaseSource databaseSource;
    private AuthSource authSource;
    private ProfilePageContract.View view;
    private User currentUser;
    private CompositeDisposable compositeDisposable;
    private final BaseSchedulerProvider schedulerProvider;

    public ProfilePagePresenter(AuthSource authSource,
                                ProfilePageContract.View view,
                                DatabaseSource databaseSource,
                                BaseSchedulerProvider schedulerProvider){
        this.authSource = authSource;
        this.view = view;
        this.databaseSource = databaseSource;
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void onThumbnailClick() {
        view.startPhotoGalleryActivity();
    }

    @Override
    public void onEditProfileClick() {
        view.startProfileDetailActivity();
    }

    @Override
    public void onLogoutClick() {
        view.showLogoutSnackbar();
    }

    @Override
    public void onLogoutConfirmed() {
        compositeDisposable.add(
                authSource.logUserOut()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.startLoginActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(e.getMessage());
                    }
                })
        );
    }

    @Override
    public void onAccountSettingsClick() {
        view.startProfileSettingsActivity();
    }

    @Override
    public void onThumbnailLoaded() {
        view.setThumbnailLoadingIndicator(false);
    }

    @Override
    public void subscribe() {
        getUserData();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void getUserData() {
        view.setThumbnailLoadingIndicator(true);
        view.setDetailLoadingIndicators(true);
        compositeDisposable.add(
                authSource.getUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        ProfilePagePresenter.this.currentUser = user;
                        getUserProfileFromDatabase();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(R.string.error_retrieving_data);
                        view.startLoginActivity();
                    }

                    @Override
                    public void onComplete() {
                        view.makeToast(R.string.error_retrieving_data);
                        view.startLoginActivity();
                    }
                })
        );
    }

    private void getUserProfileFromDatabase(){
        compositeDisposable.add(
                databaseSource.getProfile(currentUser.getUserId())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<Profile>() {
                    @Override
                    public void onSuccess(Profile profile) {
                        view.setBio(profile.getBio());
                        view.setInterests(profile.getInterests());
                        view.setName(profile.getName());
                        view.setEmail(profile.getEmail());

                        view.setDetailLoadingIndicators(false);
                        String photoURL = profile.getPhotoURL();
                        if (photoURL.equals("")){
                            view.setDefaultProfilePhoto();
                        } else {
                            view.setProfilePhotoURL(profile.getPhotoURL());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(e.getMessage());
                        view.startLoginActivity();
                    }

                    @Override
                    public void onComplete() {
                        view.startLoginActivity();
                    }
                })
        );
    }
}
