package com.linzi.utilslib.utils

import android.app.ActivityManager
import android.content.Context
import android.text.TextUtils
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.annotation.StringRes
import android.support.v4.content.FileProvider
import java.io.File
import java.net.NetworkInterface
import java.net.SocketException
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author linzi
 * @date 2019/11/18
 */
object AppUtils {
    /**
     * @author linzi
     * @date 2020/1/7 16:31
     * @context 获取设备MAC地址
     */
    fun getMacAddress(context: Context?): String? {
        if(context==null){
            return null
        }
        /*获取mac地址有一点需要注意的就是android 6.0版本后，以下注释方法不再适用，不管任何手机都会返回"02:00:00:00:00:00"这个默认的mac地址，这是googel官方为了加强权限管理而禁用了getSYstemService(Context.WIFI_SERVICE)方法来获得mac地址。*/
        //    String macAddress= "";
        //    WifiManager wifiManager = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
        //    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        //    macAddress = wifiInfo.getMacAddress();
        //    return macAddress;
        var macAddress: String? = null
        val buf = StringBuffer()
        var networkInterface: NetworkInterface? = null
        try {
            networkInterface = NetworkInterface.getByName("eth1")
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0")
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02"
            }
            val addr = networkInterface.hardwareAddress
            for (b in addr) {
                buf.append(String.format("%02X:", b))
            }
            if (buf.length > 0) {
                buf.deleteCharAt(buf.length - 1)
            }
            macAddress = buf.toString()
        } catch (e: SocketException) {
            e.printStackTrace()
            return "02:00:00:00:00:02"
        }

        return macAddress
    }

    /**
     * 获取当前版本号
     */
    fun getCurrentVersion(context: Context): Int {
        val packageManager = context.packageManager
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        val packInfo = packageManager.getPackageInfo(context.packageName, 0)
        return packInfo.versionCode
    }
    /**
     * @author linzi
     * @date 2020/1/7 16:31
     * @context 获取当前版本名
     */
    fun getCurrentVersionName(context: Context): String {
        val packageManager = context.packageManager
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        val packInfo = packageManager.getPackageInfo(context.packageName, 0)
        return packInfo.versionName
    }
    /**
     * @author linzi
     * @date 2020/1/7 16:32
     * @context 时间戳转时间字符串
     */
    fun long2Time(time:Long,ptn: String): String {
        var sf=SimpleDateFormat(ptn)
        return sf.format(time)
    }


    /**
     * 跳转安装apk
     */
    fun jumpInstallApk(context:Context,path: String,providerName:String) {
        var uri: Uri
        val intent = Intent(Intent.ACTION_VIEW)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, providerName, File(path))
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
        } else {
            uri = Uri.fromFile(File(path))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context.startActivity(intent)

    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    fun isServiceRunning(context: Context, ServiceName: String): Boolean {
        if (TextUtils.isEmpty(ServiceName)) {
            return false
        }
        val myManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningService = myManager.getRunningServices(1000) as ArrayList<ActivityManager.RunningServiceInfo>
        for (i in 0 until runningService.size) {
            if (runningService[i].service.className.toString().equals(ServiceName)) {
                return true
            }
        }
        return false
    }

    /**
     * 进程是否存活
     * @return
     */
    fun isRunningProcess(context: Context,processName:String):Boolean {
        val myManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var runnings:List<ActivityManager.RunningAppProcessInfo> = myManager.runningAppProcesses
        if (runnings != null) {
            for ( info in runnings) {
                if(TextUtils.equals(info.processName,processName)){
                    return true
                }
            }
        }
        return false
    }


    /**
     * @author linzi
     * @date 2020/1/7 16:32
     * @context 字符串格式化
     */
    fun strForMat(context: Context,@StringRes id:Int, vararg obj:Any?): String {
        when {
            obj.size==1 -> return String.format(context.getString(id), obj[0])
            obj.size==2 -> return String.format(context.getString(id), obj[0],obj[1])
            obj.size==3 -> return String.format(context.getString(id), obj[0],obj[1],obj[2])
            else -> return ""
        }
    }

}
