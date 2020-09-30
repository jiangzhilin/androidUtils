package com.linzi.androidutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.linzi.utilslib.entity.PCAEntity;
import com.linzi.utilslib.utils.JSONFileToStrUtils;
import com.linzi.utilslib.utils.NToast;
import com.linzi.utilslib.utils.PermissionUtile;
import com.linzi.utilslib.utils.PhotoPicker;
import com.linzi.utilslib.weight.AskDialog;
import com.linzi.utilslib.weight.CountNumberView;
import com.linzi.utilslib.weight.LoadDialog;
import com.linzi.utilslib.weight.PopNumKeyBordeUtils;

import java.util.List;

import base.BaseActivity;

public class MainActivity extends BaseActivity implements OnClickListener {

    private Button bt_sys;
    private Button bt_dialog;
    private Button bt_dialog2;
    private Button bt_keybord;
    private Button bt_photo;
    private Button bt_shuaxin;
    private Button bt_lottie;
    private Button bt_hello;
    private CountNumberView txt;
    private EditText ed_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void initData() {
        initView();
        setTitle("演示");
    }

    private void initView() {
        bt_sys = (Button) findViewById(R.id.bt_sys);
        bt_dialog = (Button) findViewById(R.id.bt_dialog);
        bt_keybord = (Button) findViewById(R.id.bt_keybord);
        bt_photo = (Button) findViewById(R.id.bt_photo);
        bt_shuaxin = (Button) findViewById(R.id.bt_shuaxin);
        bt_lottie = (Button) findViewById(R.id.bt_lottie);
        bt_hello = (Button) findViewById(R.id.bt_hello);
        txt = (CountNumberView) findViewById(R.id.txt);
        ed_msg = (EditText) findViewById(R.id.ed_msg);

        bt_sys.setOnClickListener(this);
        bt_dialog.setOnClickListener(this);
        bt_keybord.setOnClickListener(this);
        bt_photo.setOnClickListener(this);

        txt.showNumberWithAnimation(Float.valueOf(1000), CountNumberView.FLOATREGEX);

        List<PCAEntity> pca = JSONFileToStrUtils.getArea(this);
        System.out.println(pca.get(0).getName());
        NToast.init(this);
        PermissionUtile.getFilesPermission(MainActivity.this, new PermissionUtile.permissionCallback() {
            @Override
            public void getAll() {
                NToast.show("所有权限已获取");
            }

            @Override
            public void settingBack() {

            }

            @Override
            public void notinManifast(String permission) {

            }
        });

        NToast.showWithIcon("自定义视图的toast",R.drawable.icon_loading);

        bt_shuaxin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, RefreshActivity.class);
//                startActivity(intent);
            }
        });

        bt_lottie.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_msg.getText().toString().isEmpty()){
                    return;
                }
            }
        });
        bt_hello.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sys:
                new AskDialog(this, this)
                        .setWinTitle("系统消息")
                        .setMessage("你好啊，我是系统组件")
                        .setCloseListener()
                        .setRadius(10)
                        .setSubmitListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show();
                break;
            case R.id.bt_dialog:
                LoadDialog.show(this);
                break;
            case R.id.bt_keybord:
                new PopNumKeyBordeUtils(this)
                        .setView()
                        .setDefValues("")
                        .setKeyListenner(new PopNumKeyBordeUtils.KeyClickListener() {
                            @Override
                            public void keyListener(StringBuffer values_key) {

                            }
                        })
                        .setMaxLenght(6)
                        .setRedom(true)
                        .setSubmitListenner(new PopNumKeyBordeUtils.SubmitListener() {
                            @Override
                            public void submitListener(View view) {

                            }
                        })
                        .show(bt_dialog);
                break;
            case R.id.bt_photo:
                new PhotoPicker().setCallback((realPath, cutPath, compress)
                        -> Log.d("选中数据", "getFinalPath: " + realPath)
                ).Companion.show(this, 1,null);
                break;
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bt_sys:
//                new AskDialog(this, this)
//                        .setWinTitle("系统消息")
//                        .setMessage("你好啊，我是系统组件")
//                        .setCloseListener()
//                        .setRadius(10)
//                        .setSubmitListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                            }
//                        })
//                        .show();
//                break;
//            case R.id.bt_dialog:
//                LoadDialog.show(this);
//                break;
//            case R.id.bt_keybord:
//                new PopNumKeyBordeUtils(this)
//                        .setView()
//                        .setDefValues("")
//                        .setKeyListenner(new PopNumKeyBordeUtils.KeyClickListener() {
//                            @Override
//                            public void keyListener(StringBuffer values_key) {
//
//                            }
//                        })
//                        .setMaxLenght(12)
//                        .setRedom(true)
//                        .setSubmitListenner(new PopNumKeyBordeUtils.SubmitListener() {
//                            @Override
//                            public void submitListener(View view) {
//
//                            }
//                        })
//                        .show(bt_dialog);
//                break;
//            case R.id.bt_photo:
////                new PhotoPicker().setCallback((realPath, cutPath, compress)
////                        -> Log.d("选中数据", "getFinalPath: " + realPath)
////                ).Companion.show(this, 1,null);
//                break;
//        }
//    }
}
