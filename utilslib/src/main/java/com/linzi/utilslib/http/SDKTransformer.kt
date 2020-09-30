package com.linzi.utilslib.http

import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SDKTransformer<T> : ObservableTransformer<T, T> {
    private var life: LifecycleProvider<Any>?=null
    constructor()
    constructor(life: LifecycleProvider<Any>?){
        this.life=life
    }
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return if(this.life!=null) {
            upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.single())
                .doOnSubscribe {
                }
                .compose(this.life?.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
        }else{
            upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.single())
                .doOnSubscribe {
                }
                .observeOn(AndroidSchedulers.mainThread())
        }
    }




}