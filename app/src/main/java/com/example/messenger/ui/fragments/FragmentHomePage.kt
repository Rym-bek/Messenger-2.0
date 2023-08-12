package com.example.messenger.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.R
import com.example.messenger.adapters.UserDialogsAdapter
import com.example.messenger.databinding.HomePageBinding
import com.example.messenger.databinding.NavigationHeaderBinding
import com.example.messenger.models.Message
import com.example.messenger.models.User
import com.example.messenger.models.UserDialog
import com.example.messenger.utils.BaseResponse
import com.example.messenger.utils.UserManager
import com.example.messenger.viewmodels.*

class FragmentHomePage: Fragment(){
    private var _binding: HomePageBinding? = null
    private val binding get() = _binding!!

    private var _headerBinding: NavigationHeaderBinding? = null
    private val headerBinding get() = _headerBinding!!

    private val tokenViewModel: TokenViewModel by activityViewModels()

    private val userViewModel: UserViewModel by activityViewModels()

    private val viewModelAllDialogs: ViewModelAllDialogs by activityViewModels()

    private val webSocketViewModel: WebSocketViewModel by activityViewModels()

    private val getUserByUidViewModel: GetUserByUidViewModel by activityViewModels()

    private val dialogs: ArrayList<UserDialog> = ArrayList()
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var userDialogsAdapter: UserDialogsAdapter

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var navHostFragment: NavHostFragment

    var userModel: User? = null

    var newMessage: Message? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserData()

        observeDialogs()

        observeMessages()

        observeNewUserData()
    }

    private fun updateDialog(roomUid: String, message: String, time: String): Boolean {
        for (i in 0 until dialogs.size) {
            if (dialogs[i].roomUid == roomUid) {
                dialogs[i].message = message
                dialogs[i].time = time
                userDialogsAdapter.notifyItemChanged(i)
                if(i!=0)
                {
                    dialogs.add(0, dialogs[i])
                    dialogs.removeAt(i+1)
                    userDialogsAdapter.notifyItemMoved(i, 0)
                }
                return true
            }
        }
        return false
    }

    private fun updateDialogOnlineStatus(uid: String,onlineStatus:Boolean)
    {
        for (i in 0 until dialogs.size) {
            if (dialogs[i].anotherUid == uid) {
                dialogs[i].online = onlineStatus
                userDialogsAdapter.notifyItemChanged(i)
                break
            }
        }
    }

    private fun observeNewUserData()
    {
        getUserByUidViewModel.userData.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    val data = it.data
                    data?.message = newMessage?.message
                    data?.time = newMessage?.time
                    data?.roomUid = newMessage?.roomUid
                    if(!dialogs.any { it.roomUid == data?.roomUid })
                    {
                        dialogs.add(0,data!!)
                        userDialogsAdapter.notifyItemInserted(0)
                    }
                }
                is BaseResponse.Error -> {
                }
            }
        }
    }

    private fun observeMessages() {
        webSocketViewModel.message.observe(viewLifecycleOwner) {
            if (it != null) {
                if(!updateDialog(it.roomUid!!,it.message, it.time!!))
                {
                    newMessage=it
                    getUserByUidViewModel.getUserByUid(it.senderUid)
                }
            }
        }

        webSocketViewModel.onlineStatus.observe(viewLifecycleOwner) {
            updateDialogOnlineStatus(it!!.uid,it.online)
        }
    }

    private fun setAdapter()
    {
        userDialogsAdapter = UserDialogsAdapter(requireContext(), dialogs)
        viewManager = LinearLayoutManager(requireContext())
        binding.contentHomePage.recyclerView.apply {
            adapter = userDialogsAdapter
            layoutManager = viewManager
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun observeDialogs()
    {
        viewModelAllDialogs.dialogs.observe(viewLifecycleOwner)
        {
            when (it) {
                is BaseResponse.Loading -> {

                }
                is BaseResponse.Success -> {
                    if(dialogs.size==0)
                    {
                        dialogs.addAll(it.data!!)
                        userDialogsAdapter.notifyItemRangeInserted(0,dialogs.size)
                    }
                }
                is BaseResponse.Error -> {
                }
            }
        }
    }

    private fun observeUserData()
    {
        userViewModel.userData.observe(viewLifecycleOwner)
        {
            userModel = it
            UserManager.setUserModel(userModel)
            headerBinding.viewModel=userViewModel
            if(dialogs.size==0)
            {
                webSocketViewModel.connect(userModel?.uid!!)

                viewModelAllDialogs.loadDialogs(userModel?.uid!!)
            }
        }
    }

    private fun checkLogin()
    {
        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token == null)
            {
                findNavController().navigate(R.id.action_fragmentHomePage_to_welcomePageFragment)
                //findNavController().popBackStack(R.id.action_fragmentHomePage_to_welcomePageFragment, true)
            }
        }
    }

    private fun setupNavigationView()
    {
        navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)

        binding.contentHomePage.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomePageBinding.inflate(inflater, container, false)
        val view = binding.root

        checkLogin()

        setupNavigationView()

        val headerView = binding.navigationView.getHeaderView(0)
        _headerBinding = NavigationHeaderBinding.bind(headerView)

        setAdapter()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _headerBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketViewModel.disconnect()
    }
}