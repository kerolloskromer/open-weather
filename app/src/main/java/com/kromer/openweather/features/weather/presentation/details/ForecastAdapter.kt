package com.kromer.openweather.features.weather.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kromer.openweather.R
import com.kromer.openweather.features.weather.domain.entities.Forecast

class ForecastAdapter : ListAdapter<Forecast, ForecastViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(root)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Forecast>() {
            override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean =
                oldItem.timestamp == newItem.timestamp

            override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean =
                oldItem == newItem
        }
    }
}
