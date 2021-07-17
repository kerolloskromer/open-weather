package com.kromer.openweather.features.weather.presentation.details

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kromer.openweather.R
import com.kromer.openweather.core.utils.Utils
import com.kromer.openweather.databinding.ItemForecastBinding
import com.kromer.openweather.features.weather.domain.entities.Forecast

class ForecastViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = ItemForecastBinding.bind(itemView)

    fun bind(item: Forecast) {
        binding.tvTimestamp.text = Utils.getDate(item.timestamp, itemView.context)
        binding.tvTemp.text = itemView.context.getString(R.string.forecast_temp, item.temp)
        binding.tvHumidity.text =
            itemView.context.getString(R.string.forecast_humidity, item.humidity)
        binding.tvPressure.text =
            itemView.context.getString(R.string.forecast_pressure, item.pressure)
        binding.tvDescription.text =
            itemView.context.getString(R.string.forecast_description, item.description)
    }
}