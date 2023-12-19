package com.example.android4

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private val REQUIRED_PERMISSIONS = mutableListOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }.toTypedArray()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, 42)
        }
        val actionBar: androidx.appcompat.widget.Toolbar = findViewById(R.id.tbMainToolbar)
        setSupportActionBar(actionBar)
        setupNavigation(actionBar)
        println(Build.VERSION.SDK_INT)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 42) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, "permissions not granted, exiting app", Toast.LENGTH_LONG).show()
                finish()
            }
        }
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

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}