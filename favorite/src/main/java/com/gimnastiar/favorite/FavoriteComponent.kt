package com.gimnastiar.favorite

import android.content.Context
import com.gimnastiar.core.domain.usecase.MovieUseCase
import com.gimnastiar.rmovie.di.FavoriteModuleDependencies
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    @Component.Builder
    interface Builder {

        fun context(@BindsInstance context: Context): Builder

        fun appDependencies(dependencies: FavoriteModuleDependencies): Builder

        fun build(): FavoriteComponent
    }

    fun inject(activity: FavoriteFragment)
}