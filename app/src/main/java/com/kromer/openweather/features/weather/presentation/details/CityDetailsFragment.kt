package com.kromer.openweather.features.weather.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.kromer.openweather.R
import com.kromer.openweather.core.network.Status
import com.kromer.openweather.core.utils.MarginItemDecoration
import com.kromer.openweather.core.view.BaseFragment
import com.kromer.openweather.databinding.FragmentCityDetailsBinding
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.entities.Forecast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityDetailsFragment : BaseFragment<FragmentCityDetailsBinding>() {

    private val viewModel: CityDetailsViewModel by viewModels()
    private var cityId: Long = 0
    private val adapter: ForecastAdapter = ForecastAdapter()

    override fun getVBInflater(): (LayoutInflater) -> FragmentCityDetailsBinding =
        FragmentCityDetailsBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtras()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun getExtras() {
        val args = CityDetailsFragmentArgs.fromBundle(requireArguments())
        cityId = args.cityId
    }

    private fun setupObservers() {
        viewModel.getCityById(cityId).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {

                }

                Status.ERROR -> {

                    showError(it.message!!)
                }

                Status.SUCCESS -> {
                    setData(it.data!!)
                }
            }
        })
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun setData(city: City) {
        binding.tvCity.text = city.name
        setupRecyclerView(city.forecastList)
    }

    private fun setupRecyclerView(forecasts: List<Forecast>) {
        binding.recyclerView.addItemDecoration(
            MarginItemDecoration(
                0,
                resources.getDimensionPixelSize(R.dimen.margin),
                0,
                resources.getDimensionPixelSize(R.dimen.margin)
            )
        )
        binding.recyclerView.adapter = adapter
        adapter.submitList(forecasts)
    }
}
