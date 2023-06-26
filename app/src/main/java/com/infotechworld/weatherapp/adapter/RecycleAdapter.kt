package com.infotechworld.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infotechworld.weatherapp.R
import com.infotechworld.weatherapp.databinding.RowLayoutBinding
import com.infotechworld.weatherapp.model.Hourly
import com.infotechworld.weatherapp.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class RecycleAdapter(val arrayList: WeatherModel) :
    RecyclerView.Adapter<RecycleAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(RowLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            with(arrayList.hourly[position]){
                val c = Calendar.getInstance()
                c.timeInMillis = this.dt.toLong() * 1000
                val d = c.time
                val sdf = SimpleDateFormat("dd/mm hh:mm aa")

                binding.tvTimeHourly.text = sdf.format(d)
                binding.tvHumidity.text = "${this.humidity}%"
                binding.tvTempHourly.text = "${(((this.temp - 32) * 5) / 9).roundToInt()}°"

                if (this.weather.get(0).main.toLowerCase(Locale.ROOT).equals("clouds")) {
                    binding.imageView.setImageDrawable(context!!.resources.getDrawable(R.drawable.cloudy_3))
                } else if (this.weather.get(0).main.toLowerCase(Locale.ROOT).equals("rain")) {
                    binding.imageView.setImageDrawable(context!!.resources.getDrawable(R.drawable.rainy))
                } else if (this.weather.get(0).main.toLowerCase(Locale.ROOT).equals("strom")) {
                    binding.imageView.setImageDrawable(context!!.resources.getDrawable(R.drawable.storm))
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.hourly.size
    }
}