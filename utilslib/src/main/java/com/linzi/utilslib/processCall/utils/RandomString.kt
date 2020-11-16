package com.linzi.utilslib.processCall.utils

import java.util.*

object RandomString {
    private val str="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

    fun getRandom(length:Int):String{
        val random = Random()
        val buffer=StringBuffer()
        for(i:Int in 0 until length){
            buffer.append(str[random.nextInt(str.length)])
        }
        return buffer.toString()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(getRandom(10))
    }
}