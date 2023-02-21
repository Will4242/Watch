package ie.setu.watch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.setu.watch.R
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
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        var edit:Boolean = false

        i("Watch Activity started...")

        if (intent.hasExtra("watch_edit")) {
            watch = intent.extras?.getParcelable("watch_edit")!!
            binding.watchTitle.setText(watch.title)
            binding.watchDescription.setText(watch.description)
            binding.watchPrice.setText(watch.price.toString())
            binding.watchGender.setText(watch.gender)
            binding.watchSold.setText(watch.sold.toString())
            edit = true
            binding.btnAdd.setText(R.string.button_updateWatch)

        }

        binding.btnAdd.setOnClickListener() {
            watch.title = binding.watchTitle.text.toString()
            watch.description = binding.watchDescription.text.toString()
            watch.price = binding.watchPrice.text.toString().toDouble()
            watch.gender = binding.watchGender.text.toString()
            watch.sold = binding.watchSold.text.toString().toBoolean()
            if (watch.title.isNotEmpty() && watch.description.isNotEmpty() && watch.price > 0 && watch.gender.isNotEmpty()) {
                if(!edit)
                    app.watchs.create(watch.copy())
                else app.watchs.update((watch.copy()))
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,R.string.infoAddWatch, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_watch, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}











