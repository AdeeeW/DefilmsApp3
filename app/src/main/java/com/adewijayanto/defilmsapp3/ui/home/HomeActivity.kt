package com.adewijayanto.defilmsapp3.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.adewijayanto.defilmsapp3.R
import com.adewijayanto.defilmsapp3.databinding.ActivityHomeBinding
import com.adewijayanto.defilmsapp3.ui.settings.SettingsActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeBinding
    private lateinit var mController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        val navHostFragment: Fragment =
            supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        mController = navHostFragment.findNavController()

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.menu_movie,
            R.id.menu_tv_show,
            R.id.menu_favorite
        ).build()

        setupActionBarWithNavController(mController, appBarConfiguration)
        homeBinding.apply {
            navButton.setupWithNavController(mController)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(itemId: Int) {
        when (itemId) {
            R.id.settings -> {
                val mIntent = Intent(this, SettingsActivity::class.java)
                startActivity(mIntent)
            }
        }
    }
}