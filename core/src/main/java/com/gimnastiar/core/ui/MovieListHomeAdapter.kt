package com.gimnastiar.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gimnastiar.core.databinding.ItemMovieHomeBinding
import com.gimnastiar.core.domain.model.Movie

class MovieListHomeAdapter :
    ListAdapter<Movie, MovieListHomeAdapter.ListViewHolder>(MovieListDiffCallback()) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(var binding: ItemMovieHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.posterPath)
                    .into(imgMovie)
                tvJudul.text = data.title
                tvDeskripsi.text = data.overview
                tvAverage.text = data.voteAverage.toString().plus(" / 10")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemMovieHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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