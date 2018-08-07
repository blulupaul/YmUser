package com.mars.user.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gu on 2017/03/22
 * Desc:时间选择器
 */
public class DatePickerUtil {

    private static volatile DatePickerUtil mSingleton = null;

    private static Context mContext;

    private SimpleDateFormat mFormat;

    private DatePickerUtil() {
        mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    }

    public static DatePickerUtil getInstance(Context c) {
        mContext = c;
        if (mSingleton == null) {
            synchronized (DatePickerUtil.class) {
                if (mSingleton == null) {
                    mSingleton = new DatePickerUtil();
                }
            }
        }
        return mSingleton;
    }

    public void pickerDate(int type, final OnDateCallBack<Date> callBack) {
        boolean[] boo = new boolean[6];
        for (int i = 0; i < 6; i++) {
            if (i < type) {
                boo[i] = true;
            } else {
                boo[i] = false;
            }
        }
        //时间选择器
        TimePickerView pvEndTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                callBack.onCallBack(date);
            }
        })
                .setType(boo)
//                .setCancelText("Cancel")//取消按钮文字
//                .setSubmitText("Sure")//确认按钮文字
//                .setContentSize(20)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
                .setTitleText("选择日期")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#FF59DCE0"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#FF59DCE0"))//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                .setRange(mSelectedDate.get(Calendar.YEAR) - 20, mSelectedDate.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                .setDate(mSelectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(mStartDate, mEndDate)//起始终止年月日设定
//                .setLabel("年", "月", "日", "时", "分", "秒")
//                .isDialog(true)//是否显示为对话框样式
                .build();
        pvEndTime.show();
    }

    public interface OnDateCallBack<T> {

        void onCallBack(T t);
    }

}
