package com.linzi.utilslib.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.guoxiaoxing.phoenix.core.PhoenixOption
import com.guoxiaoxing.phoenix.core.model.MediaEntity
import com.guoxiaoxing.phoenix.core.model.MimeType
import com.guoxiaoxing.phoenix.picker.Phoenix

/**
 * @author linzi
 * @date 2019/11/25
 */
class PhotoPicker : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mediaEntities = ArrayList<MediaEntity>()
        if (values != null) {
            for (path in values!!) {
                val mediaEntity = MediaEntity()
                mediaEntity.localPath = path
                mediaEntities.add(mediaEntity)
            }
        }

            Phoenix.with()
                    .theme(PhoenixOption.THEME_DEFAULT)// 主题
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
                    .start(this, if (type == 0) PhoenixOption.TYPE_PICK_MEDIA else PhoenixOption.TYPE_TAKE_PICTURE, REQUEST_CODE)


    }

    interface photoPickerBack {
        fun getFinalPath(realPath: ArrayList<String>, cutPath: ArrayList<String>, compress: ArrayList<String>)
    }

    fun setThemes(theme: Int): PhotoPicker {
        PhotoPicker.theme = theme
        return this
    }

    fun setFileType(fileType: Int): PhotoPicker {
        PhotoPicker.fileType = fileType
        return this
    }

    fun setMaxPickNumber(maxPickNumber: Int): PhotoPicker {
        PhotoPicker.maxPickNumber = maxPickNumber
        return this
    }

    fun setMinPickNumber(minPickNumber: Int): PhotoPicker {
        PhotoPicker.minPickNumber = minPickNumber
        return this
    }

    fun setSpanCount(spanCount: Int): PhotoPicker {
        PhotoPicker.spanCount = spanCount
        return this
    }

    fun setEnablePreview(enablePreview: Boolean): PhotoPicker {
        PhotoPicker.enablePreview = enablePreview
        return this
    }

    fun setEnableCamera(enableCamera: Boolean): PhotoPicker {
        PhotoPicker.enableCamera = enableCamera
        return this
    }

    fun setEnableAnimation(enableAnimation: Boolean): PhotoPicker {
        PhotoPicker.enableAnimation = enableAnimation
        return this
    }

    fun setEnableCompress(enableCompress: Boolean): PhotoPicker {
        PhotoPicker.enableCompress = enableCompress
        return this
    }

    fun setCompressPictureFilterSize(compressPictureFilterSize: Int): PhotoPicker {
        PhotoPicker.compressPictureFilterSize = compressPictureFilterSize
        return this
    }

    fun setCompressVideoFilterSize(compressVideoFilterSize: Int): PhotoPicker {
        PhotoPicker.compressVideoFilterSize = compressVideoFilterSize
        return this
    }

    fun setThumbnailSize(thumbnailSize: Int): PhotoPicker {
        PhotoPicker.thumbnailSize = thumbnailSize
        return this
    }

    fun setVideoFilterTime(videoFilterTime: Int): PhotoPicker {
        PhotoPicker.videoFilterTime = videoFilterTime
        return this
    }

    fun setMediaFilterSize(mediaFilterSize: Int): PhotoPicker {
        PhotoPicker.mediaFilterSize = mediaFilterSize
        return this
    }

    fun setCallback(callback: photoPickerBack):PhotoPicker{
        PhotoPicker.callback = callback
        return this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//                返回的数据
                val result = Phoenix.result(data)
                val realPath = ArrayList<String>()
                val cutPath = ArrayList<String>()
                val compress = ArrayList<String>()
                if (type == 0) {
                    for (mediaEntity in result) {
                        if (mediaEntity.localPath != null) {
                            realPath.add(mediaEntity.localPath)
                        }
                        if (mediaEntity.cutPath != null) {
                            cutPath.add(mediaEntity.cutPath)
                        }
                        if (mediaEntity.compressPath != null) {
                            compress.add(mediaEntity.compressPath)
                        }
                    }
                    callback!!.getFinalPath(realPath, cutPath, compress)
                } else {
                    realPath.addAll(values!!)
                    for (mediaEntity in result) {
                        realPath.add(mediaEntity.localPath)
                    }
                    Log.d("选择图片", realPath.toString())
                    callback!!.getFinalPath(realPath, cutPath, compress)
                }

            }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        PhotoPicker.theme = PhoenixOption.THEME_DEFAULT
        PhotoPicker.fileType = MimeType.ofImage()
        PhotoPicker.maxPickNumber = 9
        PhotoPicker.minPickNumber = 0
        PhotoPicker.spanCount = 4
        PhotoPicker.enablePreview = true
        PhotoPicker.enableCamera = true
        PhotoPicker.enableAnimation = true
        PhotoPicker.enableCompress = false
        PhotoPicker.compressPictureFilterSize = 1024
        PhotoPicker.compressVideoFilterSize = 2048
        PhotoPicker.thumbnailSize = 160
        PhotoPicker.videoFilterTime = 0
        PhotoPicker.mediaFilterSize = 0
        PhotoPicker.callback = null
        PhotoPicker.values = null
        PhotoPicker.type=0
        PhotoPicker.isStop=false
    }

    companion object {

        private val TAG = "PhotoPicker"
        private var theme = PhoenixOption.THEME_DEFAULT
        private var fileType = MimeType.ofImage()
        private var maxPickNumber = 9
        private var minPickNumber = 0
        private var spanCount = 4
        private var enablePreview = true
        private var enableCamera = true
        private var enableAnimation = true
        private var enableCompress = false
        private var compressPictureFilterSize = 1024
        private var compressVideoFilterSize = 2048
        private var thumbnailSize = 160
        private var videoFilterTime = 0
        private var mediaFilterSize = 0
        private val REQUEST_CODE = 10001
        private var callback: photoPickerBack? = null

        private var isInit = false
        private var values: ArrayList<String>? = null

        private var type=0 //type==0  相册，1相机

        private var isStop=false

        fun init(imageLoad: ImageLoad) {
            Phoenix.config()
                    .imageLoader(imageLoad)
            isInit = true
        }

        fun show(activity: Activity,type:Int, values: ArrayList<String>?) {
            if (!isInit) {
                Log.e(TAG, "show: 工具未初始化")
                return
            }
            if (callback == null) {
                Log.e(TAG, "show: 未设置回调")
                return
            }

            PhotoPicker.values = values
            PhotoPicker.type = type

            val intent = Intent(activity, PhotoPicker::class.java)
            activity.startActivity(intent)

        }
    }
}