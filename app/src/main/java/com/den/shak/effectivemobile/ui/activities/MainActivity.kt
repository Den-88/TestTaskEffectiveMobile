package com.den.shak.effectivemobile.ui.activities

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.den.shak.effectivemobile.R
import com.den.shak.effectivemobile.ui.fragments.FavoriteFragment
import com.den.shak.effectivemobile.ui.fragments.HomeFragment
import com.den.shak.effectivemobile.ui.fragments.PlaceholderFragment
import com.den.shak.effectivemobile.ui.fragments.PlaceholderFragmentWithBack
import com.den.shak.effectivemobile.ui.utils.OfferViewUtils
import com.den.shak.effectivemobile.viewmodel.HomeViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModel()
    private var currentFragment: Fragment? = null
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            currentFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, currentFragment!!)
                .commit()
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        viewModel.favoriteVacancies.observe(this) { favorites ->
            updateBadge(favorites.size)
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.navigation_search -> HomeFragment()
                R.id.navigation_favorites -> FavoriteFragment()
                R.id.navigation_responses -> PlaceholderFragment()
                R.id.navigation_messages -> PlaceholderFragment()
                R.id.navigation_profile -> PlaceholderFragment()
                else -> null
            }

            if (selectedFragment != null && selectedFragment != currentFragment) {
                currentFragment = selectedFragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()
            }
            true
        }

        setupBadge()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is PlaceholderFragmentWithBack) {
                    moveTaskToBack(true)
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                }
            }
        })
    }

    private fun setupBadge() {
        val favoritesCount = viewModel.favoriteVacancies.value?.size ?: 0
        updateBadge(favoritesCount)
    }

    private fun updateBadge(favoritesCount: Int) {
        val badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.navigation_favorites)
        if (favoritesCount > 0) {
            badgeDrawable.isVisible = true
            badgeDrawable.number = favoritesCount
            badgeDrawable.backgroundColor = ContextCompat.getColor(this, R.color.red)
            badgeDrawable.badgeTextColor = ContextCompat.getColor(this, R.color.white)
            badgeDrawable.badgeGravity = BadgeDrawable.TOP_END
            badgeDrawable.horizontalOffset = OfferViewUtils.dpToPx(9)
            badgeDrawable.verticalOffset = OfferViewUtils.dpToPx(13)
        } else {
            badgeDrawable.isVisible = false
        }
    }
}
