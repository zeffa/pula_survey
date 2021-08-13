package com.pula.pulasurvey.di

import com.google.gson.GsonBuilder
import com.pula.pulasurvey.BuildConfig
import com.pula.pulasurvey.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    @Singleton
    @Provides
    @Named("server_interceptor")
    fun provideOkHttpInterceptor(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .build()
            chain.proceed(newRequest)
        }.apply {
            if (BuildConfig.DEBUG) this.addInterceptor(loggingInterceptor)
        }.build()

    @Singleton
    @Provides
    fun provideCoroutineIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}