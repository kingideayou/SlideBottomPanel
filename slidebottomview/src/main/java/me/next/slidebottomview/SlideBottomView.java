package me.next.slidebottomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * Created by NeXT on 15/8/3.
 */
public class SlideBottomView extends FrameLayout {

    private static final int TAG_BACKGROUND = 1;
    private static final int TAG_PANEL = 2;


    private static final int DEFAULT_BACKGROUND_ID = -1;
    private static final int DEFAULT_TITLE_HEIGHT_NO_DISPLAY = 60;
    private static final int DEFAULT_PANEL_HEIGHT = 380;
    private static final boolean DEFAULT_BOUNDARY = false;

    private int mChildCount;
    private float mDensity;
    private boolean isPanelShow = false;

    private float xVelocity;
    private float yVelocity;
    private float mTouchSlop;
    private int mMaxVelocity;
    private int mMinVelocity;
    private VelocityTracker mVelocityTracker;

    private int mMeasureHeight;
    private float firstDownX;
    private float firstDownY;
    private float downX;
    private float downY;
    private float deltaY;
    private long mPressStartTime;
    private boolean isDragging = false;

    private int mBackgroundId;
    private float mPanelHeight;
    private float mTitleHeightNoDisplay;
    private boolean mBoundary = false;

    private boolean isPanelOnTouch = false;

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

        mContext = context;
        mDensity = getResources().getDisplayMetrics().density;

        ViewConfiguration vc = ViewConfiguration.get(mContext);
        mMaxVelocity = vc.getScaledMaximumFlingVelocity();
        mMinVelocity = vc.getScaledMinimumFlingVelocity();
        mTouchSlop = vc.getScaledTouchSlop();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideBottomView, defStyleAttr, 0);

        mBackgroundId = a.getResourceId(R.styleable.SlideBottomView_sbv_background_layout, DEFAULT_BACKGROUND_ID);
        mPanelHeight = a.getDimension(R.styleable.SlideBottomView_sbv_panel_height, dp2px(DEFAULT_PANEL_HEIGHT));
        mBoundary = a.getBoolean(R.styleable.SlideBottomView_sbv_boundary, DEFAULT_BOUNDARY);
        mTitleHeightNoDisplay = a.getDimension(R.styleable.SlideBottomView_sbv_title_height_no_display,dp2px(DEFAULT_TITLE_HEIGHT_NO_DISPLAY));

        a.recycle();

        initBackgroundView();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mChildCount = getChildCount();
        int t = (int) (mMeasureHeight - mTitleHeightNoDisplay);
        for (int i = 0; i < mChildCount; i++) {
            View childView = getChildAt(i);
            if (childView.getTag() == null ||
                    (int) childView.getTag() != TAG_BACKGROUND) {
                childView.layout(0, t, childView.getMeasuredWidth(), childView.getMeasuredHeight() + t);
                childView.setTag(TAG_PANEL);
            } else {
                childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
                childView.setPadding(0, 0, 0, (int)mTitleHeightNoDisplay);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasureHeight = getMeasuredHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        initVelocityTracker(ev);
        boolean isConsume = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isConsume = handleActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                isConsume = false;
                break;
        }
        return isConsume || super.dispatchTouchEvent(ev);
    }

    private void initBackgroundView() {
        if (mBackgroundId != -1) {
            mDarkFrameLayout = new DarkFrameLayout(mContext);
            mDarkFrameLayout.addView(LayoutInflater.from(mContext).inflate(mBackgroundId, null));
            mDarkFrameLayout.setTag(TAG_BACKGROUND);
            addView(mDarkFrameLayout);
        }
    }

    private void handleActionMove(MotionEvent event) {
        if (isPanelOnTouch) {
            return;
        }
        if (supportScrollInView((int) (firstDownY - event.getY()))) {
            return;
        }
        computeVelocity();
        if (Math.abs(xVelocity) > Math.abs(yVelocity)) {
            return;
        }
        if (!isDragging && Math.abs(event.getY() - firstDownY) > mTouchSlop
                && Math.abs(event.getX() - firstDownX) < mTouchSlop) {
            isDragging = true;
            downY = event.getY();
        }
        if (isDragging) {
            deltaY = event.getY() - downY;
            downY = event.getY();
            View touchingView = findViewWithTag(TAG_PANEL);
            if (!mBoundary) {
                touchingView.offsetTopAndBottom((int)deltaY);
            } else {
//                float touchViewY =
//                if()
            }
        }
    }

    private boolean handleActionDown(MotionEvent event) {
        mPressStartTime = System.currentTimeMillis();
        boolean isConsume;
        firstDownX = event.getX();
        firstDownY = downY = event.getY();
        if (!isPanelShow && downY > mMeasureHeight - mTitleHeightNoDisplay) {
            isPanelOnTouch = true;
            isConsume = true;
        } else if (isPanelShow && downY > mMeasureHeight - mPanelHeight) {
            isPanelOnTouch = true;
            isConsume = true;
        } else {
            isPanelOnTouch = false;
            isConsume = false;
        }
        return isConsume;
    }

    private void computeVelocity() {
        //units是单位表示， 1代表px/毫秒, 1000代表px/秒
        mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
        xVelocity = mVelocityTracker.getXVelocity();
        yVelocity = mVelocityTracker.getYVelocity();
    }

    private boolean supportScrollInView(int direction) {
        View view = findViewWithTag(TAG_PANEL);
        if (view instanceof ViewGroup) {
            View childView = findTopChildUnder((ViewGroup) view, firstDownX, firstDownY);
            if (childView == null) {
                return false;
            }
            if (childView instanceof AbsListView) {
                AbsListView absListView = (AbsListView) childView;
                if (Build.VERSION.SDK_INT >= 19) {
                    return absListView.canScrollList(direction);
                } else {
                    return absListViewCanScrollList(absListView,direction);
                }
            } else if (childView instanceof ScrollView) {
                ScrollView scrollView = (ScrollView) childView;
                if (Build.VERSION.SDK_INT >= 14) {
                    return scrollView.canScrollVertically(direction);
                } else {
                    return scrollViewCanScrollVertically(scrollView, direction);
                }
            }

        }
        return false;
    }

    private View findTopChildUnder(ViewGroup parentView, float x, float y) {
        int childCount = parentView.getChildCount();
        //TODO fori or forr
        for (int i = childCount; i > 0; i--) {
            final View child = parentView.getChildAt(i);
            if (x >= child.getLeft() && x < child.getRight() &&
                    y >= child.getTop() && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

    /**
     *  Copy From ScrollView (API Level >= 14)
     * @param direction Negative to check scrolling up, positive to check
     *                  scrolling down.
     *   @return true if the scrollView can be scrolled in the specified direction,
     *         false otherwise
     */
    private  boolean scrollViewCanScrollVertically(ScrollView scrollView,int direction) {
        final int offset = Math.max(0, scrollView.getScrollY());
        final int range = computeVerticalScrollRange(scrollView) - scrollView.getHeight();
        if (range == 0) return false;
        if (direction < 0) { //scroll up
            return offset > 0;
        } else {//scroll down
            return offset < range - 1;
        }
    }

    /**
     * Copy From ScrollView (API Level >= 14)
     * <p>The scroll range of a scroll view is the overall height of all of its
     * children.</p>
     */
    private int computeVerticalScrollRange(ScrollView scrollView) {
        final int count = scrollView.getChildCount();
        final int contentHeight = scrollView.getHeight() - scrollView.getPaddingBottom() - scrollView.getPaddingTop();
        if (count == 0) {
            return contentHeight;
        }

        int scrollRange = scrollView.getChildAt(0).getBottom();
        final int scrollY = scrollView.getScrollY();
        final int overscrollBottom = Math.max(0, scrollRange - contentHeight);
        if (scrollY < 0) {
            scrollRange -= scrollY;
        } else if (scrollY > overscrollBottom) {
            scrollRange += scrollY - overscrollBottom;
        }

        return scrollRange;
    }

    /**
     * Copy From AbsListView (API Level >= 19)
     * @param absListView AbsListView
     * @param direction Negative to check scrolling up, positive to check
     *                  scrolling down.
     * @return true if the list can be scrolled in the specified direction,
     *         false otherwise
     */
    private boolean absListViewCanScrollList(AbsListView absListView,int direction) {
        final int childCount = absListView.getChildCount();
        if (childCount == 0) {
            return false;
        }
        final int firstPosition = absListView.getFirstVisiblePosition();
        if (direction > 0) {//can scroll down
            final int lastBottom = absListView.getChildAt(childCount - 1).getBottom();
            final int lastPosition = firstPosition + childCount;
            return lastPosition < absListView.getCount() || lastBottom > absListView.getHeight() - absListView.getPaddingTop();
        } else {//can scroll  up
            final int firstTop = absListView.getChildAt(0).getTop();
            return firstPosition > 0 || firstTop < absListView.getPaddingTop();
        }
    }

    private void initVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private int px2dp(int pxValue) {
        return (int) (pxValue / mDensity + 0.5f);
    }

    private int dp2px(int dpValue) {
        return (int) (dpValue * mDensity + 0.5f);
    }

}
