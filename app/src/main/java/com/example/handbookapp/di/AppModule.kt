package com.example.handbookapp.di

import android.content.Context
import androidx.room.Room
import com.example.handbookapp.data.local.LocalDao
import com.example.handbookapp.data.local.LocalDatabase
import com.example.handbookapp.data.repository.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // local
    @Provides @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides @Singleton
    fun provideLocalDao(localDatabase: LocalDatabase) = localDatabase.getDao()

    @Provides @Singleton
    fun provideRoomRepository(localDao: LocalDao) = RoomRepository(localDao)
}