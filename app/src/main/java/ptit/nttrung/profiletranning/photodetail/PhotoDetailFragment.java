package ptit.nttrung.profiletranning.photodetail;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ptit.nttrung.profiletranning.R;
import ptit.nttrung.profiletranning.data.auth.AuthInjection;
import ptit.nttrung.profiletranning.data.database.DatabaseInjection;
import ptit.nttrung.profiletranning.data.scheduler.SchedulerInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailFragment extends Fragment implements PhotoDetailContract.View {

    private static final String PHOTO_URL = "PHOTO_URL";

    private PhotoDetailContract.Presenter presenter;
    private ImageButton back, done;
    private ImageView photo;
    private ProgressBar progressBar;
    private String photoURL;

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }


    public PhotoDetailFragment() {
        // Required empty public constructor
    }

    public static PhotoDetailFragment newInstance(String photoURL) {
        PhotoDetailFragment f = new PhotoDetailFragment();
        Bundle args = new Bundle();
        args.putString(PHOTO_URL, photoURL);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        if (getArguments() != null) {
            this.photoURL = getArguments().getString(PHOTO_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        android.view.View v = inflater.inflate(R.layout.fragment_photo_detail, container, false);
        back = (ImageButton) v.findViewById(R.id.imb_photo_detail_back);
        done = (ImageButton) v.findViewById(R.id.imb_photo_detail_done);
        photo = (ImageView) v.findViewById(R.id.imv_photo_detail);

        progressBar = (ProgressBar) v.findViewById(R.id.pro_photo_loading);

        back.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onBackButtonPress();
            }
        });

        done.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onDoneButtonPress();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (presenter == null) {
            presenter = new PhotoDetailPresenter(AuthInjection.provideAuthSource(),
                    DatabaseInjection.provideDatabaseSource(),
                    this,
                    SchedulerInjection.provideSchedulerProvider());
        }
        presenter.subscribe();
    }

    @Override
    public void makeToast(String message) {

    }

    @Override
    public void setBitmap() {
        Picasso.with(getActivity())
                .load(photoURL)
                .fit()
                .into(photo, new Callback() {
                    @Override
                    public void onSuccess() {
                        presenter.onImageLoaded();
                    }

                    @Override
                    public void onError() {
                        presenter.onImageLoadFailure();
                    }
                });
    }

    @Override
    public void startProfilePageActivity() {

    }

    @Override
    public void startPhotoGalleryActivity() {

    }

    @Override
    public void makeToast(@StringRes int message) {
        Toast.makeText(getActivity().getApplicationContext(), getString(message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(PhotoDetailContract.Presenter presenter) {

    }

    @Override
    public void showProgressIndicator(boolean show) {

    }

    @Override
    public String getPhotoURL() {
        return null;
    }
}
