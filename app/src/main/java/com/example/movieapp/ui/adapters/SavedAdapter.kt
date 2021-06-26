package com.example.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.Constants
import com.example.movieapp.data.Result
import com.example.movieapp.databinding.ItemMovieUpcomingBinding

class SavedAdapter : RecyclerView.Adapter<SavedAdapter.SavedViewHolder>() {
    var onItemClickCallback: (Result) -> Unit = { /* no-op */ }

    var items: List<Result> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SavedViewHolder(val binding: ItemMovieUpcomingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.tvTitle.text = item.title
            binding.tvRating.text = item.vote_average.toString()
            if (item.genre_ids.isNotEmpty()) {
                binding.tvGenre.text = Constants.GENRE_MAP[item.genre_ids[0]]
            }
            Glide.with(binding.root).load("https://image.tmdb.org/t/p/w500" + item.poster_path)
                .into(binding.ivPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val viewHolder = SavedViewHolder(
            ItemMovieUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        viewHolder.itemView.setOnClickListener {
            onItemClickCallback(items[viewHolder.bindingAdapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}