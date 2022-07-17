package com.shayo.weather.ui.main

import android.content.IntentSender
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.shayo.weather.R
import com.shayo.weather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupSnackbar()

        mainActivityViewModel.registerRequest(this)

        // TODO: Move to functions
        lifecycleScope.launchWhenResumed {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {

                launch {
                    mainActivityViewModel.networkStatus.collectLatest { hasInternet ->
                        if (hasInternet) {
                            snackbar.dismiss()
                        } else {
                            snackbar.show()
                        }
                    }
                }

                launch {
                    mainActivityViewModel.gpsStatus.collectLatest { hasGps ->
                        if (!hasGps)
                            turnOnGps()
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            mainActivityViewModel.registerNetworkListener()
            mainActivityViewModel.registerGpsListener()
            mainActivityViewModel.checkPermissions()
        }
    }

    override fun onStop() {
        super.onStop()

        mainActivityViewModel.unRegisterNetworkListener()
        mainActivityViewModel.unRegisterGpsListener()
    }

    override fun onDestroy() {
        super.onDestroy()

        mainActivityViewModel.unRegisterRequest()
        _binding = null
    }

    private fun setupSnackbar() {
        snackbar =
            Snackbar.make(binding.root, R.string.no_internet_message, Snackbar.LENGTH_INDEFINITE)
    }

    private fun turnOnGps() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(
            LocationRequest.create()
        )

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this@MainActivity, 1)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }


    }
}