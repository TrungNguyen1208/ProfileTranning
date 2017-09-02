package ptit.nttrung.profiletranning.data.photos;

/**
 * Created by TrungNguyen on 9/2/2017.
 */

public class Photo {
    private String photoUri;

    public Photo(String photoUri){
        this.photoUri = photoUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
