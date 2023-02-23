package ie.setu.watch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.get
import androidx.core.view.isVisible
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
    lateinit var adapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        //Hiding filed as new watch will have sold set as false
        binding.watchSold.isVisible=false

        app = application as MainApp

        //flag for edit to update
        var edit:Boolean = false

        //Reference for spinner
        //https://www.geeksforgeeks.org/spinner-in-kotlin/
        val genders = resources.getStringArray(R.array.gender)
        // access the spinner
        val spinner = findViewById<Spinner>(R.id.watchGender)
        if (spinner != null) {
            adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, genders)
            spinner.adapter = adapter

            //can be removed
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        i("Watch Activity started...")

        if (intent.hasExtra("watch_edit")) {
            watch = intent.extras?.getParcelable("watch_edit")!!
            binding.watchTitle.setText(watch.title)
            binding.watchDescription.setText(watch.description)
            binding.watchPrice.setText(watch.price.toString())
            var spinPos = adapter.getPosition(watch.gender)
            binding.watchGender.setSelection(spinPos)
           // binding.watchSold.isSelected= watch.sold
            edit = true
            binding.btnAdd.setText(R.string.button_updateWatch)

            //So if watch is sold it can be seen in update watch
            binding.watchSold.isVisible=true
            binding.watchSold.isChecked = watch.sold

        }

        binding.btnAdd.setOnClickListener() {
            watch.title = binding.watchTitle.text.toString()
            watch.description = binding.watchDescription.text.toString()
            watch.price = binding.watchPrice.text.toString().toDouble()
            watch.gender = binding.watchGender.selectedItem.toString()
            //watch.sold = false
            if (!watch.gender.equals("Please Select Gender") && watch.title.isNotEmpty() && watch.description.isNotEmpty() && watch.price > 0 && watch.gender.isNotEmpty()) {
                if(!edit)
                    app.watchs.create(watch.copy())
                else {
                    watch.sold= binding.watchSold.isChecked
                    app.watchs.update((watch.copy()))
                }
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











