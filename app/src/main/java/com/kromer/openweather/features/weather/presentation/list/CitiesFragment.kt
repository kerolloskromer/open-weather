package com.kromer.openweather.features.weather.presentation.list

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kromer.openweather.BuildConfig
import com.kromer.openweather.R
import com.kromer.openweather.core.network.Status
import com.kromer.openweather.core.utils.MarginItemDecoration
import com.kromer.openweather.core.utils.SwipeToDeleteCallback
import com.kromer.openweather.core.utils.Utils
import com.kromer.openweather.core.view.BaseFragment
import com.kromer.openweather.core.view.extensions.hasPermission
import com.kromer.openweather.core.view.extensions.hide
import com.kromer.openweather.core.view.extensions.show
import com.kromer.openweather.databinding.FragmentCitiesBinding
import com.kromer.openweather.features.weather.domain.entities.City
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CitiesFragment : BaseFragment<FragmentCitiesBinding>() {

    companion object {
        var PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        const val STORAGE_LIMIT = 5
        const val DEFAULT_CITY = "London,UK"
    }

    private val viewModel: CitiesViewModel by viewModels()
    private val adapter: CitiesAdapter = CitiesAdapter { onItemClick(it) }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                viewModel.onLocationPermissionGranted()
            } else {
                viewModel.onLocationPermissionDenied()
            }
        }

    override fun getVBInflater(): (LayoutInflater) -> FragmentCitiesBinding =
        FragmentCitiesBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
        getAllCities()
        checkAndRequestLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        val searchView: SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getWeather(query, null, null)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.addItemDecoration(
            MarginItemDecoration(
                0,
                resources.getDimensionPixelSize(R.dimen.margin),
                0,
                resources.getDimensionPixelSize(R.dimen.margin)
            )
        )
        binding.recyclerView.adapter = adapter

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val city = adapter.getCity(viewHolder.adapterPosition)
                viewModel.deleteCity(city)
                showUndoSnackbar(city)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun onItemClick(item: City) {
        val action = CitiesFragmentDirections.actionNavigationCitiesToDetails(item.id)
        findNavController().navigate(action)
    }

    private fun showLoading() {
        binding.progressBar.show()
    }

    private fun hideLoading() {
        binding.progressBar.hide()
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun getAllCities() {
        showLoading()
        viewModel.getAllCities().observe(viewLifecycleOwner, {
            adapter.submitList(it)
            hideLoading()
        })
    }

    private fun getWeather(name: String?, lat: Double?, lng: Double?) {
        viewModel.getWeather(name, lat, lng).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    hideLoading()
                    showError(it.message!!)
                }

                Status.SUCCESS -> {
                    hideLoading()
                    val city = it.data!!
                    Utils.showWarning(
                        getString(R.string.save_city_title),
                        getString(R.string.save_city_message, city.name),
                        requireContext()
                    ) {
                        if (adapter.itemCount == STORAGE_LIMIT) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.storage_limit, STORAGE_LIMIT),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            viewModel.addCity(city)
                        }
                    }
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.permissionGranted.observe(viewLifecycleOwner, {
            if (it) {
                getLocation()
            } else {
                showExplanation()
                getWeather(DEFAULT_CITY, null, null)
            }
        })
    }

    private fun showExplanation() {
        Snackbar.make(
            binding.container,
            R.string.fine_permission_denied_explanation,
            Snackbar.LENGTH_LONG
        ).setAction(R.string.settings) {
            // Build intent that displays the App settings screen.
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts(
                "package",
                BuildConfig.APPLICATION_ID,
                null
            )
            intent.data = uri
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }.show()
    }

    private fun getLocation() {
        viewModel.getLocation().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    hideLoading()
                    showError(it.message!!)
                }

                Status.SUCCESS -> {
                    getWeather(null, it.data?.latitude, it.data?.longitude)
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.cancel()
    }

    private fun checkAndRequestLocation() {
        Timber.d("checkAndRequestLocation()")

        val permissionApproved =
            requireContext().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionApproved) {
            viewModel.onLocationPermissionGranted()
        } else {
            permReqLauncher.launch(PERMISSIONS)
        }
    }

    private fun showUndoSnackbar(city: City) {
        val snackbar: Snackbar = Snackbar.make(
            binding.container, getString(R.string.undo_message),
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction(getString(R.string.undo)) { v -> viewModel.addCity(city) }
        snackbar.show()
    }
}