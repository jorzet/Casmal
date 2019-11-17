/*
 * Copyright [2019] [Jorge Zepeda Tinoco]
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

package com.jorzet.casmal.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.jorzet.casmal.R

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 12/08/19.
 */


/**
 * Constants
 */
private const val TIME_DELAY: Long = 2000

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            goMainActivity()
        }, TIME_DELAY)
    }

    /**
     * This method creates ans intent to show [MainActivity]
     */
    private fun goMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}