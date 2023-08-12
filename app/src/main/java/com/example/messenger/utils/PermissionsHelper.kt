package com.example.messenger.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionsHelper(private val fragment: Fragment, private val permissions: Array<String>) {

    fun hasPermissions(): Boolean
    {
        return if ( permissions.all { ActivityCompat.checkSelfPermission(fragment.requireContext(), it) == PackageManager.PERMISSION_GRANTED }) {
            true
        }
        else {
            requestPermission()
            false
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(
            permissions
        )
    }


    private val requestPermissionLauncher =
        fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                // Permission granted
            }
        }
}