package com.example.messenger.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.messenger.ui.fragments.FragmentMailLogin
import com.example.messenger.ui.fragments.FragmentMailRegistration
import com.example.messenger.ui.fragments.MailAuthFragment

class ViewPagerAuthAdapter(fragment: MailAuthFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return FragmentMailLogin()
            }
            1 -> {
                return FragmentMailRegistration()
            }
        }
        return FragmentMailLogin()
    }
}