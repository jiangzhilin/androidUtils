package com.wenbo.base

import android.app.Activity
import android.os.Handler
import android.os.Message

import java.lang.ref.WeakReference

/**
 * @author linzi
 * @date 2019/11/14
 */
abstract class BizHandler(activity: Activity?) : Handler() {

    private var sr: WeakReference<Activity>? = null

    init {
        if (activity != null) {
            sr = WeakReference(activity)
        }
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        if (sr != null) {
            val ac = sr!!.get()

            if (ac != null && !ac.isFinishing) {
                handleMessage(ac, msg)
            }

        }
    }

    abstract fun handleMessage(activity: Activity, msg: Message)

    fun removeCallbacksAndMessages() {
        sr!!.clear()
        sr = null
        this.removeCallbacksAndMessages(null)
    }

}
