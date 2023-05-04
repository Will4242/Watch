package ie.setu.watch.models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class WatchMemStore : WatchStore {







    val watchs = ArrayList<WatchModel>()

    override fun findAll(): List<WatchModel> {
        return watchs
    }

    override fun findById(id:Long) : WatchModel? {
        val foundWatch: WatchModel? = watchs.find { it.id == id }
        return foundWatch
    }

    override fun create(watch: WatchModel) {
        watch.id = getId()
        watchs.add(watch)
        logAll()

        //val db = Firebase.database
       // val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        //val myRef: DatabaseReference = db.getReference("watchs")
        //myRef.setValue(watch)
        //myRef.setValue("Hello, World!")

       /* db.collection("watchs")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }*/

        val database = Firebase.database.reference
        database.child("watchs").child(watch.id.toString()).setValue(watch)
    }

    override fun update(watch: WatchModel) {
        var foundWatch: WatchModel? = watchs.find { p -> p.id == watch.id }
        if (foundWatch != null) {
            foundWatch.title = watch.title
            foundWatch.description = watch.description
            foundWatch.price = watch.price
            foundWatch.gender = watch.gender
            foundWatch.sold = watch.sold
            foundWatch.image = watch.image
            foundWatch.lat = watch.lat
            foundWatch.lng = watch.lng
            foundWatch.zoom = watch.zoom
            logAll()
        }
    }

    override fun delete(watch: WatchModel) {
        var foundWatch: WatchModel? = watchs.find { p -> p.id == watch.id }
        if (foundWatch != null) {
            watchs.remove(foundWatch)
        }
    }

    private fun logAll() {
        watchs.forEach { i("$it") }
    }
}
