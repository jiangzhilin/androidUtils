/*
    ShengDao Android Client, NToast
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.linzi.utilslib.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linzi.utilslib.R;
import com.linzi.utilslib.weight.RCRelativeLayout;


public class NToast {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void show(String msg) {
        if (mContext != null) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void show(String msg, int time) {
        if (mContext != null) {
            Toast.makeText(mContext, msg, time).show();
        }
    }

    public static void showCus(String msg) {
        if (mContext != null) {
            CusToast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
        }
    }
    public static void showCus(String msg,int time) {
        if (mContext != null) {
            CusToast.makeText(mContext,msg,time).show();
        }
    }
    public static void showWithIcon(String msg,@DrawableRes int icon) {
        if (mContext != null) {
            CusToast.makeText(mContext,msg,icon,Toast.LENGTH_SHORT).show();
        }
    }

    static class CusToast extends Toast {

        /**
         * Construct an empty Toast object.  You must call {@link #setView} before you
         * can call {@link #show}.
         *
         * @param context The context to use.  Usually your {@link Application}
         *                or {@link Activity} object.
         */
        public CusToast(Context context) {
            super(context);
        }

        public static CusToast makeText(Context context,String msg,int duration){
            CusToast toast=new CusToast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.cus_toast_layout, null);
            ViewHolder mVh=new ViewHolder(view);

            mVh.cardview.setRadius(20);
            mVh.tv_tips.setText(msg);

            toast.setView(view);
            toast.setDuration(duration);

            return toast;
        }

        public static CusToast makeText(Context context,String msg,int icon,int duration){
            CusToast toast=new CusToast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.cus_toast_layout, null);
            ViewHolder mVh=new ViewHolder(view);

            mVh.cardview.setRadius(dip2px(context,20));
            mVh.tv_tips.setText(msg);
            mVh.iv_icon.setBackgroundResource(icon);
            mVh.iv_icon.setVisibility(View.VISIBLE);
            toast.setView(view);
            toast.setDuration(duration);

            return toast;
        }


        public static class ViewHolder {
            public View rootView;
            public ImageView iv_icon;
            public TextView tv_tips;
            public RCRelativeLayout cardview;
            public LinearLayout ll_toast_view;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
                this.tv_tips = (TextView) rootView.findViewById(R.id.tv_tips);
                this.cardview = (RCRelativeLayout) rootView.findViewById(R.id.cardview);
                this.ll_toast_view = (LinearLayout) rootView.findViewById(R.id.ll_toast_view);
            }

        }
    }

    //将dp转换为px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
