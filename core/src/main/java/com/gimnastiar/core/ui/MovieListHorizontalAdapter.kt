package com.gimnastiar.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gimnastiar.core.databinding.ItemMovieHomeBinding
import com.gimnastiar.core.databinding.ItemMovieHorizontalBinding
import com.gimnastiar.core.domain.model.Movie

class MovieListHorizontalAdapter :
    ListAdapter<Movie, MovieListHorizontalAdapter.ListViewHolder>(MovieListDiffCallback()) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(var binding: ItemMovieHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.backdropPath)
                    .into(imgMovie)
                tvJudul.text = data.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemMovieHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}
