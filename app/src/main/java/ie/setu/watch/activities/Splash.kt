package ie.setu.watch.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ie.setu.watch.R
import ie.setu.watch.main.MainApp

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@Splash, WatchListActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}