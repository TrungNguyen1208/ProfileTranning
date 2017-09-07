package ptit.nttrung.profiletranning.data.photos;

import android.content.ContentResolver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by TrungNguyen on 9/7/2017.
 */

public class FakePhotoService implements PhotoSource {
    boolean returnFailure = false;

    public FakePhotoService(){

    }

    public static FakePhotoService getInstance (){
        return new FakePhotoService();
    }

    @Override
    public void setReturnFail(boolean returnFailure){
        this.returnFailure = returnFailure;
    }

    @Override
    public Maybe<List<Photo>> getThumbnails(ContentResolver resolver) {
        if (returnFailure){
            return Maybe.empty();
        } else {
            List<Photo> list = new ArrayList<>();
            for (int i = 0; i < 9; i++){
                list.add(new Photo("photo-" + Integer.toString(i)));
            }
            return Maybe.just(list);
        }
    }
}
