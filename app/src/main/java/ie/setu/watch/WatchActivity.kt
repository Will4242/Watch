package ie.setu.watch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber

class WatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch)

        Timber.plant(Timber.DebugTree())

        Timber.i("Placemark Activity started..")
    }
}

