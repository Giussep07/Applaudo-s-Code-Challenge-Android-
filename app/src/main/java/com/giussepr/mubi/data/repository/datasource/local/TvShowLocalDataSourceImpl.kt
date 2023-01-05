/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository.datasource.local

import androidx.paging.PagingSource
import com.giussepr.mubi.data.database.dao.FavoriteTvShowDao
import com.giussepr.mubi.data.database.dao.TopRatedTvShowDao
import com.giussepr.mubi.data.database.dao.TopRatedTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.dao.ontvshow.OnTvShowDao
import com.giussepr.mubi.data.database.dao.ontvshow.OnTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.dao.populartvshow.PopularTvShowDao
import com.giussepr.mubi.data.database.dao.populartvshow.PopularTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import com.giussepr.mubi.data.database.entity.ontvshow.OnTvShowEntity
import com.giussepr.mubi.data.database.entity.ontvshow.OnTvShowRemoteKey
import com.giussepr.mubi.data.database.entity.populartvshow.PopularTvShowEntity
import com.giussepr.mubi.data.database.entity.populartvshow.PopularTvShowRemoteKey
import com.giussepr.mubi.data.database.entity.topratedtvshow.TopRatedTvShowEntity
import com.giussepr.mubi.data.database.entity.topratedtvshow.TopRatedTvShowRemoteKey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowLocalDataSourceImpl @Inject constructor(
  private val favoriteTvShowDao: FavoriteTvShowDao,
  private val topRatedTvShowDao: TopRatedTvShowDao,
  private val topRatedTVShowRemoteKeyDao: TopRatedTvShowRemoteKeyDao,
  private val popularTvShowDao: PopularTvShowDao,
  private val popularTvShowRemoteKeyDao: PopularTvShowRemoteKeyDao,
  private val onTvShowDao: OnTvShowDao,
  private val onTvShowRemoteKeyDao: OnTvShowRemoteKeyDao
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

  // region Popular Tv Show
  override suspend fun getPopularTvShowRemoteKeyByTvShowId(tvShowId: Int): PopularTvShowRemoteKey? {
    return popularTvShowRemoteKeyDao.getRemoteKeyByTvShowId(tvShowId)
  }

  override suspend fun addPopularTvShowAllRemoteKeys(remoteKeys: List<PopularTvShowRemoteKey>) {
    popularTvShowRemoteKeyDao.addAllRemoteKeys(remoteKeys)
  }

  override suspend fun deleteAllPopularTvShowsRemoteKeys() {
    popularTvShowRemoteKeyDao.deleteAllRemoteKeys()
  }

  override fun getPopularTvShows(): PagingSource<Int, PopularTvShowEntity> {
    return popularTvShowDao.getAll()
  }

  override suspend fun addPopularTvShows(tvShowList: List<PopularTvShowEntity>) {
    popularTvShowDao.insertAll(tvShowList)
  }

  override suspend fun deleteAllPopularTvShows() {
    popularTvShowDao.deleteAll()
  }

  // endregion Popular Tv Show

  // region On Tv Tv Show
  override suspend fun getOnTvShowRemoteKeyByTvShowId(tvShowId: Int): OnTvShowRemoteKey? {
    return onTvShowRemoteKeyDao.getRemoteKeyByTvShowId(tvShowId)
  }

  override suspend fun addOnTvShowAllRemoteKeys(remoteKeys: List<OnTvShowRemoteKey>) {
    onTvShowRemoteKeyDao.addAllRemoteKeys(remoteKeys)
  }

  override suspend fun deleteAllOnTvShowsRemoteKeys() {
    onTvShowRemoteKeyDao.deleteAllRemoteKeys()
  }

  override fun getOnTvShows(): PagingSource<Int, OnTvShowEntity> {
    return onTvShowDao.getAll()
  }

  override suspend fun addOnTvShows(tvShowList: List<OnTvShowEntity>) {
    onTvShowDao.insertAll(tvShowList)
  }

  override suspend fun deleteAllOnTvShows() {
    onTvShowDao.deleteAll()
  }

  // endregion On Tv Tv Show
}
