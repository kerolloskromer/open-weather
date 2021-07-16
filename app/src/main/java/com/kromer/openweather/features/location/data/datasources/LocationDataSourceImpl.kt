package com.kromer.openweather.features.location.data.datasources

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.kromer.openweather.features.location.domain.entities.Location
import timber.log.Timber
import java.util.concurrent.ExecutionException


class LocationDataSourceImpl(private val context: Context) :
    LocationDataSource {

    // The Fused Location Provider provides access to location APIs.
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // Allows class to cancel the location request if it exits the activity.
    // Typically, you use one cancellation source per lifecycle.
    private var cancellationTokenSource = CancellationTokenSource()

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Location? {
        // Returns a single current location fix on the device. Unlike getLastLocation() that
        // returns a cached location, this method could cause active location computation on the
        // device. A single fresh location will be returned if the device location can be
        // determined within reasonable time (tens of seconds), otherwise null will be returned.
        //
        // Both arguments are required.
        // PRIORITY type is self-explanatory. (Other options are PRIORITY_BALANCED_POWER_ACCURACY,
        // PRIORITY_LOW_POWER, and PRIORITY_NO_POWER.)
        // The second parameter, [CancellationToken] allows the activity to cancel the request
        // before completion.
        val currentLocationTask: Task<android.location.Location> =
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )


        try {
            val result = Tasks.await(currentLocationTask)
            if (result != null) {
                Timber.d("Location (success): ${result.latitude}, ${result.longitude}")
                return Location(result.latitude, result.longitude)
            } else {
                Timber.d("Location (failure):")
            }
        } catch (e: ExecutionException) {
            // The Task failed, this is the same exception you'd get in a non-blocking
            // failure handler.
            // ...
        } catch (e: InterruptedException) {
            // An interrupt occurred while waiting for the task to complete.
            // ...
        }
        return null
    }

    override suspend fun cancel() {
        Timber.d("Location cancel")
        // Cancels location request (if in flight).
        cancellationTokenSource.cancel()
    }
}