package ptit.nttrung.profiletranning.data.photos;

/**
 * Created by TrungNguyen on 9/2/2017.
 */

public class PhotoInjection {
    public static PhotoSource providePhotoSource() {
        return PhotoService.getInstance();
    }
}
