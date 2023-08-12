package com.example.messenger.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.messenger.R
import com.example.messenger.databinding.EditUserAboutBinding
import com.example.messenger.databinding.EditUserNicknameBinding
import com.example.messenger.models.User
import com.example.messenger.viewmodels.UserViewModel

class FragmentEditUserAbout : Fragment(), MenuProvider {
    private var _binding: EditUserAboutBinding? = null

    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditUserAboutBinding.inflate(inflater, container, false)
        val view = binding.root
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fab.setOnClickListener {
            setAbout()
            parentFragmentManager.popBackStack()
        }
    }

    private fun setAbout() {
        val about = binding.about.text.toString()
        if (about != "") {
            userViewModel.updateUser(User(about = about))
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_edit_user_name, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_selectName -> {
                setAbout()
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