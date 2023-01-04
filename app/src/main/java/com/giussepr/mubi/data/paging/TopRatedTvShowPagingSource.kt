/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.giussepr.mubi.data.model.ResponseErrorBody
import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSource
import com.giussepr.mubi.domain.error.ApiException
import com.giussepr.mubi.domain.error.DomainException
import com.giussepr.mubi.domain.model.TvShow
import com.google.gson.Gson
import javax.inject.Inject


class TopRatedTvShowPagingSource @Inject constructor(
  private val tvShowRemoteDataSource: TvShowRemoteDataSource
) : PagingSource<Int, TvShow>() {

  override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
    return try {
      val page = params.key ?: 1
      val response = tvShowRemoteDataSource.getTopRatedTvShows(page = page)

      if (response.isSuccessful) {
        response.body()?.let { tvShowResponseDTO ->
          LoadResult.Page(
            data = tvShowResponseDTO.results.map { it.toDomainTvShow() },
            prevKey = null,
            nextKey = if (tvShowResponseDTO.results.isEmpty()) null else page.plus(1)
          )
        } ?: LoadResult.Error(Exception(response.message()))
      } else {
        response.errorBody()?.let { responseBody ->
          val gson = Gson()
          val responseErrorBody =
            gson.fromJson(responseBody.string(), ResponseErrorBody::class.java)

          LoadResult.Error(
            ApiException(
              responseErrorBody.statusCode,
              responseErrorBody.statusMessage
            )
          )
        } ?: LoadResult.Error(DomainException(response.message()))
      }
    } catch (e: Exception) {
      LoadResult.Error(DomainException(e.message ?: "Something went wrong"))
    }
  }
}
