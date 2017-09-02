package ptit.nttrung.profiletranning.profilepage;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ptit.nttrung.profiletranning.R;
import ptit.nttrung.profiletranning.util.ActivityUtils;

public class ProfilePageActivity extends AppCompatActivity {

    private static final String PAGE_FRAGMENT = "PAGE_FRAGMENT";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        manager = this.getFragmentManager();

        ProfilePageFragment fragment = (ProfilePageFragment) manager.findFragmentByTag(PAGE_FRAGMENT);
        if (fragment == null){
            fragment = ProfilePageFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(manager,fragment,R.id.cont_profile_page_fragment, PAGE_FRAGMENT);

    }
}
