package com.danielblandford.spaceflightnews

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupWithNavController
import com.danielblandford.spaceflightnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        binding.navView?.let {
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_articles
                ),
                binding.drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            it.setupWithNavController(navController)
        }

        binding.appBarMain.contentMain.bottomNavView?.let {
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_articles
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            it.setupWithNavController(navController)
            binding.appBarMain.contentMain.bottomNavView!!.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}