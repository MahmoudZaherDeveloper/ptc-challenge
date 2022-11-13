package com.ptc.challenge.di

import android.content.Context
import com.ptc.challenge.data.local.Database
import com.ptc.challenge.data.remote.HttpRequestInterceptor
import com.ptc.challenge.data.remote.NetworkResponseAdapterFactory
import com.ptc.challenge.data.remote.service.ApiService
import com.ptc.challenge.data.repository.RepositoryImpl
import com.ptc.challenge.domain.preferences.Preferences
import com.ptc.challenge.domain.repository.Repository
import com.ptc.challenge.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        api: ApiService,
        db: Database,
        @ApplicationContext context: Context
    ): Repository {
        return RepositoryImpl(
            api = api,
            dao = db.dao()
        )
    }


}

