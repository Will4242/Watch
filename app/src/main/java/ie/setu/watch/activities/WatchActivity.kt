package ie.setu.watch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.watch.databinding.ActivityWatchBinding
import ie.setu.watch.main.MainApp
import ie.setu.watch.models.WatchModel
import timber.log.Timber
import timber.log.Timber.i

class WatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWatchBinding
    var watch = WatchModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Watch Activity started...")
        binding.btnAdd.setOnClickListener() {
            watch.title = binding.watchTitle.text.toString()
            watch.description = binding.watchDescription.text.toString()
            if (watch.title.isNotEmpty()) {
                app.watchs.add(watch.copy())
                i("add Button Pressed: ${watch}")
                for (i in app.watchs.indices)
                { i("Placemark[$i]:${this.app.watchs[i]}") }
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}










