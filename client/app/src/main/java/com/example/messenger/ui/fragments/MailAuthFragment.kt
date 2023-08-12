package com.example.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.messenger.adapters.ViewPagerAuthAdapter
import com.example.messenger.databinding.MailAuthBinding
import com.example.messenger.databinding.MailLoginBinding
import com.example.messenger.databinding.MailRegistrationBinding
import com.google.android.material.tabs.TabLayoutMediator

class MailAuthFragment: Fragment() {
    private var _binding: MailAuthBinding? = null
    private val binding get() = _binding!!

    private val labels = arrayOf("Вход", "Регистрация")
    private lateinit var viewPagerAuthAdapter: ViewPagerAuthAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MailAuthBinding.inflate(inflater, container, false)
        val view = binding.root

        initViewPager()
        return view
    }

    private fun initViewPager()
    {
        viewPagerAuthAdapter = ViewPagerAuthAdapter(this)
        binding.viewPager.adapter = viewPagerAuthAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = labels[position]
        }.attach()
    }
}