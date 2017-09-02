package ptit.nttrung.profiletranning.createaccount;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import ptit.nttrung.profiletranning.R;
import ptit.nttrung.profiletranning.data.auth.AuthSource;
import ptit.nttrung.profiletranning.data.auth.Credentials;
import ptit.nttrung.profiletranning.data.auth.User;
import ptit.nttrung.profiletranning.data.database.DatabaseSource;
import ptit.nttrung.profiletranning.data.database.Profile;
import ptit.nttrung.profiletranning.util.BaseSchedulerProvider;

/**
 * Created by TrungNguyen on 8/28/2017.
 */

public class CreateAccountPresenter implements CreateAccountContract.Presenter {

    private AuthSource auth;
    private CreateAccountContract.View view;
    private CompositeDisposable disposableSubscriptions;
    private BaseSchedulerProvider schedulerProvider;
    private DatabaseSource database;

    public CreateAccountPresenter(AuthSource auth,
                                  DatabaseSource database,
                                  CreateAccountContract.View view,
                                  BaseSchedulerProvider schedulerProvider) {
        this.auth = auth;
        this.database = database;
        this.view = view;
        this.disposableSubscriptions = new CompositeDisposable();
        this.schedulerProvider = schedulerProvider;
        view.setPresenter(this);
    }

    @Override
    public void onCreateAccountClick() {
        if (validateAccountCredentials(view.getName(),
                view.getEmail(),
                view.getPassword(),
                view.getPasswordConfirmation())) {
            attemptAccountCreation(new Credentials
                    (view.getPassword(), view.getName(), view.getName()));
        } else {

        }
    }

    private void attemptAccountCreation(Credentials cred) {
        view.showProgressIndicator(true);
        disposableSubscriptions.add(
                auth.createAccount(cred)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                getUser();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(e.toString());
                            }
                        })
        );
    }

    public void getUser() {
        disposableSubscriptions.add(
                auth.getUser()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableMaybeObserver<User>() {
                            @Override
                            public void onSuccess(User user) {
                                addUserProfileToDatabase(user.getEmail(), user.getUserId());
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                view.showProgressIndicator(false);
                            }
                        })
        );
    }

    private void addUserProfileToDatabase(String email,String uid){
        final Profile profile = new Profile("","",uid,email,"",view.getName());
        disposableSubscriptions.add(
                database.createProfile(profile)
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

    @Override
    public void subscribe() {
        //Presenter doesn't need initialization in this case.
    }

    @Override
    public void unsubscribe() {
        disposableSubscriptions.clear();
    }

    public boolean validateAccountCredentials(String name, String email,
                                              String password, String passwordConfirmation) {
        if (email.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
            return false;
        } else if (name.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
            return false;
        } else if (password.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
            return false;
        } else if (passwordConfirmation.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
            return false;
        } else if (!email.contains("@")) {
            view.makeToast(R.string.error_invalid_email);
            return false;
        } else if (password.length() > 19) {
            view.makeToast(R.string.error_password_too_long);
            return false;
        } else if (!passwordConfirmation.equals(password)) {
            view.makeToast(R.string.error_password_mismatch);
            return false;
        } else {
            return true;
        }
    }
}
