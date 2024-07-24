package com.gimnastiar.rmovie.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gimnastiar.core.data.Resource
import com.gimnastiar.core.domain.model.DetailMovie
import com.gimnastiar.core.domain.model.Movie
import com.gimnastiar.core.ui.MovieListHorizontalAdapter
import com.gimnastiar.core.utils.ConstantValue.MOVIE_ID
import com.gimnastiar.core.utils.DataMapper
import com.gimnastiar.rmovie.R
import com.gimnastiar.rmovie.databinding.FragmentDetailMovieBinding
import com.gimnastiar.rmovie.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailMovieFragment : Fragment(R.layout.fragment_detail_movie) {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailMovieVideoModel by viewModels()

    private var isFavoriteMovie by Delegates.notNull<Boolean>()
    private var inputMovie: Movie? = null

    private var idMovie: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailMovieBinding.bind(view)

        val args = arguments?.getInt(MOVIE_ID)
        if (args != null) {
            idMovie = args
            viewModel.setId(idMovie!!)
            observeData()
        } else {
            Toast.makeText(requireContext(), "Data tidak tersedia", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        setupAdapter()

    }

    private fun setupAdapter() = with(binding) {
        val adapterHorizontal = MovieListHorizontalAdapter()
        adapterHorizontal.setOnItemClickCallback(object: MovieListHorizontalAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                viewModel.setId(data.id)
            }
        })
        rvMovieHorizontal.adapter = adapterHorizontal
        rvMovieHorizontal.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> {
                        isLoading(true)
                        Log.i("HOME FRAGMENT", "LOADING.. FRAGMENT HOME")
                    }
                    is Resource.Success -> {
                        isLoading(false)
                        movie.data.let {
                            adapterHorizontal.submitList(it)
                        }
                    }

                    is Resource.Error -> {
                        isLoading(true)
                        Log.i("HOME FRAGMENT", "ERROR FRAGMENT HOME")
                    }
                    else -> {}
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) = with(binding) {
        if(loading) {
            layoutLoading.visibility = View.VISIBLE
            layoutContent.visibility = View.GONE
        } else {
            layoutLoading.visibility = View.GONE
            layoutContent.visibility = View.VISIBLE
        }
    }

    private fun setFavoriteMovie(movie: DetailMovie) {
        viewModel.moviesFavorite.observe(viewLifecycleOwner) { data ->
            if (data.isEmpty()) {
                isFavoriteMovie = false
                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_border_24))
                Log.i("DETAIL MOVIE", "MOVIE KOSONG")
            } else {
                if (data.any {it.id == movie.id}) {
                    isFavoriteMovie = true
                    binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24))
                    Log.i("DETAIL MOVIE", "MOVIE FAVORITE")
                } else {
                    isFavoriteMovie = false
                    binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_border_24))
                    Log.i("DETAIL MOVIE", "MOVIE NOT FAVORITE")
                }
            }
        }
    }

    private fun setFavorite() {
        if (isFavoriteMovie) {
            inputMovie?.let {
                viewModel.deleteMovie(it)
            }
        } else {
            inputMovie?.let {
                viewModel.addMovie(it)

            }
        }
    }

    private fun observeData() {
        viewModel.id.observe(viewLifecycleOwner) { id ->
            viewModel.getDetailMovie(id).observe(viewLifecycleOwner) { movie ->
                if (movie.data != null) {
                    when (movie) {
                        is Resource.Loading -> {
                            isLoading(true)
                            Log.i("DETAIL FRAGMENT", "LOADING.. FRAGMENT HOME")
                        }
                        is Resource.Success -> {
                            isLoading(false)
                            inputData(movie.data!!)
                            setFavoriteMovie(movie.data!!)
                        }

                        is Resource.Error -> {
                            Log.i("DETAIL FRAGMENT", "ERROR FRAGMENT HOME")
                        }
                    }
                }
            }
        }
    }

    private fun inputData(data: DetailMovie) = with(binding) {
        inputMovie = data.let { DataMapper.mapDetailToMovie(it) }
        Glide.with(requireContext())
            .load(data.backdropPath)
            .into(imgBackdrop)
        Glide.with(requireContext())
            .load(data.posterPath)
            .into(imgPoster)
        tvTitle.text = data.title

        if (data.adult) {
            tvAdult.text = getString(R.string.adult)
            containerSAdult.background = ContextCompat.getDrawable(requireContext().applicationContext, R.drawable.shape_adult_container_bg_red)
        } else {
            tvAdult.text = getString(R.string.not_adult)
            containerSAdult.background = ContextCompat.getDrawable(requireContext().applicationContext, R.drawable.shape_adult_container_bg_green)
        }

        tvStatus.text = data.status
        tvVoteAverage.text = data.voteAverage.toString()
        tvVoteCount.text = data.voteCount.toString()
        tvOverview.text = data.overview

        btnFavorite.setOnClickListener {
            setFavorite()
        }

        iconButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}