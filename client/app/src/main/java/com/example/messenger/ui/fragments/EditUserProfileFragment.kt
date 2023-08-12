package com.example.messenger.ui.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.messenger.R
import com.example.messenger.databinding.EditUserProfileBinding
import com.example.messenger.models.User
import com.example.messenger.ui.handlers.PhotoHandlers
import com.example.messenger.utils.*
import com.example.messenger.utils.constants.ConstantsPermissions
import com.example.messenger.viewmodels.UserViewModel
import com.example.messenger.viewmodels.ViewModelProfilePhoto


class EditUserProfileFragment: Fragment(), MenuProvider {


    private var _binding: EditUserProfileBinding? = null
    private val binding get() = _binding!!

    private val  viewModelProfilePhoto : ViewModelProfilePhoto by activityViewModels()

    private val userViewModel: UserViewModel by activityViewModels()

    private var permissionsHelper = PermissionsHelper(this, ConstantsPermissions.PERMISSIONS_STORAGE)

    lateinit var photoHandlers: PhotoHandlers

    var userModel: User? = null

    private var hasPermission:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hasPermission=permissionsHelper.hasPermissions()

        observeUserData()

        observePhoto()

        photoHandlers= PhotoHandlers(this,viewModelProfilePhoto)

        initSettings()
    }

    private fun observePhoto()
    {
        viewModelProfilePhoto.uploadPhotoResponse.observe(this)
        {
            when (it) {
                is BaseResponse.Loading -> {

                }
                is BaseResponse.Success -> {
                    userModel?.photo_url =it.data?.url
                    userViewModel.updateUserData(userModel!!)
                }
                is BaseResponse.Error -> {

                }
            }
        }
    }

    private fun observeUserData()
    {
        userViewModel.userData.observe(this)
        {
            userModel = it
            binding.viewModel=userViewModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.collapsingToolbarLayout.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        //(activity as AppCompatActivity).findViewById<Toolbar>(R.id.toolbarHome).visibility = View.GONE

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initSettings()
    {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .addToBackStack("first frag")
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditUserProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.handlers = photoHandlers
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        menuInflater.inflate(R.menu.menu_edit_user_profile, menu)
    }

    private fun loadFragment(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, fragment)
            .addToBackStack("second frag")
            .commit()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_changeName ->
            {
                loadFragment(FragmentEditUserName())
                return true
            }
            R.id.action_selectPhoto ->
            {
                photoHandlers.onClickPhotoUpdate(binding.fab)
                return true
            }
            R.id.action_exit ->
            {
                findNavController().navigate(R.id.action_editUserProfileFragment_to_dialogEditUserProfileExit)
                return true
            }
            else -> false
        }
    }
}