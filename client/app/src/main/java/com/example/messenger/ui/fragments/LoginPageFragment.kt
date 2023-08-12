package com.example.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.databinding.LoginPageBinding
import com.example.messenger.methods.CountryCodeTextWatcher
import com.example.messenger.methods.PhoneNumberTextWatcher

class LoginPageFragment: Fragment() {
    private var _binding: LoginPageBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextOptions()

        setTextWatcherCode()

        setTextWatcherNumber()

        listeners()
    }

    private fun listeners() {
        val navController = findNavController()
        binding.textInputLayoutCountry.setEndIconOnClickListener{
            navController.navigate(R.id.action_loginPageFragment_to_countryCodeFragment)
        }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("bundle")?.observe(
            viewLifecycleOwner) {
            binding.textInputEditTextCountry.setText(it.getString("flag") + " " +it.getString("name"))
            binding.textInputEditTextCode.setText(it.getString("code"))
        }

        binding.button.setOnClickListener {
            navController.navigate(R.id.action_loginPageFragment_to_mailAuthFragment)
        }
    }

    private fun setTextWatcherNumber()
    {
        val textWatcher = PhoneNumberTextWatcher(binding.textInputEditTextCode,binding.textInputEditTextNumber)
        binding.textInputEditTextNumber.addTextChangedListener(textWatcher)
    }

    private fun setTextWatcherCode()
    {
        val textWatcher = CountryCodeTextWatcher(requireContext(), binding.textInputEditTextCountry,binding.textInputEditTextNumber)
        binding.textInputEditTextCode.addTextChangedListener(textWatcher)
    }

    private fun editTextOptions()
    {
        binding.textInputEditTextNumber.transformationMethod=null
        binding.textInputEditTextCode.transformationMethod=null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}