package com.example.messenger.ui.fragments

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.messenger.R
import com.example.messenger.databinding.EditUserNameBinding
import com.example.messenger.models.User
import com.example.messenger.viewmodels.UserViewModel


class FragmentEditUserName: Fragment(), MenuProvider  {
    private var _binding: EditUserNameBinding? = null

    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditUserNameBinding.inflate(inflater, container, false)
        val view = binding.root
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fab.setOnClickListener {
            setName()
            parentFragmentManager.popBackStack()
        }
    }

    private fun setName() {
        val firstname = binding.firstname.text.toString()
        if(firstname!="")
        {
            userViewModel.updateUser(User(firstname = firstname, lastname = binding.lastname.text.toString()))
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_edit_user_name, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_selectName -> {
                setName()
                parentFragmentManager.popBackStack()
                return true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}