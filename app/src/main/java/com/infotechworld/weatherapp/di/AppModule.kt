package com.infotechworld.weatherapp.di

import com.infotechworld.weatherapp.network.WeatherNetwork
import com.infotechworld.weatherapp.util.Constance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun retrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(Constance.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun retrofitApi(retrofit: Retrofit): WeatherNetwork {
        return retrofit.create(WeatherNetwork::class.java)
    }
}