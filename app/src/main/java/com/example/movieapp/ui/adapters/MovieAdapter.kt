package com.example.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.Constants.GENRE_MAP
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.data.Result
import com.example.movieapp.databinding.ItemMovieUpcomingBinding

class MovieAdapter(
    val layoutState: Boolean
) : PagingDataAdapter<Result, RecyclerView.ViewHolder>(differCallback) {
    var onItemClickCallback: (Result) -> Unit = { /* no-op */ }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MovieDiscoverViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.tvTitle.text = item.title
            binding.tvTime.text = item.release_date
            Glide.with(binding.root).load("https://image.tmdb.org/t/p/w500" + item.poster_path)
                .into(binding.ivPhoto)
        }
    }

    class MovieUpcomingViewHolder(val binding: ItemMovieUpcomingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.tvTitle.text = item.title
            binding.tvRating.text = item.vote_average.toString()
            if (item.genre_ids.isNotEmpty()) {
                binding.tvGenre.text = GENRE_MAP[item.genre_ids[0]]
            }
            Glide.with(binding.root).load("https://image.tmdb.org/t/p/w500" + item.poster_path)
                .into(binding.ivPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = when (layoutState) {
            true -> MovieDiscoverViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            false -> MovieUpcomingViewHolder(
                ItemMovieUpcomingBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
        viewHolder.itemView.setOnClickListener {
            getItem(viewHolder.bindingAdapterPosition)?.let { onItemClickCallback(it) }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        when (holder) {
            is MovieUpcomingViewHolder -> movie?.let { holder.bind(it) }
            is MovieDiscoverViewHolder -> movie?.let { holder.bind(it) }
        }
    }
}

