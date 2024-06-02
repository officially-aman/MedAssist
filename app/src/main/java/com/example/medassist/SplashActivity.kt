package com.example.medassist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.medassist.MainActivity

class SplashActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.splash_progress_bar)
        progressBar?.setVisibility(View.VISIBLE)

        Handler().postDelayed({
            progressBar?.setVisibility(View.GONE)
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, DELAY_TIME.toLong())

    }

    companion object {
        private const val DELAY_TIME = 5000 // delay time in milliseconds
    }
}
