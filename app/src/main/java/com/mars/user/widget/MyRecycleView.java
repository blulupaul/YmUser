package com.mars.user.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by gu on 2017/11/20
 * desc: ${desc}
 */
public class MyRecycleView extends RecyclerView {

    private static final int INVALID_POINTER = -1;
    private int mScrollPointerId = INVALID_POINTER;
    private int mInitialTouchX, mInitialTouchY;
    private int mTouchSlop;
    public MyRecycleView(Context context) {
        this(context, null);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public void setScrollingTouchSlop(int slopConstant) {
        super.setScrollingTouchSlop(slopConstant);
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        switch (slopConstant) {
            case TOUCH_SLOP_DEFAULT:
                mTouchSlop = vc.getScaledTouchSlop();
                break;
            case TOUCH_SLOP_PAGING:
                mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(vc);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        final int action = MotionEventCompat.getActionMasked(e);
        final int actionIndex = MotionEventCompat.getActionIndex(e);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mScrollPointerId = MotionEventCompat.getPointerId(e, 0);
                mInitialTouchX = (int) (e.getX() + 0.5f);
                mInitialTouchY = (int) (e.getY() + 0.5f);
                return super.onInterceptTouchEvent(e);

            case MotionEventCompat.ACTION_POINTER_DOWN:
                mScrollPointerId = MotionEventCompat.getPointerId(e, actionIndex);
                mInitialTouchX = (int) (MotionEventCompat.getX(e, actionIndex) + 0.5f);
                mInitialTouchY = (int) (MotionEventCompat.getY(e, actionIndex) + 0.5f);
                return super.onInterceptTouchEvent(e);

            case MotionEvent.ACTION_MOVE: {
                final int index = MotionEventCompat.findPointerIndex(e, mScrollPointerId);
                if (index < 0) {
                    return false;
                }

                final int x = (int) (MotionEventCompat.getX(e, index) + 0.5f);
                final int y = (int) (MotionEventCompat.getY(e, index) + 0.5f);
                if (getScrollState() != SCROLL_STATE_DRAGGING) {
                    final int dx = x - mInitialTouchX;
                    final int dy = y - mInitialTouchY;
                    final boolean canScrollHorizontally = getLayoutManager().canScrollHorizontally();
                    final boolean canScrollVertically = getLayoutManager().canScrollVertically();
                    boolean startScroll = false;
                    if (canScrollHorizontally && Math.abs(dx) > mTouchSlop && (Math.abs(dx) >= Math.abs(dy) || canScrollVertically)) {
                        startScroll = true;
                    }
                    if (canScrollVertically && Math.abs(dy) > mTouchSlop && (Math.abs(dy) >= Math.abs(dx) || canScrollHorizontally)) {
                        startScroll = true;
                    }
                    return startScroll && super.onInterceptTouchEvent(e);
                }
                return super.onInterceptTouchEvent(e);
            }

            default:
                return super.onInterceptTouchEvent(e);
        }
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /***
     * 改变高度 其中onMeasure函数决定了组件显示的高度与宽度；
     * makeMeasureSpec函数中第一个函数决定布局空间的大小，第二个参数是布局模式
     * MeasureSpec.AT_MOST的意思就是子控件需要多大的控件就扩展到多大的空间
     * 之后在ScrollView中添加这个组件就OK了。
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
