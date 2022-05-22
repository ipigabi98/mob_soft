package com.example.mobsoftapp.di

import com.example.mobsoftapp.network.StoreService
import com.example.mobsoftapp.persistence.ProductDao
import com.example.mobsoftapp.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        disneyService: StoreService,
        posterDao: ProductDao
    ): MainRepository {
        return MainRepository(disneyService, posterDao)
    }
}