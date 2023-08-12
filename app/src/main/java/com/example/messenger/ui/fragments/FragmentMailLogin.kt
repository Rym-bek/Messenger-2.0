package com.example.messenger.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messenger.databinding.MailLoginBinding
import com.example.messenger.models.ProfileAuth
import com.example.messenger.ui.HomePage
import com.example.messenger.utils.BaseResponse
import com.example.messenger.viewmodels.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMailLogin : Fragment(){
    private val viewModelLogin : ViewModelLogin by viewModels()
    private val tokenViewModel : TokenViewModel by viewModels()
    private var _binding: MailLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MailLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.materialButtonEnter.setOnClickListener{
            loginAccount(binding.textInputEditTextEmail.text.toString(),
                binding.textInputEditTextPassword.text.toString())
        }
    }

    private fun loginAccount(email: String, password: String) {
        viewModelLogin.loginAccount(ProfileAuth(email,password))

        viewModelLogin.loginResponse.observe(viewLifecycleOwner)
        {
            when (it) {
                is BaseResponse.Loading -> {

                }
                is BaseResponse.Success -> {
                    it.data?.let { it1 -> tokenViewModel.saveToken(it1.token) }
                    goToHome()
                }
                is BaseResponse.Error -> {

                }
            }
        }
    }

    private fun goToHome()
    {
        val intent = Intent(activity, HomePage::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}