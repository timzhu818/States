package apptentive.com.states.application

import android.app.Application
import apptentive.com.states.di.mainComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = mainComponent
}
