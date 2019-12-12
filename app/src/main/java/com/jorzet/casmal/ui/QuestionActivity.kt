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

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jorzet.casmal.R
import com.jorzet.casmal.fragments.question.MultipleQuestionFragment

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class QuestionActivity: AppCompatActivity() {
    /**
     * Tags
     */
    companion object {
        const val QUESTION_LIST: String = "question_list"
    }

    /**
     * Models
     */
    public lateinit var mNextQuestion: View
    public var mQuestions: List<String>? = arrayListOf()
    public var mCurrectQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        mNextQuestion = findViewById(R.id.btn_next_question)

        mQuestions = intent.extras!!.getStringArrayList(QUESTION_LIST)

        val currentFragment = MultipleQuestionFragment()
        val manager = getSupportFragmentManager();
        val transaction = manager.beginTransaction();
        transaction.replace(R.id.question_fragment_container, currentFragment);
        transaction.commitAllowingStateLoss()
    }

}