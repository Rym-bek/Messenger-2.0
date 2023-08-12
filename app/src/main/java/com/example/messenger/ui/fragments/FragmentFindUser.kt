package com.example.messenger.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.adapters.FindUserAdapter
import com.example.messenger.databinding.FindUserBinding
import com.example.messenger.models.UserDialog
import com.example.messenger.utils.BaseResponse
import com.example.messenger.utils.UserManager
import com.example.messenger.viewmodels.ViewModelGetUsers
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class FragmentFindUser: Fragment(), MenuProvider {
    private var _binding: FindUserBinding? = null

    private val binding get() = _binding!!

    private val viewModelGetUsers: ViewModelGetUsers by viewModels()

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var findUserAdapter: FindUserAdapter

    private val myUid = UserManager.user?.uid
    private var users: ArrayList<UserDialog> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FindUserBinding.inflate(inflater, container, false)
        val view = binding.root

        val menuHost: MenuHost = binding.toolbar
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        observers()

        setAdapter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }


    private fun setAdapter()
    {
        findUserAdapter = FindUserAdapter(requireContext(), users)
        viewManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            adapter = findUserAdapter
            layoutManager = viewManager
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun initToolbar()
    {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }

    private fun observers()
    {
        viewModelGetUsers.users.observe(viewLifecycleOwner)
        {
            when (it) {
                is BaseResponse.Loading -> {

                }
                is BaseResponse.Success -> {
                    users= it.data!!
                    findUserAdapter.filterList(users)
                }
                is BaseResponse.Error -> {
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.toolbar_searchView)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query!="") {
                    viewModelGetUsers.loadUsers(query.lowercase(), myUid!!)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}