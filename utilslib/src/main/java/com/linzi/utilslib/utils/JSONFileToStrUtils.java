package com.linzi.utilslib.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.alibaba.fastjson.JSONObject;
import com.linzi.utilslib.entity.PCAEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2018/11/12.
 */

public class JSONFileToStrUtils {
    public static String getJson(String fileName,Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static List<PCAEntity> getArea(Context mContext){
        String area=getJson("pca-code.json",mContext);
        List<PCAEntity>pca= JSONObject.parseArray(area,PCAEntity.class);
        return pca;
    }
}
