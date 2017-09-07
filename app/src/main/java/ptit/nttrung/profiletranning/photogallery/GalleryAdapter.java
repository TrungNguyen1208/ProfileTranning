package ptit.nttrung.profiletranning.photogallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ptit.nttrung.profiletranning.R;
import ptit.nttrung.profiletranning.data.photos.Photo;

/**
 * Created by TrungNguyen on 9/6/2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PhotoHoloder> {

    private List<Photo> photos;
    private Context context;
    private LayoutInflater inflater;
    private PhotoGalleryContract.Presenter presenter;

    public GalleryAdapter(List<Photo> photos, Context context,PhotoGalleryContract.Presenter presenter) {
        this.photos = photos;
        this.context = context;
        this.presenter = presenter;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public PhotoHoloder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_photo, parent, false);
        return new PhotoHoloder(view);
    }

    @Override
    public void onBindViewHolder(PhotoHoloder holder, int position) {
        final  Photo photo = photos.get(position);

        //TODO: figure out a way to solve resizing which works on all device sizes
        Picasso.with(context)
                .load(photo.getPhotoUri())
                .resize(600,600)
                .centerCrop()
                .into(holder.imbPhotoThumbnail);

//        holder.imbPhotoThumbnail.setImageBitmap(photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class PhotoHoloder extends RecyclerView.ViewHolder {

        @BindView(R.id.imb_photo_thumbnail)
        ImageButton imbPhotoThumbnail;

        public PhotoHoloder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imbPhotoThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onPhotoItemClick(getAdapterPosition());
                }
            });
        }
    }

}