package com.example.android4

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: androidx.appcompat.widget.Toolbar = findViewById(R.id.tbMainToolbar)
        setSupportActionBar(actionBar)
        setupNavigation(actionBar)
    }

    private fun setupNavigation(actionBar: androidx.appcompat.widget.Toolbar) {
        val sp = getSharedPreferences("userData", Context.MODE_PRIVATE)
        val drawer: DrawerLayout = findViewById(R.id.dlDrawerLayout)
        val navView: NavigationView = findViewById(R.id.nvMainNavView)
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nhNavHost) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val btmNavView: BottomNavigationView = findViewById(R.id.bnvBottomNavView)
        bindActionBarWithNavigation(drawer, actionBar)
        navView.setupWithNavController(navController)
        btmNavView.setupWithNavController(navController)
        Utilities.setupNavHeader(navView, sp)
    }
    private fun bindActionBarWithNavigation(
        drawer: DrawerLayout,
        actionBar: androidx.appcompat.widget.Toolbar
    ) {
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            actionBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }
}