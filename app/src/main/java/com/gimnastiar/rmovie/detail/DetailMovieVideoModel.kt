package com.gimnastiar.rmovie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gimnastiar.core.data.Resource
import com.gimnastiar.core.domain.model.DetailMovie
import com.gimnastiar.core.domain.model.Movie
import com.gimnastiar.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieVideoModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    val movie = movieUseCase.getPopularMovie().asLiveData()

    private var _id :MutableLiveData<Int> = MutableLiveData()
    val id: LiveData<Int> get() = _id

    fun setId(newId: Int) { _id.value = newId }

    fun getDetailMovie(id: Int): LiveData<Resource<DetailMovie>> =
        movieUseCase.getDetailMovie(id).asLiveData()

    fun addMovie(movie: Movie) = viewModelScope.launch {
        movieUseCase.insertMovie(movie)
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        movieUseCase.deleteMovie(movie)
    }

    val moviesFavorite = movieUseCase.getMovieFavorite().asLiveData()
}