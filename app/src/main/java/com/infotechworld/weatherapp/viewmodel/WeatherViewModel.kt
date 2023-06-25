package com.infotechworld.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infotechworld.weatherapp.Repository.WeatherDataRepo
import com.infotechworld.weatherapp.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: WeatherDataRepo) : ViewModel() {

    /*init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getData("United+States")
        }
    }

    val data : LiveData<List<UnivercityDataItem>>
    get() = repo.data*/

    //https://api.openweathermap.org/data/2.5/onecall?lat=19.456360&lon=72.792458&units=imperial&appid=b143bb707b2ee117e62649b358207d3e
    init {
        refreshData()
    }

    fun refreshData(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getData(19.456360, 72.792458, "imperial", "b143bb707b2ee117e62649b358207d3e")
        }
    }

    val data : LiveData<WeatherModel>
        get() = repo.data
}