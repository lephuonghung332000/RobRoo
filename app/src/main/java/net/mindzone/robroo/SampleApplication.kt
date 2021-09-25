package net.mindzone.robroo

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import net.mindzone.robroo.utils.LocaleManager
import dagger.hilt.android.HiltAndroidApp
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@HiltAndroidApp
class RobRooApplication :MultiDexApplication(){

    init {
        instance = this
    }

    companion object {
        private var instance: RobRooApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = RobRooApplication.applicationContext()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.updateContext(base))
        MultiDex.install(this)
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.updateContext(this)

    }
}