package com.gimnastiar.core.data.source.remote

import android.util.Log
import com.gimnastiar.core.data.source.remote.network.ApiResponse
import com.gimnastiar.core.data.source.remote.network.ApiService
import com.gimnastiar.core.data.source.remote.response.DetailMovieResponse
import com.gimnastiar.core.data.source.remote.response.ListMovieResponse
import com.gimnastiar.core.data.source.remote.response.ResultMovie
import com.gimnastiar.core.utils.ConstantValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getListMovie() : Flow<ApiResponse<List<ResultMovie>>> {
        return flow {
            try {
                val response = apiService.getListPopular()
                val result = response.results
                if (result.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(id: Int) : Flow<ApiResponse<DetailMovieResponse>> {
        return flow {
            try {
                val response = apiService.getDetail(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}