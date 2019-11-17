package com.jorzet.casmal.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jorzet.casmal.room.RoomConstants

@Entity(tableName = RoomConstants.tbl_accounts)
data class Account(
    @PrimaryKey
    @ColumnInfo(name = RoomConstants.col_user_id)
    var userId: String,

    @ColumnInfo(name = RoomConstants.col_user_name)
    var userName: String,

    @ColumnInfo(name = RoomConstants.col_user_email)
    var userEmail: String,

    @ColumnInfo(name = RoomConstants.col_provider)
    var provider: String
)