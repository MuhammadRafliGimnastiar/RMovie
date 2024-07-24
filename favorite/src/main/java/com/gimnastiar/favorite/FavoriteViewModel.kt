package com.gimnastiar.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gimnastiar.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favorite = movieUseCase.getMovieFavorite().asLiveData()
}