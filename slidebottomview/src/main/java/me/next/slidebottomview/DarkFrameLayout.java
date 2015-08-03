package me.next.slidebottomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by NeXT on 15/8/3.
 */
public class DarkFrameLayout extends FrameLayout {

    public static final int MAX_ALPHA = 0X9f;

    private int alpha = 0x00;
    private Paint mFadePaint;

    public DarkFrameLayout(Context context) {
        this(context, null);
    }

    public DarkFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DarkFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFadePaint = new Paint();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mFadePaint.setColor(Color.argb(alpha, 0, 0, 0));
        canvas.drawRect(0, 0, getMeasuredWidth(), getHeight(), mFadePaint);
    }

    public void fade(boolean fade) {
        alpha = fade ? 0x8f : 0x00;
        invalidate();
    }

    public void fade(int alpha) {
        this.alpha = alpha;
        invalidate();
    }
}
