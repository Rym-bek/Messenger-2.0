package com.example.messenger.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.adapters.CountryAdapter
import com.example.messenger.database.DatabaseHelper
import com.example.messenger.databinding.CountryCodePageBinding
import com.example.messenger.databinding.EditUserNameBinding
import com.example.messenger.models.Country
import com.example.messenger.viewmodels.UserViewModel
import java.util.*

class CountryCodeFragment: Fragment(), MenuProvider {
    private var _binding: CountryCodePageBinding? = null

    private val binding get() = _binding!!

    private lateinit var countryAdapter: CountryAdapter
    private val countryArrayList: ArrayList<Country> = ArrayList()
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var filteredList: ArrayList<Country> = ArrayList()

    private fun setAdapter()
    {
        countryAdapter = CountryAdapter(countryArrayList)
        viewManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = countryAdapter
            layoutManager = viewManager
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun createDatabase()
    {
        databaseHelper = DatabaseHelper(requireContext())
        databaseHelper.createDataBase()
        databaseHelper.openDataBase()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountryCodePageBinding.inflate(inflater, container, false)
        val view = binding.root

        val menuHost: MenuHost = requireHost() as MenuHost
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        createDatabase()
        countryArrayList.addAll(databaseHelper.getCountriesInfo())
        setAdapter()
        return view
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu)
        val search = menu.findItem(R.id.toolbar_searchView)
        val searchView : SearchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList.clear()
                countryArrayList.forEach{
                    if (newText != null) {
                        if(it.name.lowercase(Locale.getDefault()).contains(newText.lowercase(Locale.getDefault())))
                        {
                            filteredList.add(it)
                        }
                    }
                    countryAdapter.filterList(filteredList)
                }
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