package ptit.nttrung.profiletranning.data.database;

import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * Created by TrungNguyen on 8/29/2017.
 */

public interface DatabaseSource {
    /**
     * Create a profile to write to the Firebase Cloud Database
     * Completable chi quan tam den loi goi thanh cong.
     * Khong quan tam den phan tu cua Observer
     */
    Completable createProfile(Profile profile);

    //Biet se nhan ve mot doi tuong
    Maybe<Profile> getProfile(String uid);

    Completable deleteProfile(String uid);

    Completable updateProfile(Profile profile);

    void setReturnFail(boolean bool);

    void setReturnEmpty(boolean bool);

}