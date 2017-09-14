package ptit.nttrung.profiletranning.profiledetail;

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
 * Created by TrungNguyen on 9/3/2017.
 */

public class ProfileDetailPresenter implements ProfileDetailContract.Presenter {

    private AuthSource auth;
    private DatabaseSource database;
    private ProfileDetailContract.View view;
    private CompositeDisposable subscriptions;
    private BaseSchedulerProvider schedulerProvider;
    private Profile currentProfile;

    public ProfileDetailPresenter(AuthSource auth,
                                  ProfileDetailContract.View view,
                                  DatabaseSource database,
                                  BaseSchedulerProvider schedulerProvider
    ) {
        this.auth = auth;
        this.database = database;
        this.view = view;
        //this.rootRef = FirebaseDatabase.getInstance().getReference();
        this.subscriptions = new CompositeDisposable();
        this.schedulerProvider = schedulerProvider;
        view.setPresenter(this);


    }

    @Override
    public void subscribe() {
        getActiveUser();
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }

    @Override
    public void onBackButtonClick() {
        view.startProfilePageActivity();
    }

    @Override
    public void onDoneButtonClick() {
        currentProfile.setBio(view.getBio());
        currentProfile.setInterests(view.getInterests());

        subscriptions.add(database.updateProfile(currentProfile)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(e.getMessage());
                    }
                })
        );
    }

    private void getActiveUser() {
        subscriptions.add(auth.getUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        getUserProfile(user.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(R.string.error_no_data_found);
                    }

                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }
                })
        );
    }

    private void getUserProfile(String userId) {
        subscriptions.add(database.getProfile(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<Profile>() {
                    @Override
                    public void onSuccess(Profile profile) {
                        ProfileDetailPresenter.this.currentProfile = profile;
                        view.setBioText(profile.getBio());
                        view.setInterestsText(profile.getInterests());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(R.string.error_no_data_found);
                    }

                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }
                })
        );
    }
}
