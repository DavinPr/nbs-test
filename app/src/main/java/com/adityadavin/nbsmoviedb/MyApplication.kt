package com.adityadavin.nbsmoviedb

import android.app.Application
import com.adityadavin.nbsmoviedb.core.di.databaseModule
import com.adityadavin.nbsmoviedb.core.di.networkModule
import com.adityadavin.nbsmoviedb.core.di.repositoryModule
import com.adityadavin.nbsmoviedb.di.useCaseModule
import com.adityadavin.nbsmoviedb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(applicationContext)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

}