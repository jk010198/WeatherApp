package com.infotechworld.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.infotechworld.weatherapp.adapter.RecycleAdapter
import com.infotechworld.weatherapp.adapter.WeatherForecastAdapter
import com.infotechworld.weatherapp.databinding.ActivityMainBinding
import com.infotechworld.weatherapp.databinding.ActivityTommorowWeatherBinding
import com.infotechworld.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TommorowWeatherActivity : AppCompatActivity() {

    lateinit var viewmodel: WeatherViewModel
    lateinit var binding: ActivityTommorowWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tommorow_weather)

        binding.recyclerViewForecast.apply {
            layoutManager = LinearLayoutManager(this@TommorowWeatherActivity)
            setHasFixedSize(true)
        }

        viewmodel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        viewmodel.data.observe(this, Observer {
            binding.recyclerViewForecast.adapter = WeatherForecastAdapter(it)
        })

        binding.imageView2.setOnClickListener({
            onBackPressed()
        })
    }
}