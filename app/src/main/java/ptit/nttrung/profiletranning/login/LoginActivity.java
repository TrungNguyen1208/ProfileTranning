package ptit.nttrung.profiletranning.login;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ptit.nttrung.profiletranning.R;
import ptit.nttrung.profiletranning.util.ActivityUtils;

public class LoginActivity extends AppCompatActivity {

    private static final String LOGIN_FRAGMENT = "LOGIN_FRAGMENT";
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = getFragmentManager();
        LoginFragment fragment = (LoginFragment) manager.findFragmentByTag(LOGIN_FRAGMENT);
        if (fragment == null){
            fragment = LoginFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(manager,fragment,R.id.root_login_fragment,LOGIN_FRAGMENT);
    }
}
