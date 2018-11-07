package com.linzi.androidutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.linzi.utilslib.weight.AskDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AskDialog(this,this)
                .setWinTitle("系统消息")
                .setMessage("你好啊，我是系统组件,asdasdasdasdasdadasdasdadadadasdadadadadasdasdsdasdads")
                .setCloseListener()
                .setSubmitListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();

    }
}
