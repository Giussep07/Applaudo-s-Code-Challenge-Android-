/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository.datasource.local

import com.giussepr.mubi.data.database.dao.FavoriteTvShowDao
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowLocalDataSourceImpl @Inject constructor(private val favoriteTvShowDao: FavoriteTvShowDao) :
  TvShowLocalDataSource {

  override fun getAllFavoriteTvShows(): Flow<List<FavoriteTvShowEntity>> {
    return favoriteTvShowDao.getAll()
  }
}
