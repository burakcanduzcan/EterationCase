package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.burakcanduzcan.eterationnativedevelopmentstudycase.R
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private var badge: BadgeDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main)!!
            .findNavController()
        setupAppBar(navController)
        setupNavigationComponent(navController)
        setupBasketBadge()

        sharedViewModel.basketCount.observe(this) { count ->
            if (count > 0) {
                badge?.apply {
                    isVisible = true
                    number = count
                }
            } else {
                badge?.isVisible = false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main)!!
            .findNavController()
        return navController.navigateUp() || super.onSupportNavigateUp()
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

    private fun setupBasketBadge() {
        val bottomNavigationView = binding.navView
        badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_basket).apply {
            isVisible = false
            backgroundColor = getColor(R.color.red)
            badgeTextColor = getColor(android.R.color.white)
        }
    }

    fun setAppBarTitle(title: String) {
        supportActionBar?.title = title
    }
}