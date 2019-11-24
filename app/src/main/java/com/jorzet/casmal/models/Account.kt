package com.jorzet.casmal.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jorzet.casmal.room.RoomConstants

@Entity(tableName = RoomConstants.tbl_accounts)
data class Account(
    @PrimaryKey
    @ColumnInfo(name = RoomConstants.col_row_id)
    var rowId: Int = 1,

    @ColumnInfo(name = RoomConstants.col_user_id) //ProviderId by @provider
    var userId: String,

    @ColumnInfo(name = RoomConstants.col_user_name) //Logged username
    var userName: String,

    @ColumnInfo(name = RoomConstants.col_user_email) //Logged email
    var userEmail: String,

    @ColumnInfo(name = RoomConstants.col_image) //ImageUrl or Base64
    val image: String,

    @ColumnInfo(name = RoomConstants.col_provider) //Facebook, Google or Email
    var provider: String
)