/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository.datasource.local

import androidx.paging.PagingSource
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
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

}
