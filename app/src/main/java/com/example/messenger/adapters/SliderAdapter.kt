package com.example.messenger.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.ItemSliderBinding
import com.example.messenger.models.Slide

class SliderAdapter(private val context: Context, private val sliderDataArrayList: ArrayList<Slide>)
    : RecyclerView.Adapter<SliderAdapter.SliderAdapterViewHolder>() {

    class SliderAdapterViewHolder(var binding: ItemSliderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapterViewHolder {
        val binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderAdapterViewHolder, position: Int) {
        val slide: Slide = sliderDataArrayList[position]
        holder.binding.imageViewSlider.setImageResource(context.resources.getIdentifier(
            "ic_"+slide.image,
            "drawable",
            context.packageName
        ))
        holder.binding.textViewSliderTitle.text=slide.title
        holder.binding.textViewSliderDescription.text=slide.text
    }

    override fun getItemCount(): Int {
        return sliderDataArrayList.size
    }
}

