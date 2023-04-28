package ie.setu.watch.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.watch.R
import ie.setu.watch.adapters.WatchAdapter
import ie.setu.watch.adapters.WatchListener
import ie.setu.watch.databinding.ActivityWatchListBinding
import ie.setu.watch.main.MainApp
import ie.setu.watch.models.WatchModel

class WatchListActivity : AppCompatActivity(), WatchListener {


    lateinit var app: MainApp
    private lateinit var binding: ActivityWatchListBinding
    //var listPosition = 0
    private var listPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = WatchAdapter(app.watchs.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, WatchActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, WatchMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.watchs.findAll().size)
            }
            else // Deleting
                if (it.resultCode == 99)     (binding.recyclerView.adapter)?.notifyItemRemoved(listPosition)
        }


    override fun onWatchClick(watch: WatchModel, pos : Int) {
        val launcherIntent = Intent(this, WatchActivity::class.java)
        launcherIntent.putExtra("watch_edit", watch)
        listPosition = pos
        getClickResult.launch(launcherIntent)
    }

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )    { }



    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.watchs.findAll().size)
            }
            //to update list
            else if (it.resultCode == 99) {
                (binding.recyclerView.adapter)?.notifyItemRemoved(listPosition)

            }
        }
}

