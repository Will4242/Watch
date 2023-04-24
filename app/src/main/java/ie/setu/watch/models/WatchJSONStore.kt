package ie.setu.watch.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.setu.watch.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "watchs.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<WatchModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class WatchJSONStore(private val context: Context) : WatchStore {

    var watchs = mutableListOf<WatchModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<WatchModel> {
        logAll()
        return watchs
    }

    override fun create(watch: WatchModel) {
        watch.id = generateRandomId()
        watchs.add(watch)
        serialize()
    }


    override fun update(watch: WatchModel) {
        // todo
    }

    override fun delete(watch: WatchModel) {
        TODO("Not yet implemented")
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(watchs, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        watchs = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        watchs.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}