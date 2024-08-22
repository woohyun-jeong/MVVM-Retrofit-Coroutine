package com.hadi.retrofitmvvm.app

import android.app.Application

class MyApplication : Application() {
    companion object{
        // 싱글턴 패턴
        lateinit var instance: MyApplication
            private set
        // 자기자신을 가져온다.
    }
    //onCreate()
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}