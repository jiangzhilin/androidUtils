package com.linzi.utilslib.http

import android.os.Build
import android.support.annotation.RequiresApi
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class SDKObserver<T> : Observer<T>{

    override fun onSubscribe(d: Disposable) {

    }
    override fun onError(e: Throwable) {
        onCompleted()
    }


    override fun onNext(value: T) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if (value != null) {
            onSuccess(value)
        } else {
            onFaild("返回空值")
        }


    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onComplete() {
        onCompleted()
    }


    abstract fun onSuccess(t: T)
    abstract fun onFaild(msg: Any?)
    abstract fun onCompleted()

}