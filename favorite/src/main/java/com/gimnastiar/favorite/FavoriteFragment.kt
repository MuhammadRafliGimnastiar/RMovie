package com.gimnastiar.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gimnastiar.core.domain.model.Movie
import com.gimnastiar.core.ui.MovieListSearchAdapter
import com.gimnastiar.core.utils.ConstantValue.MOVIE_ID
import com.gimnastiar.favorite.databinding.FragmentFavoriteBinding
import com.gimnastiar.rmovie.R
import com.gimnastiar.rmovie.di.FavoriteModuleDependencies
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels() {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        buttonClick()
    }

    private fun buttonClick() = with(binding) {
        topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() = with(binding) {

        val adapterSearch = MovieListSearchAdapter()
        adapterSearch.setOnItemClickCallback(object: MovieListSearchAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                moveToDetail(data)
            }
        })

        rvFavorite.adapter = adapterSearch
        rvFavorite.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.favorite.observe(viewLifecycleOwner) { data ->
            if(data.isEmpty()) {
                emptyHandler(true)
            } else {
                emptyHandler(false)
                adapterSearch.submitList(data)
            }
        }
    }

    private fun emptyHandler(isEmpty: Boolean) = with(binding) {
        tvMovieEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun moveToDetail(data: Movie) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, data.id)
        findNavController().navigate(R.id.action_favoriteFragment_to_detailMovieFragment, bundle)
        Log.i("HOME FRAGMENT", "ACTION CLICK MOVE TO PAGE")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}