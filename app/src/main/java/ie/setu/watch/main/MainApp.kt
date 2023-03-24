package ie.setu.watch.main

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.setu.watch.models.WatchMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val watchs = WatchMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Watch started")

        // Write a message to the database
        // Write a message to the database
        //val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        //val myRef: DatabaseReference = database.getReference("message")

        //myRef.setValue("Hello, World!")
    }
}

