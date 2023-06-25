package com.infotechworld.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infotechworld.weatherapp.R
import com.infotechworld.weatherapp.databinding.RowLayoutBinding
import com.infotechworld.weatherapp.databinding.RowLayoutForecastBinding
import com.infotechworld.weatherapp.model.Hourly
import com.infotechworld.weatherapp.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeatherForecastAdapter(val arrayList: WeatherModel) :
    RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowLayoutForecastBinding) : RecyclerView.ViewHolder(binding.root)
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(RowLayoutForecastBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            with(arrayList.daily[position]){
                val c = Calendar.getInstance()
                c.timeInMillis = this.dt.toLong() * 1000
                val d = c.time
                val sdf = SimpleDateFormat("EEE")

                binding.tvForcastDay.text = sdf.format(d)
                binding.tvForcastDayDesc.text = "${this.weather.get(0).description}, ${this.rain}mm"
                binding.tvForcastDayMax.text = "${(((this.temp.max - 32) * 5) / 9).roundToInt()}°"
                binding.tvForcastDayMin.text = "${(((this.temp.min - 32) * 5) / 9).roundToInt()}°"

                if (this.weather.get(0).main.toLowerCase(Locale.ROOT).equals("clouds")) {
                    binding.imageViewForcast.setImageDrawable(context!!.resources.getDrawable(R.drawable.cloudy_3))
                } else if (this.weather.get(0).main.toLowerCase(Locale.ROOT).equals("rain")) {
                    binding.imageViewForcast.setImageDrawable(context!!.resources.getDrawable(R.drawable.rainy))
                } else if (this.weather.get(0).main.toLowerCase(Locale.ROOT).equals("strom")) {
                    binding.imageViewForcast.setImageDrawable(context!!.resources.getDrawable(R.drawable.storm))
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.daily.size
    }
}