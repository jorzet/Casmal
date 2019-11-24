package com.jorzet.casmal.managers

import android.app.Application
import android.widget.ImageView
import com.jorzet.casmal.R
import com.jorzet.casmal.utils.Utils
import com.squareup.picasso.Picasso
import java.io.File

@Suppress("unused")
class ImageManager {
    private lateinit var application: Application
    private lateinit var picasso: Picasso
    private val icPlaceholder: Int = R.drawable.ic_casmal
    private val icError: Int = R.drawable.ic_casmal

    companion object {
        @Volatile
        private var instance: ImageManager? = null

        fun getInstance(): ImageManager {
            if (instance != null) {
                return instance as ImageManager
            }

            synchronized(this) {
                val instance = ImageManager()
                this.instance = instance

                return instance
            }
        }

        init {
            Utils.print("Instance", "Instance ImageManager = " + instance.hashCode())
        }
    }

    fun initialize(application: Application) {
        this.application = application

        picasso = Picasso.Builder(application.applicationContext).loggingEnabled(false).indicatorsEnabled(false).build()
    }

    fun setImage(resource: Int, view: ImageView) = picasso.load(resource).error(icError).into(view)

    fun setImage(file: File, view: ImageView?) = picasso.load(file).error(icError).into(view)

    fun setImage(file: File, view: ImageView, x: Int, y: Int) = picasso.load(file).resize(x, y).error(icError).into(view)

    fun setImage(url: String, view: ImageView?) = picasso.load(url).placeholder(icPlaceholder).error(icError).into(view)
}