package com.example.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.adapters.SliderAdapter
import com.example.messenger.database.DatabaseHelper
import com.example.messenger.databinding.CountryCodePageBinding
import com.example.messenger.databinding.WelcomePageBinding
import com.example.messenger.methods.ZoomOutPageTransformer
import com.example.messenger.models.Slide

class WelcomePageFragment:Fragment() {
    private var _binding: WelcomePageBinding? = null

    private val binding get() = _binding!!

    private lateinit var sliderAdapter: SliderAdapter
    private val arrayListSlider: ArrayList<Slide> = ArrayList()
    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.viewpager.currentItem == 0) {
                    //findNavController().navigateUp()
                    navController.popBackStack()
                } else {
                    binding.viewpager.currentItem = binding.viewpager.currentItem - 1
                }
            }
        })

        listeners()
    }

    private fun listeners() {
        binding.button.setOnClickListener {
            navController.navigate(R.id.action_welcomePageFragment_to_loginPageFragment)
        }
    }

    private fun createDatabase()
    {
        databaseHelper = DatabaseHelper(requireContext())
        databaseHelper.createDataBase()
        databaseHelper.openDataBase()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = WelcomePageBinding.inflate(inflater, container, false)
        val view = binding.root

        navController=findNavController()

        createDatabase()
        arrayListSlider.addAll(databaseHelper.getSliderInfo())
        setAdapter()
        return view
    }

    private fun setAdapter()
    {
        binding.viewpager.setPageTransformer(ZoomOutPageTransformer())
        sliderAdapter = SliderAdapter(requireContext(), arrayListSlider)
        binding.viewpager.adapter = sliderAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}