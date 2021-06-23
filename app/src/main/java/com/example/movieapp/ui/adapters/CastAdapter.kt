package com.example.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.cast.Cast
import com.example.movieapp.databinding.ItemCastBinding

class CastAdapter : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    var items: List<Cast> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CastViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Cast){
            Glide.with(binding.root).load("https://image.tmdb.org/t/p/w500" + item.profile_path)
                .placeholder(R.drawable.empty_avatar)
                .into(binding.ivCast)
            binding.tvName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}