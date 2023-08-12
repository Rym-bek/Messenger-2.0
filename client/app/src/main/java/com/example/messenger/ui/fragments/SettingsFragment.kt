package com.example.messenger.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.messenger.R
import com.example.messenger.models.User
import com.example.messenger.utils.BaseResponse
import com.example.messenger.viewmodels.UserViewModel


class SettingsFragment : PreferenceFragmentCompat() {

    var userModel: User? = null

    private var nicknamePreference: Preference? = null
    private var namePreference: Preference? = null
    private var aboutPreference: Preference? = null
    private var phoneNumberPreference: Preference? = null
    private var emailPreference: Preference? = null

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        nicknamePreference = findPreference("nickname")
        namePreference = findPreference("name")
        aboutPreference = findPreference("about")
        phoneNumberPreference = findPreference("phone_number")
        emailPreference = findPreference("email")

        listeners()
        observers()
    }

    private fun observers() {
        userViewModel.userData.observe(this) {
            userModel= it
            setUserInfo()
        }
    }

    private fun setUserInfo() {
        val nickname = userModel?.nickname
        val fullName = userModel?.firstname+" "+userModel?.lastname
        val about = userModel?.about
        val phone = userModel?.phone
        val email = userModel?.email
        if(nickname!=null)
        {
            nicknamePreference?.title = nickname
        }
        if(userModel?.firstname!=null)
        {
            namePreference?.title = fullName
        }
        if(about!=null)
        {
            aboutPreference?.title = about
        }
        if(phone!=null)
        {
            phoneNumberPreference?.title = phone
        }
        if(email!=null)
        {
            emailPreference?.title = email
        }
    }

    private fun loadFragment(fragment: Fragment) {
        requireParentFragment().childFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, fragment)
            .addToBackStack("second frag")
            .commit()
    }

    private fun listeners()
    {
        nicknamePreference?.setOnPreferenceClickListener {
            loadFragment(FragmentEditUserNickname())
            true
        }

        namePreference?.setOnPreferenceClickListener {
            loadFragment(FragmentEditUserName())
            true
        }

        aboutPreference?.setOnPreferenceClickListener {
            loadFragment(FragmentEditUserAbout())
            true
        }

        phoneNumberPreference?.setOnPreferenceClickListener {

            true
        }

        emailPreference?.setOnPreferenceClickListener {

            true
        }
    }
}