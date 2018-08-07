package com.mars.user.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mars.user.R;


/**
 * Created by gu on 2018/7/17
 * Desc: 自定义导航栏
 */

public class CustomActionBar extends RelativeLayout {
    private LinearLayout mLlBack;
    private TextView mTvCustomTitle;

    private View view;
    private RelativeLayout mRlCustomTitle;
    private String mTitleText = "";

    public CustomActionBar(Context context) {
        this(context, null);
    }

    public CustomActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.custom_action_bar, this, true);
        // 初始化控件
        initView();
        getAttrs(context, attrs);
        setTitle(mTitleText);
        initListener(context);
    }

    /**
     * 得到属性值
     *
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.custom_action_bar);
        mTitleText = ta.getString(R.styleable.custom_action_bar_custom_title);
        ta.recycle();
    }

    /**
     * desc:初始化控件
     */
    private void initView() {
        mLlBack = view.findViewById(R.id.ll_back);
        mTvCustomTitle = view.findViewById(R.id.tv_customTitle);
    }

    public void setTitle(String title) {
        mTvCustomTitle.setText(title);
    }

    private void initListener(final Context context) {
        mLlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
    }
}
