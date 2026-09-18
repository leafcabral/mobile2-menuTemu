package com.leafcabral.menutemu

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.leafcabral.menutemu.categories.AccessoriesFragment
import com.leafcabral.menutemu.categories.BooksFragment
import com.leafcabral.menutemu.categories.ElectronicsFragment
import com.leafcabral.menutemu.categories.FashionFragment
import com.leafcabral.menutemu.categories.FurnitureFragment
import com.leafcabral.menutemu.categories.StationeryFragment
import com.leafcabral.menutemu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
	private lateinit var fragmentManager: FragmentManager
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		WindowCompat.setDecorFitsSystemWindows(window, false)
		WindowInsetsControllerCompat(window, window.decorView)
			.hide(WindowInsetsCompat.Type.statusBars())

		setSupportActionBar(binding.toolbar)

		val toggle = ActionBarDrawerToggle(
			this,
			binding.root,
			binding.toolbar,
			R.string.nav_open,
			R.string.nav_close
		)
		binding.root.addDrawerListener(toggle)
		toggle.syncState()

		binding.navigationDrawer.setNavigationItemSelectedListener(this)

		binding.bottomNavigation.setOnItemSelectedListener { item ->
			openFragment(
				when (item.itemId) {
					R.id.bottom_home -> HomeFragment()
					R.id.bottom_cart -> CartFragment()
					R.id.bottom_profile -> ProfileFragment()
					R.id.bottom_menu -> MenuFragment()
					else -> HomeFragment()
				}
			)
			true
		}

		fragmentManager = supportFragmentManager
		openFragment(HomeFragment())

		binding.floatingAction.setOnClickListener {
			Toast.makeText(this, "Categorias", Toast.LENGTH_SHORT).show()
		}

		onBackPressedDispatcher.addCallback(this) {
			if (binding.root.isDrawerOpen(GravityCompat.START)) {
				binding.root.closeDrawer(GravityCompat.START)
			} else {
				finish()
			}
		}
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		openFragment(
			when (item.itemId) {
				R.id.nav_books -> BooksFragment()
				R.id.nav_fashion -> FashionFragment()
				R.id.nav_furniture -> FurnitureFragment()
				R.id.nav_stationery -> StationeryFragment()
				R.id.nav_accessories -> AccessoriesFragment()
				R.id.nav_electronics -> ElectronicsFragment()
				else -> null
			}
		)

		binding.root.closeDrawer(GravityCompat.START)
		return true
	}

	private fun openFragment(fragment: Fragment?) {
		if (fragment == null) { return }
		val fragmentTransaction = fragmentManager.beginTransaction()
		fragmentTransaction.replace(R.id.fragment_container, fragment)
		fragmentTransaction.commit()
	}
}