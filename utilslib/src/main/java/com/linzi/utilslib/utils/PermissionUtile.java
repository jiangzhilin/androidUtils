package com.linzi.utilslib.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.linzi.utilslib.weight.AskDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by Administrator on 2019/1/24.
 */

public class PermissionUtile{

    public interface permissionCallback{
        public void getAll();
//        public void someNotAllow();
        public void settingBack();
        public void notinManifast(String permission);
    }

    public static void getSysPermission(Activity activity){
        getPermission(activity,null,getUsePermission());
    }
    public static void getSysPermission(Activity activity,permissionCallback call){
        getPermission(activity,call,getUsePermission());
    }
    public static void getFilesPermission(Activity activity){
        getPermission(activity,null,getFilePermission());
    }
    public static void getFilesPermission(Activity activity,permissionCallback call){
        getPermission(activity,call,getFilePermission());
    }

    public static void getCusPermission(Activity activity,String... permission){
        getPermission(activity,null,permission);
    }
    public static void getCusPermission(Activity activity,permissionCallback call,String... permission){
        getPermission(activity,call,permission);
    }

    private static void getPermission(final Activity activity, final permissionCallback callback, String[]permissonArray){
        // 先判断是否有权限。
        try {
            // 申请权限。
            PackageManager pm = activity.getPackageManager();
            Log.d(TAG, "包名: "+ activity.getPackageName());
            PackageInfo pack = pm.getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] permissionStrings = pack.requestedPermissions;
            Log.d(TAG, "getPermission: "+ JSONObject.toJSONString(permissionStrings));
            List<String>permissions=Arrays.asList(permissionStrings);
            for(String permission:permissonArray){
                if(!permissions.contains(permission)){
                    Log.e(TAG, "getPermission: "+permission+"未加入Manifest权限清单");
                    if(callback!=null){
                        callback.notinManifast(permission);
                    }
                    return;
                }
            }
            AndPermission.with(activity)
                    .runtime()
                    .permission(permissonArray)
                    .rationale(mRationale)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> strings) {
                            Log.d(TAG, "onAction: 这些权限都已经获取");
                            if(callback!=null){
                                callback.getAll();
                            }
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> strings) {
                            Log.d(TAG, "onAction: 用户已拒绝这些权限");
                            if (AndPermission.hasAlwaysDeniedPermission(activity, strings)) {
                                // 这些权限被用户总是拒绝。
                                new AskDialog(activity, activity)
                                        .setWinTitle("系统提示")
                                        .setMessage("我们需要以下权限才能正常运行："+strings+"，是否到系统设置中开启这些权限？")
                                        .setCloseListener("不用了", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        })
                                        .setSubmitListener("好的",new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                AndPermission.with(activity)
                                                        .runtime()
                                                        .setting()
                                                        .onComeback(new Setting.Action() {
                                                            @Override
                                                            public void onAction() {
                                                                // 用户从设置回来了。
                                                                Log.d(TAG, "onAction: 从设置界面回来");
                                                                if(callback!=null){
                                                                    callback.settingBack();
                                                                }
                                                            }
                                                        })
                                                        .start();
                                            }
                                        })
                                        .show();
                            }
                        }
                    })
                    .start();
        }catch (Exception e){
            Log.e(TAG, "getPermission: ",e);
        }
    }

    private static String[] getUsePermission(){
        return new String[]{Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
        };
    }

    private static String[] getFilePermission(){

        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
    }


    private static Rationale mRationale = new Rationale() {
        @Override
        public void showRationale(Context context, Object o, RequestExecutor executor) {
            // 这里使用一个Dialog询问用户是否继续授权。

            // 如果用户继续：
            executor.execute();

            // 如果用户中断：
            executor.cancel();
        }
    };


}
