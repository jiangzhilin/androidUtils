package com.linzi.androidutils.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;

/**
 * Created by Administrator on 2019/2/28.
 */

public class AssManager {
    public static String[] getfileFromAssets(Context activity, String path) {
        AssetManager assetManager = activity.getAssets();
        String[] files=null;
        try {
            files = assetManager.list(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return files;

    }
}
