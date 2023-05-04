package ie.setu.watch.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import ie.setu.watch.databinding.ActivityWatchMapsBinding
import ie.setu.watch.databinding.ContentWatchMapsBinding
import ie.setu.watch.main.MainApp

class WatchMapsActivity : AppCompatActivity(),GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityWatchMapsBinding
    private lateinit var contentBinding: ContentWatchMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWatchMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentWatchMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        app = application as MainApp
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.watchs.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //val watch = marker.tag as WatchModel
        val tag = marker.tag as Long
        val watch = app.watchs.findById(tag)
        contentBinding.currentTitle.text = watch!!.title
        contentBinding.currentPrice.text = "\u20AC" + watch.price.toString()
        Picasso.get().load(watch.image).into(contentBinding.currentImage)
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }
    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }
    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}




