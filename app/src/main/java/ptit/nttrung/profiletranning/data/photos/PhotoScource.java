package ptit.nttrung.profiletranning.data.photos;

import android.content.ContentResolver;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by TrungNguyen on 9/2/2017.
 */

public interface PhotoScource {
    Maybe<List<Photo>> getThumbnails(ContentResolver resolver);

    void setReturnFail(boolean bool);
}
