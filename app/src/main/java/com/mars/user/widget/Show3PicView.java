package com.mars.user.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mars.user.R;
import com.mars.user.app.GlideApp;
import com.mars.user.ui.universal.picshow.PictureShowActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gu on 2017/11/20
 * desc: 评价显示最多三张图片，组合view
 */
public class Show3PicView extends LinearLayout {

    private LinearLayout mLlParentPickerPic;

    private RelativeLayout mRlPickerPic1;
    private ImageView mImgPickerPic1;
    private ImageView mImgPicDelete1;

    private RelativeLayout mRlPickerPic2;
    private ImageView mImgPickerPic2;
    private ImageView mImgPicDelete2;

    private RelativeLayout mRlPickerPic3;
    private ImageView mImgPickerPic3;
    private ImageView mImgPicDelete3;

    private Context mContext;

    private ArrayList<String> mUrlList = new ArrayList<>();

    public Show3PicView(Context context) {
        this(context, null);
    }

    public Show3PicView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Show3PicView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_picker_pic_view, this,true);
        mContext = context;
        initView(view);
    }

    private void initView(View view) {
        mRlPickerPic1 = view.findViewById(R.id.rl_pickPic1);
        mImgPickerPic1 =  view.findViewById(R.id.img_pickPic1);
        mImgPicDelete1 =  view.findViewById(R.id.img_pickPicDelete1);

        mRlPickerPic2 =  view.findViewById(R.id.rl_pickPic2);
        mImgPickerPic2 =  view.findViewById(R.id.img_pickPic2);
        mImgPicDelete2 =  view.findViewById(R.id.img_pickPicDelete2);

        mRlPickerPic3 =  view.findViewById(R.id.rl_pickPic3);
        mImgPickerPic3 =  view.findViewById(R.id.img_pickPic3);
        mImgPicDelete3 =  view.findViewById(R.id.img_pickPicDelete3);

        mLlParentPickerPic = view.findViewById(R.id.ll_parentPickerPic);

        initListener();

        changeShow();
    }

    private void initListener(){
        mLlParentPickerPic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureShowActivity.Companion.startSelf(mContext,getUrlList());
            }
        });
    }

    public ArrayList<String> getUrlList() {
        return mUrlList;
    }

    public void setUrlList(ArrayList<String> urlList) {
        if (mUrlList == null) {
            mUrlList = new ArrayList<>();
        }
        mUrlList.clear();
        mUrlList.addAll(urlList);
        changeShow();
    }

    private void changeShow() {
        mLlParentPickerPic.setVisibility(VISIBLE);
        List<String> urlList = getUrlList();
        int size = getUrlList().size();
        if (size <= 0) {
            mLlParentPickerPic.setVisibility(GONE);
        } else if (size == 1) {
            mRlPickerPic1.setVisibility(VISIBLE);
            mRlPickerPic2.setVisibility(INVISIBLE);
            mRlPickerPic3.setVisibility(INVISIBLE);
            GlideApp.with(mContext).load(urlList.get(0))
                    .error(R.mipmap.placeholder)
                    .into(mImgPickerPic1);

        } else if (size == 2) {
            mRlPickerPic1.setVisibility(VISIBLE);
            mRlPickerPic2.setVisibility(VISIBLE);
            mRlPickerPic3.setVisibility(INVISIBLE);
            GlideApp.with(mContext).load(urlList.get(0))
                    .error(R.mipmap.placeholder)
                    .into(mImgPickerPic1);

            GlideApp.with(mContext).load(urlList.get(1))
                    .error(R.mipmap.placeholder)
                    .into(mImgPickerPic2);
        } else  {
            mRlPickerPic1.setVisibility(VISIBLE);
            mRlPickerPic2.setVisibility(VISIBLE);
            mRlPickerPic3.setVisibility(VISIBLE);

            GlideApp.with(mContext).load(urlList.get(0))
                    .error(R.mipmap.placeholder)
                    .into(mImgPickerPic1);

            GlideApp.with(mContext).load(urlList.get(1))
                    .error(R.mipmap.placeholder)
                    .into(mImgPickerPic2);

            GlideApp.with(mContext).load(urlList.get(2))
                    .error(R.mipmap.placeholder)
                    .into(mImgPickerPic3);
        }
    }
}
