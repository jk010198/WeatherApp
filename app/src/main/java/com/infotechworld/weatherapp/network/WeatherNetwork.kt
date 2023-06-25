package com.infotechworld.weatherapp.network

import com.infotechworld.weatherapp.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherNetwork {

    /*@GET("search")
    //@GET("/coordsOfTrip?id={id}")
    suspend fun getData(@Query("country") state: String): Response<List<UnivercityDataItem>>*/

    ////onecall?lat=12.9082847623315&lon=77.65197822993314&units=imperial&appid=b143bb707b2ee117e62649b358207d3e
    @GET("onecall")
    suspend fun getData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double, @Query("units") units: String, @Query("appid") appid: String
    ): Response<WeatherModel>
}