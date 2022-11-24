package com.test.di.component

import android.app.Application
import com.test.MainApplication
import com.test.di.module.ApplicationModule
import com.test.di.module.DatabaseModule
import com.test.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DatabaseModule::class,
        NetworkModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: MainApplication)
}