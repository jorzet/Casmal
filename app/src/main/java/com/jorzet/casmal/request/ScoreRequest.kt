package com.jorzet.casmal.request

import com.jorzet.casmal.interfaces.RequestListener
import com.jorzet.casmal.managers.RequestManager
import com.jorzet.casmal.models.Score
import com.jorzet.casmal.utils.Endpoints
import org.json.JSONObject

class ScoreRequest(userId: String, listener: RequestListener<String>) : RequestManager(Endpoints.scoreEndpoint.replace("{userId}", userId), listener) {
    companion object {
        fun parse(userId: String, listener: RequestListener<MutableList<Score>>) {
            val request = ScoreRequest(userId, object : RequestListener<String> {
                override fun onStartRequest() {
                    listener.onStartRequest()
                }

                override fun onSuccess(response: String) {
                    val list: MutableList<Score> = ArrayList()

                    try {
                        val jsonObject = JSONObject(response)
                        val jsonArray = jsonObject.getJSONArray("score")
                        val size = jsonArray.length()

                        for (i in 1..size) {
                            val itemObject = jsonArray.getJSONObject(i)

                            val id = "e$i"
                            val examObject = itemObject.getJSONObject(id)
                            val user: Int = examObject.getInt("")
                            val best: Int = examObject.getInt("")
                            val average: Int = examObject.getInt("")

                            val score = Score(id, user, best, average)
                            list.add(score)
                        }

                        listener.onSuccess(list)
                    } catch (ex: Exception) {
                        listener.onError(ex)
                    }
                }

                override fun onError(ex: Exception) {
                    listener.onError(ex)
                }
            })

            request.executeGet()
        }
    }
}