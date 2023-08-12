package com.example.messenger.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.databinding.FindLocationsBinding
import com.example.messenger.databinding.FindLocationsStartBinding
import com.example.messenger.databinding.HomeContainerBinding

class LocationDialog:DialogFragment() {
    private lateinit var binding: FindLocationsStartBinding
    private lateinit var locationManager: LocationManager
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return Dialog(requireContext(), R.style.FullScreenDialog).apply {
            binding = FindLocationsStartBinding.inflate(layoutInflater)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)

            binding.button.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        findNavController().navigate(R.id.fragmentHomePage)
    }

    override fun onResume() {
        super.onResume()
        if (isLocationEnabled()) {
            this.dismiss()
        }
    }
    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}
