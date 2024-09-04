package com.example.lokalassignmentapp.di

import android.content.Context
import androidx.room.Room
import com.example.lokalassignmentapp.Utils.GsonParser
import com.example.lokalassignmentapp.api.NetworkService
import com.example.lokalassignmentapp.local.BookMarkDB
import com.example.lokalassignmentapp.local.Convertors
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://testapi.getlokalapp.com/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NetworkService = retrofit.create(NetworkService::class.java)

    @Singleton
    @Provides
    fun provideBookMarkDB(@ApplicationContext context: Context): BookMarkDB {
        return Room
            .databaseBuilder(context, BookMarkDB::class.java, "db")
            .addTypeConverter(Convertors(GsonParser(Gson())))
            .build()
    }

    @Singleton
    @Provides
    fun provideBookMarkDao(bookMarkDB: BookMarkDB) = bookMarkDB.dao

}