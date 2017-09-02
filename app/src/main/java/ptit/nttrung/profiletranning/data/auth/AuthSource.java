package ptit.nttrung.profiletranning.data.auth;

import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * Created by TrungNguyen on 8/29/2017.
 */

public interface AuthSource {
    Completable createAccount(Credentials cred);

    Completable attemptLogin(Credentials cred);

    Completable deleteUser();

    Maybe<User> getUser();

    Completable logUserOut();

    Completable reauthenticateUser(String password);

    void setReturnFail(boolean bool);
}
