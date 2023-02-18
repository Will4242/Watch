package ie.setu.watch.models

import timber.log.Timber.i

class WatchMemStore : WatchStore {

    val watchs = ArrayList<WatchModel>()

    override fun findAll(): List<WatchModel> {
        return watchs
    }

    override fun create(watch: WatchModel) {
        watchs.add(watch)
        logAll()
    }

    fun logAll() {
        watchs.forEach{ i("${it}") }
    }
}