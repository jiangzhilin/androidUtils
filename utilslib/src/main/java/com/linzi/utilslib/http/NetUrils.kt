package com.linzi.utilslib.http

import android.content.Context
import android.net.ConnectivityManager
import com.linzi.sdk.utils.LOG
import com.linzi.utilslib.config.Config
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author linzi
 * @date 2019/9/29 0029
 */
object NetUrils {
    private const val NETWORK_WIFI = 0//使用wifi
    private const val NETWORK_MOBILE = 1//使用手机网络
    private const val NETWORK_NONE = 2//网络异常

    private var flowable:Disposable?=null
    private var times=0
    var NET_MODE=0//主机连接状态0离线，1在线

    fun getNetWorkState(context: Context): Int {
        //得到连接管理器对象
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager
                .activeNetworkInfo
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_WIFI//wifi
            } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return NETWORK_MOBILE//mobile
            }
        } else {
            //网络异常
            return NETWORK_NONE
        }
        return NETWORK_NONE
    }

    /**
	 * ping
	 *
	 * @param pingNum
	 *            ping的次数
	 * @param url
	 *            ping的目标URL
	 * @return
	 */
	fun ping( pingNum:Int,  url:String,call:Observer<Boolean>) {
        if(flowable ==null) {
            synchronized(this) {
                if(flowable ==null) {
                    startPing(pingNum,url,call)
                }
            }
        }else{
            flowable?.dispose()
            flowable =null
            startPing(pingNum,url,call)
        }
	}
    /**
     * @author linzi
     * @date 2020/4/14 18:01
     * @context 开启定时ping
     */
    private fun startPing(pingNum:Int,  url:String,call:Observer<Boolean>){
        flowable = Observable.interval(0, Config.FRESH_NET_TIME, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    var status = 0
                    var process: Process
                    try {
                        process = Runtime.getRuntime().exec("/system/bin/ping -c $pingNum $url")
                        status = process.waitFor()

                        synchronized(this) {
                            NET_MODE = if (status == 0) {
                                1
                            } else {
                                0
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        synchronized(this) {
                            NET_MODE = 0
                        }
                    }
                    LOG.E("连接状态$times${Thread.currentThread().id}", NET_MODE)

                    if(times ==0) {
                        call.onNext(true)
                    }else if(((times *Config.FRESH_NET_TIME)%Config.NOTICE_TIME).toInt()==0&& times >0&&((times *Config.FRESH_NET_TIME)>=(Config.NOTICE_TIME))){
                        call.onNext(false)
                    }
                    times++
                }
    }
    /**
     * @author linzi
     * @date 2020/4/14 18:00
     * @context 执行ping操作
     */
    fun justPing(pingNum:Int,url:String):Int{
        var status = 0
        var process: Process
        try {
            process = Runtime.getRuntime().exec("/system/bin/ping -c $pingNum $url")
            status = process.waitFor()

            synchronized(this) {
                return if (status == 0) {
                    1
                } else {
                    0
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
    }
    //获取本地IP
    val localIPAddress: String
        get() {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.hostAddress.toString()
                    }
                }
            }
            return "null"
        }

    fun destroy(){
        flowable?.dispose()
        flowable =null
        times =0
    }

}
