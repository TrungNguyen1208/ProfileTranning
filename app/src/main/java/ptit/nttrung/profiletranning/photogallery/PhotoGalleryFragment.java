package ptit.nttrung.profiletranning.photogallery;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ptit.nttrung.profiletranning.R;
import ptit.nttrung.profiletranning.data.photos.Photo;
import ptit.nttrung.profiletranning.data.photos.PhotoInjection;
import ptit.nttrung.profiletranning.data.scheduler.SchedulerInjection;
import ptit.nttrung.profiletranning.photodetail.PhotoDetailActivity;
import ptit.nttrung.profiletranning.profilepage.ProfilePageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoGalleryFragment extends Fragment implements PhotoGalleryContract.View {

    private static final String EXTRA_PHOTO_URL = "EXTRA_PHOTO_URL";
    private PhotoGalleryContract.Presenter presenter;
    private GalleryAdapter adapter;
    private RecyclerView photoGallery;
    private TextView noData;
    private ProgressBar progressBar;
    private ImageButton back;

    public PhotoGalleryFragment() {
    }

    public static PhotoGalleryFragment newInstance() {
        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        photoGallery = (RecyclerView) v.findViewById(R.id.rec_gallery_gallery);
        photoGallery.setLayoutManager(new GridLayoutManager(getActivityContext(), 2));
        progressBar = (ProgressBar) v.findViewById(R.id.pro_gallery_loading);
        noData = (TextView) v.findViewById(R.id.lbl_gallery_no_data);
        back = (ImageButton) v.findViewById(R.id.imb_gallery_back);
        back.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                startProfilePageActivity();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (presenter == null) {
            presenter = new PhotoGalleryPresenter(
                    this,
                    PhotoInjection.providePhotoSource(),
                    SchedulerInjection.provideSchedulerProvider(),
                    getActivity().getContentResolver()
                    //TODO figure out what happens to content resolver when activity destroyed
            );
        }

        presenter.subscribe();
    }

    private void startProfilePageActivity() {
        Intent i = new Intent(getActivity(), ProfilePageActivity.class);
        startActivity(i);
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdapterData(List<Photo> photos) {
        adapter = new GalleryAdapter(photos, getActivity(), presenter);
        photoGallery.setAdapter(adapter);
    }

    @Override
    public void setNoListDataFound() {
        progressBar.setVisibility(android.view.View.INVISIBLE);
        photoGallery.setVisibility(android.view.View.INVISIBLE);
        noData.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public Activity getActivityContext() {
        return getActivity();
    }

    @Override
    public void makeToast(@StringRes int message) {

    }

    @Override
    public void setPresenter(PhotoGalleryContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void startPhotoDetailActivity(String photoURL) {
        Intent i = new Intent(getActivity(), PhotoDetailActivity.class);
        i.putExtra(EXTRA_PHOTO_URL, photoURL);
        startActivity(i);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        if (show) {
            progressBar.setVisibility(android.view.View.VISIBLE);
            photoGallery.setVisibility(android.view.View.INVISIBLE);
            noData.setVisibility(android.view.View.INVISIBLE);
        } else {
            progressBar.setVisibility(android.view.View.INVISIBLE);
            photoGallery.setVisibility(android.view.View.VISIBLE);
            noData.setVisibility(android.view.View.INVISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }
}
