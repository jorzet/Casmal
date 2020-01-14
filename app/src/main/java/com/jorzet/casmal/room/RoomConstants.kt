/*
 * Copyright [2020] [Jorge Zepeda Tinoco]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jorzet.casmal.room

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class RoomConstants {
    companion object {
        const val databaseName = "DrCasmal"
        const val databaseVersion = 1
        const val tbl_accounts = "tbl_accounts"
        const val col_row_id = "col_row_id"
        const val col_user_name = "col_user_name"
        const val col_user_id = "col_user_id"
        const val col_user_email = "col_user_email"
        const val col_image = "col_image"
        const val col_provider = "col_provider" //Google, Facebook, Email
    }
}