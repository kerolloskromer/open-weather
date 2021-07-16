package com.kromer.openweather.features.weather.presentation.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kromer.openweather.databinding.ItemCityBinding
import com.kromer.openweather.features.weather.domain.entities.City

class CityViewHolder(itemView: View, itemClick: (City) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = ItemCityBinding.bind(itemView)
    private var currentCity: City? = null

    init {
        itemView.setOnClickListener {
            currentCity?.let {
                itemClick(it)
            }
        }
    }

    fun bind(item: City) {
        currentCity = item

        binding.tvCity.text = item.name
    }
}