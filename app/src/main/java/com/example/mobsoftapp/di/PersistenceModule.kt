package com.example.mobsoftapp.di

import android.app.Application
import androidx.room.Room
import com.example.mobsoftapp.R
import com.example.mobsoftapp.persistence.AppDatabase
import com.example.mobsoftapp.persistence.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                application.getString(R.string.database)
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePosterDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }
}