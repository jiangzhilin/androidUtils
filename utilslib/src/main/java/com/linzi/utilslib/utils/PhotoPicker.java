package com.linzi.utilslib.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/24.
 */

public class PhotoPicker extends AppCompatActivity{

    private static final String TAG = "PhotoPicker";
    private static int theme= PhoenixOption.THEME_DEFAULT;
    private static int fileType= MimeType.ofImage();
    private static int maxPickNumber=9;
    private static int minPickNumber=0;
    private static int spanCount=4;
    private static boolean enablePreview=true;
    private static boolean enableCamera=true;
    private static boolean enableAnimation=true;
    private static boolean enableCompress=false;
    private static int compressPictureFilterSize=1024;
    private static int compressVideoFilterSize=2048;
    private static int thumbnailSize=160;
    private static int videoFilterTime=0;
    private static int mediaFilterSize=0;
    private static int REQUEST_CODE=10001;
    private static photoPickerBack callback;

    private static boolean isInit=false;
    private static List<String>values;

    public static void init(ImageLoad imageLoad){
        Phoenix.config()
                .imageLoader(imageLoad);
        isInit=true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<MediaEntity>mediaEntities=new ArrayList<>();
        if(values!=null) {
            for (String path : values) {
                MediaEntity mediaEntity = new MediaEntity();
                mediaEntity.setLocalPath(path);
                mediaEntities.add(mediaEntity);
            }
        }
        Phoenix.with()
                .theme(theme)// 主题
                .fileType(fileType)//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(maxPickNumber)// 最大选择数量
                .minPickNumber(minPickNumber)// 最小选择数量
                .spanCount(spanCount)// 每行显示个数
                .enablePreview(enablePreview)// 是否开启预览
                .enableCamera(enableCamera)// 是否开启拍照
                .enableAnimation(enableAnimation)// 选择界面图片点击效果
                .enableCompress(enableCompress)// 是否开启压缩
                .compressPictureFilterSize(compressPictureFilterSize)//多少kb以下的图片不压缩
                .compressVideoFilterSize(compressVideoFilterSize)//多少kb以下的视频不压缩
                .thumbnailHeight(thumbnailSize)// 选择界面图片高度
                .thumbnailWidth(thumbnailSize)// 选择界面图片宽度
                .enableClickSound(false)// 是否开启点击声音
                .pickedMediaList(mediaEntities)// 已选图片数据
                .videoFilterTime(videoFilterTime)//显示多少秒以内的视频
                .mediaFilterSize(mediaFilterSize)//显示多少kb以下的图片/视频，默认为0，表示不限制
                //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                .start(this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE);

    }

    public interface photoPickerBack{
        public void getFinalPath(List<String>realPath,List<String>cutPath,List<String>compress);
    }

    public PhotoPicker setThemes(int theme) {
        PhotoPicker.theme = theme;
        return this;
    }

    public PhotoPicker setFileType(int fileType) {
        PhotoPicker.fileType = fileType;
        return this;
    }

    public PhotoPicker setMaxPickNumber(int maxPickNumber) {
        PhotoPicker.maxPickNumber = maxPickNumber;
        return this;
    }

    public PhotoPicker setMinPickNumber(int minPickNumber) {
        PhotoPicker.minPickNumber = minPickNumber;
        return this;
    }

    public PhotoPicker setSpanCount(int spanCount) {
        PhotoPicker.spanCount = spanCount;
        return this;
    }

    public PhotoPicker setEnablePreview(boolean enablePreview) {
        PhotoPicker.enablePreview = enablePreview;
        return this;
    }

    public PhotoPicker setEnableCamera(boolean enableCamera) {
        PhotoPicker.enableCamera = enableCamera;
        return this;
    }

    public PhotoPicker setEnableAnimation(boolean enableAnimation) {
        PhotoPicker.enableAnimation = enableAnimation;
        return this;
    }

    public PhotoPicker setEnableCompress(boolean enableCompress) {
        PhotoPicker.enableCompress = enableCompress;
        return this;
    }

    public PhotoPicker setCompressPictureFilterSize(int compressPictureFilterSize) {
        PhotoPicker.compressPictureFilterSize = compressPictureFilterSize;
        return this;
    }

    public PhotoPicker setCompressVideoFilterSize(int compressVideoFilterSize) {
        PhotoPicker.compressVideoFilterSize = compressVideoFilterSize;
        return this;
    }

    public PhotoPicker setThumbnailSize(int thumbnailSize) {
        PhotoPicker.thumbnailSize = thumbnailSize;
        return this;
    }

    public PhotoPicker setVideoFilterTime(int videoFilterTime) {
        PhotoPicker.videoFilterTime = videoFilterTime;
        return this;
    }

    public PhotoPicker setMediaFilterSize(int mediaFilterSize) {
        PhotoPicker.mediaFilterSize = mediaFilterSize;
        return this;
    }

    public PhotoPicker setCallback(photoPickerBack callback){
        PhotoPicker.callback=callback;
        return this;
    }

    public static void show(Activity activity,List<String> values){
        if(!isInit){
            Log.e(TAG, "show: 工具未初始化");
            return;
        }
        if(callback==null){
            Log.e(TAG, "show: 未设置回调");
            return;
        }

        PhotoPicker.values=values;

        Intent intent=new Intent(activity,PhotoPicker.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //返回的数据
            List<MediaEntity> result = Phoenix.result(data);
            List<String>realPath=new ArrayList<>();
            List<String>cutPath=new ArrayList<>();
            List<String>compress=new ArrayList<>();
            for(MediaEntity mediaEntity:result){
                if(mediaEntity.getLocalPath()!=null){
                    realPath.add(mediaEntity.getLocalPath());
                }
                if(mediaEntity.getCutPath()!=null){
                    cutPath.add(mediaEntity.getCutPath());
                }
                if(mediaEntity.getCompressPath()!=null){
                    compress.add(mediaEntity.getCompressPath());
                }
            }
            callback.getFinalPath(realPath,cutPath,compress);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoPicker.theme= PhoenixOption.THEME_DEFAULT;
        PhotoPicker.fileType= MimeType.ofImage();
        PhotoPicker.maxPickNumber=9;
        PhotoPicker.minPickNumber=0;
        PhotoPicker.spanCount=4;
        PhotoPicker.enablePreview=true;
        PhotoPicker.enableCamera=true;
        PhotoPicker.enableAnimation=true;
        PhotoPicker.enableCompress=false;
        PhotoPicker.compressPictureFilterSize=1024;
        PhotoPicker.compressVideoFilterSize=2048;
        PhotoPicker.thumbnailSize=160;
        PhotoPicker.videoFilterTime=0;
        PhotoPicker.mediaFilterSize=0;
        PhotoPicker.callback=null;
        PhotoPicker.values=null;
    }
}
