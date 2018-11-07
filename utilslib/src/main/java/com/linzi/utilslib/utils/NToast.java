/*
    ShengDao Android Client, NToast
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.linzi.utilslib.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


public class NToast {
    private static Context mContext;
    public static void init(Context context){
        mContext=context;
    }

    public static void show(String msg){
        if(mContext!=null){
            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
        }
    }
}
