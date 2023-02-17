package ie.setu.watch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.setu.watch.R
import ie.setu.watch.main.MainApp

class WatchListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)
        app = application as MainApp
    }
}