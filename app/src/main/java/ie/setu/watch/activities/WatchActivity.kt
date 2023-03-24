package ie.setu.watch.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.get
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.watch.R
import ie.setu.watch.databinding.ActivityWatchBinding
import ie.setu.watch.helpers.showImagePicker
import ie.setu.watch.main.MainApp
import ie.setu.watch.models.Location
import ie.setu.watch.models.WatchModel
import timber.log.Timber
import timber.log.Timber.i

class WatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWatchBinding
    var watch = WatchModel()
    lateinit var app: MainApp
    lateinit var adapter:ArrayAdapter<String>
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //var location = Location(52.245696, -7.139102, 15f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        //Hiding filed as new watch will have sold set as false
        binding.watchSold.isVisible=false
        binding.btnDelete.isVisible=false

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
            //gets position of spinner in adapter for update
            var spinPos = adapter.getPosition(watch.gender)
            binding.watchGender.setSelection(spinPos)
           // binding.watchSold.isSelected= watch.sold
            edit = true
            binding.btnAdd.setText(R.string.button_updateWatch)

            //So if watch is sold it can be seen in update watch
            binding.watchSold.isVisible=true
            binding.btnDelete.isVisible=true
            binding.watchSold.isChecked = watch.sold
//change back WILL
            if (watch.image != Uri.EMPTY.toString()) {
                binding.chooseImage.setText(R.string.change_watch_image)
            }
            Picasso.get()
                .load(watch.image)
                .into(binding.watchImage)
        }

        binding.watchLocation.setOnClickListener {
            i ("Set Location Pressed")
        }


        binding.btnDelete.setOnClickListener(){
            app.watchs.delete(watch)
            setResult(99)
            finish()
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

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.watchLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (watch.zoom != 0f) {
                location.lat =  watch.lat
                location.lng = watch.lng
                location.zoom = watch.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            //change back WILL
                            watch.image = result.data!!.data!!.toString()
                            Picasso.get()
                                .load(watch.image.toUri())
                                .into(binding.watchImage)
                            binding.chooseImage.setText(R.string.change_watch_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            watch.lat = location.lat
                            watch.lng = location.lng
                            watch.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }



}











