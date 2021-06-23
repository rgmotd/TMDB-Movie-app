package com.example.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.Result
import com.example.movieapp.databinding.ItemSliderBinding

class SliderAdapter : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    var items: List<Result> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SliderViewHolder(var binding: ItemSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            Glide.with(binding.root).load("https://image.tmdb.org/t/p/original" + item.backdrop_path)
                .into(binding.imageSlider)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}