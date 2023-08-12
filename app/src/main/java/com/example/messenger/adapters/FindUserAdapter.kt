package com.example.messenger.adapters;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.messenger.R
import com.example.messenger.databinding.ItemUserBinding
import com.example.messenger.models.UserDialog
import com.example.messenger.ui.fragments.FragmentUserMessagePage

class FindUserAdapter(private val context:Context, private var users: java.util.ArrayList<UserDialog>)
    : RecyclerView.Adapter<FindUserAdapter.FindUserAdapterViewHolder>() {

    class FindUserAdapterViewHolder(var binding:ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindUserAdapterViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FindUserAdapterViewHolder(binding)
    }

    fun filterList(filterllist: ArrayList<UserDialog>) {
        users = filterllist
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FindUserAdapterViewHolder, position: Int) {
        val userDialog: UserDialog = users[position]
        if(userDialog.firstname!=null)
        {
            holder.binding.name.text=userDialog.firstname+" "+userDialog.lastname
        }
        else
        {
            holder.binding.name.text=null
        }
        holder.binding.nickName.text=userDialog.nickname

        Glide.with(context)
            .load(userDialog.photo_url)
            .signature(ObjectKey(userDialog.photo_url.hashCode()))
            .into(holder.binding.image)

        holder.itemView.setOnClickListener{ view ->
            val bundle = bundleOf(
                "roomUid" to userDialog.roomUid,
                "anotherUid" to userDialog.anotherUid,
                "photo_url" to userDialog.photo_url,
                "firstname" to userDialog.firstname,
            )
            view.findNavController().navigate(R.id.action_fragmentFindUser_to_fragmentUserMessagePage,bundle)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}
