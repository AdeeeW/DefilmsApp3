package com.adewijayanto.defilmsapp3.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.adewijayanto.defilmsapp3.databinding.ActivitySpalshScreenBinding
import com.adewijayanto.defilmsapp3.ui.home.HomeActivity

class SpalshScreen : AppCompatActivity() {
    private lateinit var splashScreenBinding: ActivitySpalshScreenBinding

    companion object{
        const val SPLASH_TIME: Long = 8000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        WindowManager.LayoutParams.FLAG_FULLSCREEN

        setContentView(splashScreenBinding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        },SPLASH_TIME)
    }
}