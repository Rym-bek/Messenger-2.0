package com.example.messenger.adapters

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.messenger.R
import com.example.messenger.databinding.ItemDialogBinding
import com.example.messenger.models.LocationUser
import com.example.messenger.models.UserDialog
import com.example.messenger.ui.fragments.FragmentUserMessagePage
import kotlin.math.roundToInt

class UserLocationsAdapter(private val context: Context, private var locations: ArrayList<LocationUser>) : RecyclerView.Adapter<UserLocationsAdapter.UserLocationsAdapterViewHolder>() {
    class UserLocationsAdapterViewHolder(var binding: ItemDialogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserLocationsAdapterViewHolder {
        val binding = ItemDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserLocationsAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserLocationsAdapterViewHolder, position: Int) {
        val locationUser: LocationUser = locations[position]
        if(locationUser.firstname!=null)
        {
            holder.binding.name.text=locationUser.firstname
        }
        else
        {
            holder.binding.name.text=locationUser.nickname
        }
        holder.binding.text.text= locationUser.formattedDistance+" "+context.resources.getString(R.string.meters)
        holder.binding.time.text=""

        Glide.with(context)
            .load(locationUser.photo_url)
            .signature(ObjectKey(locationUser.photo_url.hashCode()))
            .into(holder.binding.image)

        holder.itemView.setOnClickListener{ view ->
            val bundle = bundleOf(
                "anotherUid" to locationUser.uid,
                "photo_url" to locationUser.photo_url,
                "firstname" to locationUser.firstname,
            )
            view.findNavController().navigate(R.id.action_findLocationsFragment_to_fragmentUserMessagePage,bundle)
        }
    }

    override fun getItemCount(): Int {
        return locations.size
    }
}