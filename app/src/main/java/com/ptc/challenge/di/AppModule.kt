package com.ptc.challenge.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ptc.challenge.data.local.Database
import com.ptc.challenge.data.prefrances.PreferencesImpl
import com.ptc.challenge.domain.preferences.Preferences
import com.ptc.challenge.utils.Constants.Companion.DB_NAME
import com.ptc.challenge.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): Preferences {
        return PreferencesImpl(context)
    }


    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app,
            Database::class.java,
            DB_NAME
        ).build()
    }


    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
        }
    }

}