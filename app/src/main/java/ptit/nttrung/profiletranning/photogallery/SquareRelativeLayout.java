package ptit.nttrung.profiletranning.photogallery;

/**
 * Created by TrungNguyen on 9/3/2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * A RelativeLayout that will always be square -- same width and height,
 * where the height is based off the width.
 * Courtesy of Anonsage on stackoverflow
 */
public class SquareRelativeLayout extends RelativeLayout {

    public SquareRelativeLayout(Context context) {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}