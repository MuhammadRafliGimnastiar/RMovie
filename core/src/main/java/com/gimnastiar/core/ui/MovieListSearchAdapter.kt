package com.gimnastiar.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gimnastiar.core.databinding.ItemMovieSearchBinding
import com.gimnastiar.core.domain.model.Movie

class MovieListSearchAdapter :
    ListAdapter<Movie, MovieListSearchAdapter.ListViewHolder>(MovieListDiffCallback()) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(var binding: ItemMovieSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.posterPath)
                    .into(imgPoster)
                tvTitle.text = data.title
                tvOverview.text = data.overview
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    fun filterList(filterlist: ArrayList<Movie>) {
        this.submitList(filterlist)
        notifyDataSetChanged()
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
