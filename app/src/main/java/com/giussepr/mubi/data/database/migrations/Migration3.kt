/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration3 : Migration(3, 4) {

  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("CREATE TABLE IF NOT EXISTS `top_rated_tv_show` (`id` INTEGER NOT NULL, `backdrop_path` TEXT NOT NULL, `first_air_date` TEXT NOT NULL, `name` TEXT NOT NULL, `original_language` TEXT NOT NULL, `original_name` TEXT NOT NULL, `overview` TEXT NOT NULL, `popularity` REAL NOT NULL, `poster_path` TEXT NOT NULL, `vote_average` REAL NOT NULL, `vote_count` INTEGER NOT NULL, `image_url` TEXT NOT NULL, `detail_image_url` TEXT NOT NULL, PRIMARY KEY(`id`))")
  }
}
