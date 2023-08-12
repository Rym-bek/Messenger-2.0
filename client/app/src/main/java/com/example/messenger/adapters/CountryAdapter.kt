package com.example.messenger.adapters;

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.ItemCountryBinding
import com.example.messenger.models.Country


class CountryAdapter(private var countryArrayList:ArrayList<Country>)
    : RecyclerView.Adapter<CountryAdapter.CountryAdapterViewHolder>(){

    class CountryAdapterViewHolder(var binding:ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryAdapterViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryAdapterViewHolder(binding)
    }

    fun filterList(filterllist: ArrayList<Country>) {
        countryArrayList = filterllist
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CountryAdapterViewHolder, position: Int) {
        val country: Country = countryArrayList[position]
        holder.binding.flag.text=country.flag
        holder.binding.country.text=country.name
        holder.binding.code.text="+".plus(country.code)
        holder.itemView.setOnClickListener{
            val bundle = bundleOf(
                "flag" to country.flag,
                "name" to country.name,
                "code" to country.code
            )
            val previousNavController = it.findNavController()
            previousNavController.previousBackStackEntry?.savedStateHandle?.set("bundle", bundle)
            previousNavController.popBackStack()
        }
    }

    override fun getItemCount(): Int {
        return countryArrayList.size
    }
}
