package ie.setu.watch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.watch.databinding.ActivityWatchBinding
import ie.setu.watch.models.WatchModel
import timber.log.Timber
import timber.log.Timber.i

class WatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWatchBinding
    var watch = WatchModel()
    val watchs = ArrayList<WatchModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Watch Activity started...")

        binding.btnAdd.setOnClickListener() {
            watch.title = binding.watchTitle.text.toString()
            if (watch.title.isNotEmpty()) {
                i("add Button Pressed: $watch.title")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
            watch.description = binding.watchDescription.text.toString()
            if (watch.description.isNotEmpty()) {
                i("add Button Pressed: $watch.description")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a description", Snackbar.LENGTH_LONG)
                    .show()
            }

        }
        var watch1 = WatchModel("Test Object")

        watchs.add(watch1)

        println("Successfully Added: " + watch1.toString())
    }
}









