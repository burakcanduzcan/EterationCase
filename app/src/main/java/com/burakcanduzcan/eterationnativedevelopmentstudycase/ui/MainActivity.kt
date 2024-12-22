package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.burakcanduzcan.eterationnativedevelopmentstudycase.R
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main)!!
            .findNavController()
        setupAppBar(navController)
        setupNavigationComponent(navController)
    }

    private fun setupAppBar(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_basket)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupNavigationComponent(navController: NavController) {
        binding.navView.setupWithNavController(navController)
    }

    fun setAppBarTitle(title: String) {
        supportActionBar?.title = title
    }
}