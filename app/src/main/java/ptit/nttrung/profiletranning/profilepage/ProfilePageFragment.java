package ptit.nttrung.profiletranning.profilepage;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import ptit.nttrung.profiletranning.R;
import ptit.nttrung.profiletranning.data.auth.AuthInjection;
import ptit.nttrung.profiletranning.data.database.DatabaseInjection;
import ptit.nttrung.profiletranning.data.scheduler.SchedulerInjection;
import ptit.nttrung.profiletranning.login.LoginActivity;
import ptit.nttrung.profiletranning.photogallery.PhotoGalleryActivity;
import ptit.nttrung.profiletranning.profiledetail.ProfileDetailActivity;
import ptit.nttrung.profiletranning.profilesettings.ProfileSettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilePageFragment extends Fragment implements ProfilePageContract.View {

    @BindView(R.id.imb_page_logout)
    ImageButton imbLogout;
    @BindView(R.id.imb_page_user_settings)
    ImageButton imbUserSettings;
    @BindView(R.id.imb_page_thumbnail)
    CircleImageView imbThumbnail;
    @BindView(R.id.lbl_page_user_name)
    TextView lbUserName;
    @BindView(R.id.lbl_page_user_email)
    TextView lblUserEmail;
    @BindView(R.id.pro_profile_page_thumbnail)
    ProgressBar proProfilePageThumbnail;
    @BindView(R.id.lbl_page_user_bio_sub)
    TextView lblUserBioSub;
    @BindView(R.id.lbl_page_user_bio)
    TextView lblUserBio;
    @BindView(R.id.pro_profile_page_bio)
    ProgressBar proProfilePageBio;
    @BindView(R.id.lbl_page_user_interests_sub)
    TextView lblUserInterestsSub;
    @BindView(R.id.lbl_page_user_interests)
    TextView lblUserInterests;
    @BindView(R.id.pro_profile_page_interests)
    ProgressBar proProfilePageInterests;
    @BindView(R.id.fab_edit_profile_details)
    FloatingActionButton fabEditDetails;

    Unbinder unbinder;

    private ProfilePageContract.Presenter presenter;

    public ProfilePageFragment() {

    }

    public static ProfilePageFragment newInstance(){
        ProfilePageFragment fragment = new ProfilePageFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_page, container, false);
        unbinder = ButterKnife.bind(this, v);

        imbThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onThumbnailClick();
            }
        });

        imbUserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAccountSettingsClick();
            }
        });

        imbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLogoutClick();

            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (presenter == null) {
            presenter = new ProfilePagePresenter(
                    AuthInjection.provideAuthSource(),
                    this,
                    DatabaseInjection.provideDatabaseSource(),
                    SchedulerInjection.provideSchedulerProvider()
            );
        }
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void makeToast(@StringRes int stringId) {
        Toast.makeText(getActivity(), getString(stringId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(ProfilePageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setName(String name) {
        lbUserName.setText(name);
    }

    @Override
    public void setEmail(String email) {
        lblUserEmail.setText(email);
    }

    @Override
    public void setBio(String bio) {
        lblUserBio.setText(bio);
    }

    @Override
    public void setInterests(String interests) {
        if (interests.equals("")){
            lblUserInterests.setText(getString(R.string.default_interests));
        } else {
            lblUserInterests.setText(interests);
        }
    }

    @Override
    public void setProfilePhotoURL(String profilePhotoURL) {
        Picasso.with(getActivity())
                .load(Uri.parse(profilePhotoURL))
                .noFade()
                .into(imbThumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                        presenter.onThumbnailLoaded();
                    }

                    @Override
                    public void onError() {
                        setDefaultProfilePhoto();
                    }
                });
        //CircleImageView requires noFade() to be set
    }

    @Override
    public void setDefaultProfilePhoto() {
        Picasso.with(getActivity())
                .load(R.drawable.default_profile_pic)
                .noFade()
                .into(imbThumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                        presenter.onThumbnailLoaded();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(
                                getActivity(),
                                getString(R.string.error_loading_image),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void startPhotoGalleryActivity() {
        Intent i = new Intent(getActivity(), PhotoGalleryActivity.class);
        startActivity(i);
    }

    @Override
    public void startProfileDetailActivity() {
        Intent i = new Intent(getActivity(), ProfileDetailActivity.class);
        startActivity(i);
    }

    @Override
    public void startProfileSettingsActivity() {
        Intent i = new Intent(getActivity(), ProfileSettingsActivity.class);
        startActivity(i);
    }

    @Override
    public void showLogoutSnackbar() {
        Snackbar.make(getView(),getString(R.string.action_log_out),Snackbar.LENGTH_SHORT)
                .setAction(R.string.action_log_out, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.onLogoutConfirmed();
                    }
                })
                .show();
    }

    @Override
    public void startLoginActivity() {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void setThumbnailLoadingIndicator(boolean show) {
        if (show){
            proProfilePageThumbnail.setVisibility(View.VISIBLE);
            imbThumbnail.setVisibility(View.INVISIBLE);
            lbUserName.setVisibility(View.INVISIBLE);
            lblUserEmail.setVisibility(View.INVISIBLE);
        } else {
            proProfilePageThumbnail.setVisibility(View.INVISIBLE);
            imbThumbnail.setVisibility(View.VISIBLE);
            lbUserName.setVisibility(View.VISIBLE);
            lblUserEmail.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setDetailLoadingIndicators(boolean show) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
