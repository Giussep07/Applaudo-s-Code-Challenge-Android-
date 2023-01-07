/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.giussepr.mubi.data.database.MubiDatabase
import com.giussepr.mubi.data.database.entity.airingtoday.AiringTodayTvShowEntity
import com.giussepr.mubi.data.database.entity.airingtoday.AiringTodayTvShowRemoteKey
import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSource
import com.giussepr.mubi.data.repository.datasource.local.TvShowLocalDataSource

@ExperimentalPagingApi
class AiringTodayTvShowRemoteMediator(
  private val tvShowRemoteDataSource: TvShowRemoteDataSource,
  private val tvShowLocalDataSource: TvShowLocalDataSource,
  private val mubiDatabase: MubiDatabase
) : RemoteMediator<Int, AiringTodayTvShowEntity>() {

  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, AiringTodayTvShowEntity>
  ): MediatorResult {
    return try {
      val currentPage = when (loadType) {
        // This will triggered the first time to make the request to server
        LoadType.REFRESH -> {
          val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
          remoteKeys?.nextKey?.minus(1) ?: 1
        }
        // Load the start of the paging data
        LoadType.PREPEND -> {
          val remoteKeys = getRemoteKeyForFirstItem(state)
          val prevPage = remoteKeys?.prevKey
            ?: return MediatorResult.Success(
              endOfPaginationReached = remoteKeys != null
            )
          prevPage
        }
        // Load at the end of the paging data
        LoadType.APPEND -> {
          val remoteKeys = getRemoteKeyForLastItem(state)
          val nextPage = remoteKeys?.nextKey
            ?: return MediatorResult.Success(
              endOfPaginationReached = remoteKeys != null
            )
          nextPage
        }
      }

      val response = tvShowRemoteDataSource.getAiringTodayTvShows(page = currentPage)
      if (response.isSuccessful) {
        response.body()?.let { tvShowResponseDTO ->
          val tvShowList = tvShowResponseDTO.results.map { it.toDataAiringTodayTvShowEntity() }
          val endOfPaginationReached = tvShowList.isEmpty()

          val prevPage = if (currentPage == 1) null else currentPage - 1
          val nextPage = if (endOfPaginationReached) null else currentPage + 1

          mubiDatabase.withTransaction {
            if (loadType == LoadType.REFRESH) {
              tvShowLocalDataSource.deleteAllAiringTodayTvShows()
              tvShowLocalDataSource.deleteAllAiringTodayTvShowsRemoteKeys()
            }
            val keys = tvShowList.map { tvShow ->
              AiringTodayTvShowRemoteKey(
                tvShowId = tvShow.tvShowId,
                nextKey = nextPage,
                prevKey = prevPage
              )
            }

            tvShowLocalDataSource.addAiringTodayTvShowAllRemoteKeys(remoteKeys = keys.sortedBy { it.nextKey })
            tvShowLocalDataSource.addAiringTodayTvShows(tvShowList)
          }
          MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } ?: MediatorResult.Error(Exception(response.message()))
      } else {
        MediatorResult.Error(Exception(response.message()))
      }

    } catch (e: Exception) {
      MediatorResult.Error(e)
    }
  }

  private suspend fun getRemoteKeyClosestToCurrentPosition(
    state: PagingState<Int, AiringTodayTvShowEntity>
  ): AiringTodayTvShowRemoteKey? {
    return state.anchorPosition?.let { position ->
      state.closestItemToPosition(position)?.id?.let { id ->
        tvShowLocalDataSource.getAiringTodayTvShowRemoteKeyByTvShowId(tvShowId = id)
      }
    }
  }

  private suspend fun getRemoteKeyForFirstItem(
    state: PagingState<Int, AiringTodayTvShowEntity>
  ): AiringTodayTvShowRemoteKey? {
    return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
      ?.let { tvShow ->
        tvShowLocalDataSource.getAiringTodayTvShowRemoteKeyByTvShowId(tvShowId = tvShow.tvShowId)
      }
  }

  private suspend fun getRemoteKeyForLastItem(
    state: PagingState<Int, AiringTodayTvShowEntity>
  ): AiringTodayTvShowRemoteKey? {
    return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
      ?.let { tvShow ->
        tvShowLocalDataSource.getAiringTodayTvShowRemoteKeyByTvShowId(tvShowId = tvShow.tvShowId)
      }
  }
}
