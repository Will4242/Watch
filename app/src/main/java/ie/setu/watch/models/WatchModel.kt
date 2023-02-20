package ie.setu.watch.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatchModel(var id: Long = 0,
                      var title: String = "",
                      var description: String = "",
                      var price: Double = 0.00,
                      var gender: String = "",
                      var available: Boolean = true
                      ) : Parcelable
