package com.infotechworld.weatherapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.infotechworld.weatherapp.adapter.RecycleAdapter
import com.infotechworld.weatherapp.databinding.ActivityMainBinding
import com.infotechworld.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewmodel: WeatherViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        viewmodel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        binding.shimmar.startShimmer()
        getWeatherData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.MainConstraint.visibility = View.GONE
            binding.shimmar.visibility = View.VISIBLE
            binding.shimmar.startShimmer()
            binding.swipeRefreshLayout.isRefreshing = true
            Handler().postDelayed(Runnable {
                viewmodel.refreshData()
                binding.swipeRefreshLayout.isRefreshing = false
                binding.MainConstraint.visibility = View.VISIBLE
                binding.shimmar.visibility = View.GONE
            }, 2500)
        }

        binding.tvNext7DayData.setOnClickListener({
            startActivity(Intent(this, TommorowWeatherActivity::class.java))
        })
    }

    private fun getWeatherData() {
        viewmodel.data.observe(this, Observer {

            val c = Calendar.getInstance()
            c.timeInMillis = it.current.dt.toLong() * 1000
            val d = c.time
            val sdf = SimpleDateFormat("EEE MMM/dd/yyyy hh:mm:ss")
            binding.tvDate.text = sdf.format(d)

            binding.tvWeather.text = it.hourly.get(0).weather.get(0).main.toString()

            binding.tvTemp.text = "${(((it.current.temp - 32) * 5) / 9).roundToInt()}°"
            binding.tvTempFeels.text =
                "${(((it.daily.get(0).temp.max - 32) * 5) / 9).roundToInt()}° / ${(((it.daily.get(0).temp.min - 32) * 5) / 9).roundToInt()}° Feels like ${(((it.current.feels_like - 32) * 5) / 9).roundToInt()}°"

            binding.tvUVIndex.text = (if (it.current.uvi.toInt() <= 2) {
                binding.imageViewUV.setImageDrawable(resources.getDrawable(R.drawable.green_circle))
                "Low"
            } else if (it.current.uvi.toInt() >= 3 && it.current.uvi.toInt() <= 5) {
                binding.imageViewUV.setImageDrawable(resources.getDrawable(R.drawable.yellow_circle))
                "Medium"
            } else if (it.current.uvi.toInt() >= 6 && it.current.uvi.toInt() <= 7) {
                binding.imageViewUV.setImageDrawable(resources.getDrawable(R.drawable.orange_circle))
                "High"
            } else if (it.current.uvi.toInt() >= 8 && it.current.uvi.toInt() <= 10) {
                binding.imageViewUV.setImageDrawable(resources.getDrawable(R.drawable.red_circle))
                "Very high"
            } else {
                binding.imageViewUV.setImageDrawable(resources.getDrawable(R.drawable.violet_circle))
                "Extremly high"
            }).toString()

            binding.tvWindSpeed.text = "${it.current.wind_speed.roundToInt().toString()} Kmph"
            binding.tvHumidity.text = "${it.current.humidity.toString()}%"

            if (it.hourly.get(0).weather.get(0).main.toLowerCase(Locale.ROOT).equals("clouds")) {
                binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.cloudy_3))
            } else if (it.hourly.get(0).weather.get(0).main.toLowerCase(Locale.ROOT).equals("rain")) {
                binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.rainy))
            } else if (it.hourly.get(0).weather.get(0).main.toLowerCase(Locale.ROOT).equals("sunny")) {
                binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.cloudy_sunny))
            }


            c.timeInMillis = it.current.sunrise.toLong() * 1000
            val dateSunrise = c.time
            val sdfSun = SimpleDateFormat("hh:mm aa")
            binding.tvSunrise.text = "Sunrise\n ${sdfSun.format(dateSunrise)} "

            c.timeInMillis = it.current.sunset.toLong() * 1000
            val dateSunset = c.time
            binding.tvSunset.text = "Sunset\n ${sdfSun.format(dateSunset)} "

            binding.recyclerView.adapter = RecycleAdapter(it)

            binding.shimmar.stopShimmer()
            binding.MainConstraint.visibility = View.VISIBLE
            binding.shimmar.visibility = View.GONE
        })
    }

    override fun onRestart() {
        super.onRestart()
        viewmodel.refreshData()
    }
}