package apptentive.com.states.application

import org.koin.core.module.Module

class TestMainApplication : MainApplication() {
    override fun provideDependency() = listOf<Module>()
}
