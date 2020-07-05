package com.geekhub.dagger2test.di.modules

import android.content.Context
import com.geekhub.dagger2test.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext
}