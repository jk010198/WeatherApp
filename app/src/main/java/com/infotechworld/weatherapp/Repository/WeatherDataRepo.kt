package com.infotechworld.weatherapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.infotechworld.weatherapp.model.WeatherModel
import com.infotechworld.weatherapp.network.WeatherNetwork
import javax.inject.Inject

class WeatherDataRepo @Inject constructor(private val api: WeatherNetwork) {

    /*private val mData = MutableLiveData<List<UnivercityDataItem>>()

    val data : LiveData<List<UnivercityDataItem>>
    get() = mData

    suspend fun getData(state: String){
        val result = api.getData(state)
        if (result.isSuccessful && result.body() != null){
            mData.postValue(result.body())
        }

    }*/

    private val mData = MutableLiveData<WeatherModel>()

    val data : LiveData<WeatherModel>
        get() = mData

    suspend fun getData(lat: Double, lon: Double, units: String, appid: String){
        val result = api.getData(lat, lon, units, appid)
        if (result.isSuccessful && result.body() != null){
            mData.postValue(result.body())
        }

    }
}