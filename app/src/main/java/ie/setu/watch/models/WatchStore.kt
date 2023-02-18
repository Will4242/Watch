package ie.setu.watch.models

interface WatchStore {
    fun findAll(): List<WatchModel>
    fun create(watch: WatchModel)
}