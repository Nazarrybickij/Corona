package com.geekhub.dagger2test.di.components

import com.geekhub.dagger2test.App
import com.geekhub.dagger2test.MainActivity
import com.geekhub.dagger2test.SPStorage
import com.geekhub.dagger2test.di.modules.AppModule
import com.geekhub.dagger2test.network.NetworkRepository
import com.geekhub.dagger2test.network.NetworkService
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(networkService: NetworkRepository)
    fun inject(mainActivity: MainActivity)
}