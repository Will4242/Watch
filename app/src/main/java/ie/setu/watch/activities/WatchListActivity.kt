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
    var listPosition = 0

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
        }

    override fun onWatchClick(watch: WatchModel, pos: Int) {
        //get position for deleting
        listPosition=pos
        val launcherIntent = Intent(this, WatchActivity::class.java)
        launcherIntent.putExtra("watch_edit", watch)
        getClickResult.launch(launcherIntent)
    }

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

