package com.jorzet.casmal.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Level
import com.jorzet.casmal.utils.Utils
import java.lang.Exception

class LevelsRepository : ViewModelRepository() {
    private val levels: MutableLiveData<MutableList<Level>> = MutableLiveData()

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
        val items: MutableList<Level> = levels.value ?: return

        Utils.print("Set ${items.size} levels")
        items.forEach {
            Utils.print(it.toString())
        }

        items.clear()
        items.addAll(list)

        this.levels.value = items
    }

    @NonNull
    fun getLevels() : LiveData<MutableList<Level>> = levels

    fun load() {
        FirebaseRequestManager.getInstance().requestLevels(object: FirebaseRequestManager.OnGetLevelsListener {
            override fun onGetLevelsSuccess(levels: MutableList<Level>) {
                setLevels(levels)
            }

            override fun onGetLevelsFail(throwable: Throwable) {
                setException(Exception(throwable))
            }
        })
    }

    fun destroy() {
        levels.value = null
    }
}