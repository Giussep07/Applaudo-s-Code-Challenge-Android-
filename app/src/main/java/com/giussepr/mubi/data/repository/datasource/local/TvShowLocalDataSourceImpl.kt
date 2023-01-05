/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository.datasource.local

import androidx.paging.PagingSource
import com.giussepr.mubi.data.database.dao.FavoriteTvShowDao
import com.giussepr.mubi.data.database.dao.TopRatedTvShowDao
import com.giussepr.mubi.data.database.dao.TopRatedTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import com.giussepr.mubi.data.database.entity.TopRatedTvShowEntity
import com.giussepr.mubi.data.database.entity.TopRatedTvShowRemoteKey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowLocalDataSourceImpl @Inject constructor(
  private val favoriteTvShowDao: FavoriteTvShowDao,
  private val topRatedTvShowDao: TopRatedTvShowDao,
  private val topRatedTVShowRemoteKeyDao: TopRatedTvShowRemoteKeyDao
) : TvShowLocalDataSource {

  override fun getAllFavoriteTvShows(): Flow<List<FavoriteTvShowEntity>> {
    return favoriteTvShowDao.getAll()
  }

  override suspend fun saveFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) {
    favoriteTvShowDao.insert(favoriteTvShow)
  }

  override suspend fun getLocalFavoriteShowById(tvShowId: Int): FavoriteTvShowEntity? {
    return favoriteTvShowDao.getFavoriteTvShowById(tvShowId)
  }

  override suspend fun removeFavoriteTvShow(tvShowId: Int) {
    favoriteTvShowDao.deleteById(tvShowId)
  }

  override suspend fun getRemoteKeyByTvShowId(tvShowId: Int): TopRatedTvShowRemoteKey? {
    return topRatedTVShowRemoteKeyDao.getRemoteKeyByTvShowId(tvShowId)
  }

  override suspend fun deleteAllTopRatedTvShows() {
    topRatedTvShowDao.deleteAll()
  }

  override suspend fun deleteAllTopRatedTvShowsRemoteKeys() {
    topRatedTVShowRemoteKeyDao.deleteAllRemoteKeys()
  }

  override suspend fun addAllRemoteKeys(remoteKeys: List<TopRatedTvShowRemoteKey>) {
    topRatedTVShowRemoteKeyDao.addAllRemoteKeys(remoteKeys)
  }

  override suspend fun addTopRatedTvShows(tvShowList: List<TopRatedTvShowEntity>) {
    topRatedTvShowDao.insertAll(tvShowList)
  }

  override fun getTopRatedTvShows(): PagingSource<Int, TopRatedTvShowEntity> {
    return topRatedTvShowDao.getAll()
  }
}
