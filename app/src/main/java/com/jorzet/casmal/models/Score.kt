package com.jorzet.casmal.models

data class Score(
    val examId: String,
    val user: Int,
    val best: Int,
    val average: Int
)