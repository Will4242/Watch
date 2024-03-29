package ie.setu.watch.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatchModel(var id: Long = 0,
                      var title: String = "",
                      var description: String = "",
                      var price: Double = 0.00,
                      var gender: String = "",
                      var sold: Boolean = false,
                      //change back
                      //var image: String = "",
                      var image: Uri = Uri.EMPTY,
                      var lat : Double = 0.0,
                      var lng: Double = 0.0,
                      var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
