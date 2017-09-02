package ptit.nttrung.profiletranning.data.auth;

/**
 * Created by TrungNguyen on 8/29/2017.
 */

public class Credentials {
    private String password;
    private String username;
    private String email;

    public Credentials() {
    }

    public Credentials(String password, String username, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
