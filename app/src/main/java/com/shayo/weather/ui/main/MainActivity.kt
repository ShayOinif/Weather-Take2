package com.shayo.weather.ui.main

import android.Manifest
import android.content.Context
import android.content.IntentFilter
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.shayo.weather.R
import com.shayo.weather.broadcast.GpsReceiver
import com.shayo.weather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PERMISSIONS_PREFERENCES_NAME = "permissions"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PERMISSIONS_PREFERENCES_NAME)
private val DENIED_PERMISSION = booleanPreferencesKey("denied_permission")

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private var shownGps = false

    @Inject
    lateinit var gpsReceiver: GpsReceiver

    private lateinit var snackbar: Snackbar

    private lateinit var deniedPermissionFlow: Flow<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        deniedPermissionFlow = dataStore.data
            .map { preferences ->
                preferences[DENIED_PERMISSION] ?: false
            }

        setupSnackbar()

        registerPermissionLauncher()

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
                        Log.d("Shay", hasGps.toString())
                        if (hasGps) {
                            shownGps = false
                        } else {
                            if (!shownGps) {
                                shownGps = true
                                turnOnGps()
                            }
                        }
                    }
                }

            }
        }
    }

    private fun registerPermissionLauncher() {
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    lifecycleScope.launch {
                        dataStore.edit { preferences ->
                            preferences[DENIED_PERMISSION] = false
                        }
                    }

                    mainActivityViewModel.updatePermissionStatus(PermissionStatus.GRANTED)
                } else {
                    lifecycleScope.launch {
                        deniedPermissionFlow.take(1).collectLatest {
                            if (!it)
                                showDialog(
                                    R.string.dialog_no_permission_title,
                                    R.string.coarse_location_no_permission_rationale,
                                    DialogButton(
                                        R.string.dialog_permission_button_positive
                                    ) {
                                        mainActivityViewModel.updatePermissionStatus(
                                            PermissionStatus.DENIED
                                        )
                                    }
                                )

                            dataStore.edit { preferences ->
                                preferences[DENIED_PERMISSION] = true
                            }
                        }
                    }
                }
            }
    }

    private fun checkForLocationPermissions() {

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                lifecycleScope.launch {
                    dataStore.edit { preferences ->
                        preferences[DENIED_PERMISSION] = false
                    }
                }
                mainActivityViewModel.updatePermissionStatus(PermissionStatus.GRANTED)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                showDialog(
                    R.string.dialog_permission_request_title,
                    R.string.permission_rationale,
                    DialogButton(
                        R.string.dialog_permission_button_positive
                    ) {
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    },
                    DialogButton(
                        R.string.dialog_permission_button_negative
                    ) {
                        mainActivityViewModel.updatePermissionStatus(PermissionStatus.DENIED)

                        lifecycleScope.launch {
                            dataStore.edit { preferences ->
                                preferences[DENIED_PERMISSION] = true
                            }
                        }
                    }
                )
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        checkForLocationPermissions()

        registerReceiver(
            gpsReceiver,
            IntentFilter(LocationManager.MODE_CHANGED_ACTION)
        )

        mainActivityViewModel.registerNetworkMonitor()
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(gpsReceiver)
        mainActivityViewModel.unregisterNetworkMonitor()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun setupSnackbar() {
        snackbar =
            Snackbar.make(
                binding.root,
                R.string.no_internet_message,
                Snackbar.LENGTH_INDEFINITE
            )
    }

    private fun turnOnGps() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(
            LocationRequest.create()
        )

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnFailureListener { exception ->
            Log.d("Shay", "Exception")
            if (exception is ResolvableApiException) {
                Log.d("Shay", "Resolvable")
                try {
                    Log.d("Shay", "Try")
                    exception.startResolutionForResult(this@MainActivity, 1)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d("Shay", "Catch")
                }
            } else {
                showDialog(
                    R.string.turn_on_gps_title,
                    R.string.turn_on_gps_message,
                    DialogButton(
                        R.string.dialog_permission_button_positive
                    ) {}
                )
            }
        }
    }
}

private fun MainActivity.showDialog(
    @StringRes
    titleStringId: Int,
    @StringRes
    messageStringId: Int,
    positiveDialogButton: DialogButton,
    negativeDialogButton: DialogButton? = null
) {
    val dialog = AlertDialog.Builder(this)
        .setTitle(getString(titleStringId))
        .setMessage(getString(messageStringId))
        .setCancelable(false)
        .setPositiveButton(getString(positiveDialogButton.textStringId)) { _, _ ->
            positiveDialogButton.block()
        }

    negativeDialogButton?.run {
        dialog.setNegativeButton(getString(negativeDialogButton.textStringId)) { _, _ ->
            negativeDialogButton.block()
        }
    }

    dialog.show()
}

private data class DialogButton(
    @StringRes
    val textStringId: Int,
    val block: () -> Unit
)