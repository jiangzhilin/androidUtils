package com.linzi.sdk.utils

import android.util.Log

import com.google.gson.Gson

import java.io.Serializable
import java.util.Calendar

/**
 * @author linzi
 * @date 2019/5/16 0016
 */
object LOG {
    private var DEBUG = true
    private var TAG="$LOG"

    fun init(debug: Boolean) {
        DEBUG = debug
    }

    fun <T> D(tag: String, t: T) {
        if (DEBUG) {
            if (t is Serializable) {
                Log.d(tag, Gson().toJson(t))
            } else {
                Log.d(tag, t.toString())
            }
        }
    }

    fun <T> D(t: T) {
        if (DEBUG) {
            if (t is Serializable) {
                Log.d(TAG, Gson().toJson(t))
            } else {
                val cal = Calendar.getInstance()
                Log.d(cal.time.toString(), t.toString())
            }
        }
    }

    fun <T> E(tag: String, t: T?) {
        if (DEBUG) {
            if (t is Serializable) {
                Log.e(tag, Gson().toJson(t))
            } else {
                Log.e(tag, t?.toString() ?: "null")
            }
        }
    }

    fun <T> E(t: T) {
        if (DEBUG) {
            if (t is Serializable) {
                Log.e(TAG, Gson().toJson(t))
            } else {
                val cal = Calendar.getInstance()
                Log.e(cal.time.toString(), t.toString())
            }
        }
    }

}
