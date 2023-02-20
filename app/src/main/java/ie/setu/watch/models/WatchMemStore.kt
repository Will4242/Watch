package ie.setu.watch.models

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

    override fun create(watch: WatchModel) {
        watch.id = getId()
        watchs.add(watch)
        logAll()
    }

    override fun update(watch: WatchModel) {
        var foundWatch: WatchModel? = watchs.find { p -> p.id == watch.id }
        if (foundWatch != null) {
            foundWatch.title = watch.title
            foundWatch.description = watch.description
            foundWatch.price = watch.price
            foundWatch.gender = watch.gender
            foundWatch.available = watch.available
            logAll()
        }
    }

    private fun logAll() {
        watchs.forEach { i("$it") }
    }
}
