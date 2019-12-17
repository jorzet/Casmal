package com.jorzet.casmal.request

import com.jorzet.casmal.models.Question

class PushQuestionRequest(question: Question): AbstractUpdateDatabase<Question, Boolean>() {

}