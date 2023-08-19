package com.apex.codeassesment.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.apex.codeassesment.BuildConfig
import com.apex.codeassesment.data.local.PreferencesHelper
import com.apex.codeassesment.data.local.PreferencesManager
import com.apex.codeassesment.data.remote.APIClient
import com.apex.codeassesment.data.repository.UserRepository
import com.apex.codeassesment.data.repository.UserRepositoryInf
import com.apex.codeassesment.utils.PREF_NAME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun providePreferences(appPrefs: PreferencesManager): PreferencesHelper

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepository: UserRepository): UserRepositoryInf


    companion object {

        @Provides
        @Singleton
        fun provideGson(): Gson {
            return GsonBuilder().create()
        }

        @Provides
        @PreferenceInfo
        fun providePrefName(): String {
            return PREF_NAME
        }

        @Provides
        @Singleton
        fun provideSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("random-user-preferences", Context.MODE_PRIVATE)
        }

        @Provides
        @Singleton
        fun provideContext(application: Application): Context = application.applicationContext

        @Singleton
        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return loggingInterceptor
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
            if (BuildConfig.DEBUG) {
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else OkHttpClient
                .Builder()
                .build()

        @Provides
        @Singleton
        fun provideRetrofit(
            okHttpClient: OkHttpClient
        ): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): APIClient =
            retrofit.create(APIClient::class.java)

    }
}