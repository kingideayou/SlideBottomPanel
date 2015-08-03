package me.next.slidebottomview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by NeXT on 15/8/3.
 */
public class SlideBottomView extends FrameLayout {

    public SlideBottomView(Context context) {
        this(context, null);
    }

    public SlideBottomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
