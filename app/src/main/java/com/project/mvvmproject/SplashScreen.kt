package com.project.mvvmproject

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import com.project.mvvmproject.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private val SPLASH_SCREEN = 2000f
    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            val intent = Intent(applicationContext, MainActivity::class.java)

            val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashScreen)
            startActivity(intent, options.toBundle())
        }, SPLASH_SCREEN.toLong())
    }
}