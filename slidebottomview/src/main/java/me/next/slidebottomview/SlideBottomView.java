package me.next.slidebottomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by NeXT on 15/8/3.
 */
public class SlideBottomView extends FrameLayout {

    private static final int DEFAULT_BACKGROUND_ID = -1;

    private int mBackgroundId;

    private Context mContext;
    private DarkFrameLayout mDarkFrameLayout;

    public SlideBottomView(Context context) {
        this(context, null);
    }

    public SlideBottomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideBottomView, defStyleAttr, 0);

        mBackgroundId = a.getResourceId(R.styleable.SlideBottomView_sbv_background_layout, DEFAULT_BACKGROUND_ID);

        a.recycle();

        mContext = context;
        initBackgroundView();
    }

    private void initBackgroundView() {
        if (mBackgroundId != -1) {
            mDarkFrameLayout = new DarkFrameLayout(mContext);
            mDarkFrameLayout.addView(LayoutInflater.from(mContext).inflate(mBackgroundId, null));
            addView(mDarkFrameLayout);
        }
    }

}
