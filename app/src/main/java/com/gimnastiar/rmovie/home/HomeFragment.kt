package com.gimnastiar.rmovie.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gimnastiar.core.data.Resource
import com.gimnastiar.core.domain.model.Movie
import com.gimnastiar.core.ui.MovieListHomeAdapter
import com.gimnastiar.core.ui.MovieListHorizontalAdapter
import com.gimnastiar.core.ui.MovieListSearchAdapter
import com.gimnastiar.core.utils.ConstantValue.MOVIE_ID
import com.gimnastiar.rmovie.R
import com.gimnastiar.rmovie.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var movieList: ArrayList<Movie>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)


        setupData()
        buttonClick()

    }

    private fun buttonClick() = with(binding) {
        searchBar.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.btnFavorite -> {
                    moveToFavorite()
                    true
                }
                else -> false
            }
        }
    }

    private fun moveToFavorite() {
        findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
    }

    private fun setupData() = with(binding) {
        val adapter = MovieListHomeAdapter()
        adapter.setOnItemClickCallback(object: MovieListHomeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                moveToDetail(data)
            }
        })

        val adapterHorizontal = MovieListHorizontalAdapter()
        adapterHorizontal.setOnItemClickCallback(object: MovieListHorizontalAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                moveToDetail(data)
            }
        })

        val adapterSearch = MovieListSearchAdapter()
        adapterSearch.setOnItemClickCallback(object: MovieListSearchAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                moveToDetail(data)
            }
        })

        rvMovie.adapter = adapter
        rvMovie.layoutManager = GridLayoutManager(requireContext(), 2)

        rvMovieHorizontal.adapter = adapterHorizontal
        rvMovieHorizontal.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        rvSearch.adapter = adapterSearch
        rvSearch.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> {
                        isLoading(true)
                        Log.i("HOME FRAGMENT", "LOADING.. FRAGMENT HOME")
                    }
                    is Resource.Success -> {
                        isLoading(false)
                        movie.data.let { dataMovie ->
                            adapter.submitList(dataMovie)
                            adapterHorizontal.submitList(dataMovie)
                            movieList = dataMovie as ArrayList
                        }
                    }

                    is Resource.Error -> {
                        isLoading(true)
                        Log.i("HOME FRAGMENT", "ERROR FRAGMENT HOME")
                    }
                }
            }
        }

        searchView.editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s?.toString()

                val filteredList: ArrayList<Movie> = ArrayList()
                if (text != null)
                for (item in movieList) {
                    if (item.title.toLowerCase().contains(text.toLowerCase())) filteredList.add(item)
                }

                if (filteredList.isEmpty()) {
//                    Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
                } else {
                    adapterSearch.filterList(filteredList)
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

    }

    private fun isLoading(loading: Boolean) = with(binding) {
        if(loading) {
            shimmer.visibility = View.VISIBLE
            layoutContent.visibility = View.GONE
        } else {
            shimmer.visibility = View.GONE
            layoutContent.visibility = View.VISIBLE
        }
    }

    private fun moveToDetail(data: Movie) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, data.id)
        findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment, bundle)
        Log.i("HOME FRAGMENT", "ACTION CLICK MOVE TO PAGE")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}