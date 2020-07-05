package com.geekhub.dagger2test

import android.app.Application
import android.content.res.Resources
import com.geekhub.dagger2test.di.components.AppComponent
import com.geekhub.dagger2test.di.components.DaggerAppComponent
import com.geekhub.dagger2test.di.modules.AppModule
import javax.inject.Inject

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        getResources = resources
        context = this
    }

    companion object {
        var context:App? = null
        var appComponent: AppComponent? = null
        var getResources: Resources? = null
    }
}