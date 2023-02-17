package ie.setu.watch.main

import android.app.Application
import ie.setu.watch.models.WatchModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val watchs = ArrayList<WatchModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Watch started")
    }
}

