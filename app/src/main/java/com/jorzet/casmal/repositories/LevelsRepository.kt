package com.jorzet.casmal.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.models.Level
import com.jorzet.casmal.utils.Utils

class LevelsRepository {
    private var levels: MutableLiveData<MutableList<Level>> = MutableLiveData()

    companion object {
        val instance: LevelsRepository = LevelsRepository()

        init {
            Utils.print("Instance", "Instance LevelsRepository = " + instance.hashCode())
        }
    }

    init {
        val levels: MutableList<Level> = ArrayList()

        this.levels.value = levels
    }

    fun setLevels(list: MutableList<Level>) {
        val items: MutableList<Level>? = levels.value

        if(items != null) {
            items.clear()
            items.addAll(list)

            this.levels.value = items
        } else {
            Utils.print("Levels are null")
        }
    }

    @NonNull
    fun getLevels() : LiveData<MutableList<Level>> = levels

    fun destroy() {
        levels.value = null
    }
}