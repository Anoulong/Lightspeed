package com.example.lighspeed

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.example.lighspeed.databinding.ActivityMainBinding
import core.DependencyManager
import core.EventDataState
import core.EventEntity

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val dependencyManager = DependencyManager()

    private val eventViewModel =  dependencyManager.providesEventViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            val event =  EventEntity(tag = "LightSpeed", body = "Anoulong", appID = BuildConfig.APPLICATION_ID)
            eventViewModel.registerEvent(event).observe(this@MainActivity, Observer { eventState ->
                when(eventState){
                    is EventDataState.Success<*> -> {
                        Snackbar.make(view, eventState.data.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()

                    }
                    is EventDataState.Error -> {
                        Snackbar.make(view, eventState.error, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()

                    }
                     EventDataState.Loading -> {
                         //TODO Show loading
                    }
                }

            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}