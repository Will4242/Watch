package ie.setu.watch.models

interface WatchStore {
    fun findAll(): List<WatchModel>
    fun create(watch: WatchModel)
    fun update(watch: WatchModel)
    fun delete(watch: WatchModel)
}