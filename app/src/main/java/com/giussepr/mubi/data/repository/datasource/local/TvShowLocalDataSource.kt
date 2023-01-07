/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository.datasource.local

import androidx.paging.PagingSource
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import com.giussepr.mubi.data.database.entity.airingtoday.AiringTodayTvShowEntity
import com.giussepr.mubi.data.database.entity.airingtoday.AiringTodayTvShowRemoteKey
import com.giussepr.mubi.data.database.entity.ontvshow.OnTvShowEntity
import com.giussepr.mubi.data.database.entity.ontvshow.OnTvShowRemoteKey
import com.giussepr.mubi.data.database.entity.populartvshow.PopularTvShowEntity
import com.giussepr.mubi.data.database.entity.populartvshow.PopularTvShowRemoteKey
import com.giussepr.mubi.data.database.entity.topratedtvshow.TopRatedTvShowEntity
import com.giussepr.mubi.data.database.entity.topratedtvshow.TopRatedTvShowRemoteKey
import kotlinx.coroutines.flow.Flow

interface TvShowLocalDataSource {
  fun getAllFavoriteTvShows(): Flow<List<FavoriteTvShowEntity>>
  suspend fun saveFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity)
  suspend fun getLocalFavoriteShowById(tvShowId: Int): FavoriteTvShowEntity?
  suspend fun removeFavoriteTvShow(tvShowId: Int)

  // region Top Rated Tv Show
  suspend fun getRemoteKeyByTvShowId(tvShowId: Int): TopRatedTvShowRemoteKey?
  suspend fun deleteAllTopRatedTvShows()
  suspend fun deleteAllTopRatedTvShowsRemoteKeys()
  suspend fun addAllRemoteKeys(remoteKeys: List<TopRatedTvShowRemoteKey>)
  suspend fun addTopRatedTvShows(tvShowList: List<TopRatedTvShowEntity>)
  fun getTopRatedTvShows(): PagingSource<Int, TopRatedTvShowEntity>

  // endregion Top Rated Tv Show
  // region Popular Tv Show
  suspend fun getPopularTvShowRemoteKeyByTvShowId(tvShowId: Int): PopularTvShowRemoteKey?
  suspend fun addPopularTvShowAllRemoteKeys(remoteKeys: List<PopularTvShowRemoteKey>)
  suspend fun deleteAllPopularTvShowsRemoteKeys()
  fun getPopularTvShows(): PagingSource<Int, PopularTvShowEntity>
  suspend fun addPopularTvShows(tvShowList: List<PopularTvShowEntity>)
  suspend fun deleteAllPopularTvShows()

  // endregion Popular Tv Show
  // region On Tv Tv Show
  suspend fun getOnTvShowRemoteKeyByTvShowId(tvShowId: Int): OnTvShowRemoteKey?
  suspend fun addOnTvShowAllRemoteKeys(remoteKeys: List<OnTvShowRemoteKey>)
  suspend fun deleteAllOnTvShowsRemoteKeys()
  fun getOnTvShows(): PagingSource<Int, OnTvShowEntity>
  suspend fun addOnTvShows(tvShowList: List<OnTvShowEntity>)
  suspend fun deleteAllOnTvShows()

  // endregion On TV Tv Show
  // region AiringToday Tv Show
  suspend fun getAiringTodayTvShowRemoteKeyByTvShowId(tvShowId: Int): AiringTodayTvShowRemoteKey?
  suspend fun addAiringTodayTvShowAllRemoteKeys(remoteKeys: List<AiringTodayTvShowRemoteKey>)
  suspend fun deleteAllAiringTodayTvShowsRemoteKeys()
  fun getAiringTodayTvShows(): PagingSource<Int, AiringTodayTvShowEntity>
  suspend fun addAiringTodayTvShows(tvShowList: List<AiringTodayTvShowEntity>)
  suspend fun deleteAllAiringTodayTvShows()

  // endregion Airing Today Tv Show

}
