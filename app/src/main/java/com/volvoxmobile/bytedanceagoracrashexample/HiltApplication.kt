package com.volvoxmobile.bytedanceagoracrashexample

import android.app.Application
import android.content.Context
import com.volvoxmobile.bytedanceagoracrashexample.bytedance.ByteDanceBeautySDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ByteDanceBeautySDK.initBeautySDK(this)
    }
    companion object {
        lateinit var appContext: Context
        private var fragments: MutableList<String> = mutableListOf()
        fun getPreviousFragment(): String {
            return fragments.firstOrNull() ?: ""
        }

        fun getCurrentFragment(): String {
            return fragments.lastOrNull() ?: ""
        }

        fun addFragment(name: String) {
            if (fragments.size > 1) {
                fragments.removeFirstOrNull()
            }
            fragments.add(name)
        }
    }
}