/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.di

import com.giussepr.mubi.data.api.TmdbApi
import com.giussepr.mubi.presentation.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
@InstallIn(ViewModelComponent::class)
object ApiModule {

  @Provides
  @Named(Constants.BASE_URL)
  fun provideBaseUrl(): String = "https://api.themoviedb.org/3/"

  @Provides
  @Named(Constants.API_KEY)
  fun provideApiKey(): String = "cc2038ba0eaa048db605315fc938c4cd"

  @Provides
  fun provideRetrofit(@Named(Constants.BASE_URL) baseUrl: String): TmdbApi {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val client: OkHttpClient = OkHttpClient.Builder()
      .addInterceptor(logging)
      .build()

    val retrofit = Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()

    return retrofit.create(TmdbApi::class.java)
  }
}
