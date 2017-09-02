package ptit.nttrung.profiletranning.data.auth;

/**
 * Created by TrungNguyen on 8/29/2017.
 */

public class AuthInjection {

    public static AuthSource provideAuthSource(){
        return FirebaseAuthService.getInstance();
    }
}
