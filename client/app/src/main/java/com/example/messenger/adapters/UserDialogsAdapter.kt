package com.example.messenger.adapters

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.messenger.R
import com.example.messenger.databinding.ItemDialogBinding
import com.example.messenger.models.UserDialog
import com.example.messenger.ui.fragments.FragmentUserMessagePage

class UserDialogsAdapter(private val context: Context, private var dailogs: ArrayList<UserDialog>) : RecyclerView.Adapter<UserDialogsAdapter.UserDialogsAdapterViewHolder>() {
    class UserDialogsAdapterViewHolder(var binding: ItemDialogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserDialogsAdapterViewHolder {
        val binding = ItemDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserDialogsAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dailogs.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: UserDialogsAdapterViewHolder, position: Int) {
        val userDialog: UserDialog = dailogs[position]
        if(userDialog.firstname!=null)
        {
            holder.binding.name.text=userDialog.firstname
        }
        else
        {
            holder.binding.name.text=userDialog.nickname
        }
        holder.binding.text.text=userDialog.message
        holder.binding.time.text= userDialog.formattedTime

        Glide.with(context)
            .load(userDialog.photo_url)
            .signature(ObjectKey(userDialog.photo_url.hashCode()))
            .into(holder.binding.image)

        if(userDialog.online == true)
        {
            holder.binding.online.visibility=View.VISIBLE
        }
        else
        {
            holder.binding.online.visibility=View.INVISIBLE
        }


        holder.itemView.setOnClickListener{ view ->
            Log.d("suka_blya", userDialog.roomUid.toString())
            val bundle = bundleOf(
                "roomUid" to userDialog.roomUid,
                "anotherUid" to userDialog.anotherUid,
                "photo_url" to userDialog.photo_url,
                "firstname" to userDialog.firstname,
                "online" to userDialog.online,
            )
            view.findNavController().navigate(R.id.action_fragmentHomePage_to_fragmentUserMessagePage,bundle)
        }
    }
}