package com.linzi.androidutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.linzi.utilslib.entity.PCAEntity;
import com.linzi.utilslib.utils.JSONFileToStrUtils;
import com.linzi.utilslib.weight.AskDialog;
import com.linzi.utilslib.weight.CountNumberView;
import com.linzi.utilslib.weight.LoadDialog;
import com.linzi.utilslib.weight.PopNumKeyBordeUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_sys;
    private Button bt_dialog;
    private Button bt_keybord;
    private CountNumberView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bt_sys = (Button) findViewById(R.id.bt_sys);
        bt_dialog = (Button) findViewById(R.id.bt_dialog);
        bt_keybord = (Button) findViewById(R.id.bt_keybord);
        txt = (CountNumberView) findViewById(R.id.txt);

        bt_sys.setOnClickListener(this);
        bt_dialog.setOnClickListener(this);
        bt_keybord.setOnClickListener(this);

        txt.showNumberWithAnimation(Float.valueOf(1000), CountNumberView.FLOATREGEX);

        List<PCAEntity> pca=JSONFileToStrUtils.getArea(this);
        System.out.println(pca.get(0).getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sys:
                new AskDialog(this, this)
                        .setWinTitle("系统消息")
                        .setMessage("你好啊，我是系统组件")
                        .setCloseListener()
                        .setSubmitListener(new View.OnClickListener() {
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
                        .setSubmitListenner(new PopNumKeyBordeUtils.SubmitListener() {
                            @Override
                            public void submitListener(View view) {

                            }
                        })
                        .show(bt_dialog);
                break;
        }
    }
}
