package ie.setu.watch.main

import android.app.Application
import android.widget.Switch
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.setu.watch.models.WatchJSONStore
import ie.setu.watch.models.WatchMemStore
import ie.setu.watch.models.WatchStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var watchs: WatchStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
       // watchs = WatchMemStore()
        watchs = WatchJSONStore(applicationContext)
        i("Watch started")



        // Write a message to the database
        // Write a message to the database
        //val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        //val myRef: DatabaseReference = database.getReference("message")

        //myRef.setValue("Hello, World!")
    }
}

