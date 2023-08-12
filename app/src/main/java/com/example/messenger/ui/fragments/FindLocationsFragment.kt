package com.example.messenger.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.R
import com.example.messenger.adapters.UserLocationsAdapter
import com.example.messenger.databinding.FindLocationsBinding
import com.example.messenger.databinding.FindLocationsStartBinding
import com.example.messenger.models.LocationData
import com.example.messenger.models.LocationUser
import com.example.messenger.utils.*
import com.example.messenger.utils.constants.Constants
import com.example.messenger.utils.constants.ConstantsPermissions
import com.example.messenger.viewmodels.LocationViewModel
import com.google.android.gms.location.*

class FindLocationsFragment: Fragment() {
    private var _binding: FindLocationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var bindingSecond: FindLocationsStartBinding

    private val permissionsHelper: PermissionsHelper = PermissionsHelper(this, ConstantsPermissions.PERMISSIONS_LOCATION)

    private lateinit var locationManager: LocationManager

    private val locationViewModel: LocationViewModel by activityViewModels()

    private val locations: ArrayList<LocationUser> = ArrayList()

    private lateinit var viewManager: LinearLayoutManager

    private lateinit var userLocationsAdapter: UserLocationsAdapter

    private var hasPermission:Boolean = false

    private val uid = UserManager.user?.uid

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setLocationManager()
        _binding = FindLocationsBinding.inflate(inflater, container, false)
        val view = binding.root
        if(!isLocationEnabled()) {
            findNavController().navigate(R.id.action_findLocationsFragment_to_locationDialog)
        }

        initToolbar()

        setAdapter()
        return view
    }

    override fun onResume() {
        super.onResume()
        if(isLocationEnabled()) {
            setMainScreen()
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMainScreen()
    {
        hasPermission=permissionsHelper.hasPermissions()

        observeLocationStatus()
        observeLocations()

        listeners()

        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    getLocation(location)
                }
            }

        getSharedPref()

        readSharedPref()
    }

    private fun readSharedPref()
    {
        val switchState = sharedPreferences.getBoolean(Constants.SWITCH_STATE, false)

        binding.switchCompat.isChecked = switchState
    }

    private fun getSharedPref()
    {
        sharedPreferences = requireActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_STORAGE, Context.MODE_PRIVATE)
    }

    private fun setSharedPref()
    {
        val editor = sharedPreferences.edit()
        editor.putBoolean(Constants.SWITCH_STATE, binding.switchCompat.isChecked)
        editor.apply()
    }


    private fun setLocation(location:Location)
    {
        if(binding.switchCompat.isChecked)
        {
            locationViewModel.setLocation(
                LocationData(
                    uid = uid!!,
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            )
        }
    }

    private fun getLocation(location:Location)
    {
        locationViewModel.getLocations(
            LocationData(
                uid = uid!!,
                latitude = location.latitude,
                longitude = location.longitude
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun listeners()
    {
        binding.switchCompat.setOnCheckedChangeListener { buttonView, isChecked ->
            setSharedPref()
            if (isChecked) {
                fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location : Location? ->
                        if (location != null) {
                            setLocation(location)
                        }
                    }
            } else {
                if (uid != null) {
                    locationViewModel.enableLocation(uid,false)
                }
            }
        }
    }

    private fun setAdapter()
    {
        userLocationsAdapter = UserLocationsAdapter(requireContext(), locations)
        viewManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            adapter = userLocationsAdapter
            layoutManager = viewManager
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeLocations()
    {
        locationViewModel.locations.observe(viewLifecycleOwner)
        {
            when (it) {
                is BaseResponse.Loading -> {

                }
                is BaseResponse.Success -> {
                    locations.clear()
                    it.data?.let {
                            it1 -> locations.addAll(it1)
                    }
                    userLocationsAdapter.notifyDataSetChanged()
                }
                is BaseResponse.Error -> {
                }
                else -> {}
            }
        }
    }

    private fun observeLocationStatus()
    {
        locationViewModel.setStatus.observe(viewLifecycleOwner)
        {
            if(it == true)
            {
                Toast.makeText(requireContext(), "Местоположение обновлено", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(requireContext(), "Ошибка обновления местоположения", Toast.LENGTH_SHORT).show()
            }
        }

        locationViewModel.enableStatus.observe(viewLifecycleOwner)
        {
            if(it == true)
            {
                Toast.makeText(requireContext(), "Вас больше не видно", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initToolbar()
    {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun setLocationManager()
    {
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}