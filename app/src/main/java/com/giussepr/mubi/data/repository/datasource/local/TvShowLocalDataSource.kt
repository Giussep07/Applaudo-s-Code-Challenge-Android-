/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository.datasource.local

import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import kotlinx.coroutines.flow.Flow

interface TvShowLocalDataSource {
  fun getAllFavoriteTvShows(): Flow<List<FavoriteTvShowEntity>>
  suspend fun saveFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity)
  suspend fun getLocalFavoriteShowById(tvShowId: Int): FavoriteTvShowEntity?
  suspend fun removeFavoriteTvShow(tvShowId: Int)
}
