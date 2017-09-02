package ptit.nttrung.profiletranning.data.auth;


public class AuthInjection {

    public static AuthSource provideAuthSource(){
        return FirebaseAuthService.getInstance();
    }

}