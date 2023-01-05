/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1 : Migration(1, 2) {

  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("CREATE TABLE IF NOT EXISTS `tv_show_remote_key` (`tv_show_id` INTEGER NOT NULL, `nextKey` INTEGER, PRIMARY KEY(`tv_show_id`))")
  }
}
