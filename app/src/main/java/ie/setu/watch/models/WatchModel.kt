package ie.setu.watch.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatchModel(var id: Long = 0,
                      var title: String = "",
                      var description: String = "") : Parcelable
