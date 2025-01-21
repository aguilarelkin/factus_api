package com.factus.app.data.network

import android.content.Context
import com.factus.app.data.RepositoryDataStoreImpl
import com.factus.app.data.RepositoryLoginImpl
import com.factus.app.domain.repository.DataStoreRepository
import com.factus.app.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provide Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://api-sandbox.factus.com.co/").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    /**
     * Provide interceptor for info apis
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES).writeTimeout(30, TimeUnit.MINUTES).addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()
    }

    /**
     * Provide Service API Login
     */
    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Provides
    fun provideProductRepository(
        loginApiService: LoginApiService,
        dataStoreRepository: DataStoreRepository
    ): LoginRepository {
        return RepositoryLoginImpl(loginApiService, dataStoreRepository)
    }

    /**
     * Provide DataStore
     */
    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreRepository {
        return RepositoryDataStoreImpl(context)
    }
}