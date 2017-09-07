package ptit.nttrung.profiletranning.data.photos;

/**
 * Created by TrungNguyen on 9/7/2017.
 */

public class PhotoInjection {
    public static PhotoSource providePhotoSource() {
        return FakePhotoService.getInstance();
    }
}
