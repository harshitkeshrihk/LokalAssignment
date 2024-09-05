package com.example.lokalassignmentapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lokalassignmentapp.Screens.BookmarksFragment
import com.example.lokalassignmentapp.Screens.JobFragment
import com.example.lokalassignmentapp.ViewModel.MainViewModel
import com.example.lokalassignmentapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val jobFragment = JobFragment()
    private val bookmarksFragment = BookmarksFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = jobFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.rootLayout, jobFragment, getString(R.string.JobFragment))
            add(R.id.rootLayout, bookmarksFragment, getString(R.string.BookmarksFragment)).hide(bookmarksFragment)
        }.commit()


        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener)

    }

     private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.Job-> {
                fragmentManager.beginTransaction().hide(activeFragment).show(jobFragment).commit()
                activeFragment = jobFragment
                true
            }
            R.id.bookmarks-> {
                fragmentManager.beginTransaction().hide(activeFragment).show(bookmarksFragment).commit()
                activeFragment = bookmarksFragment
                true
            }
            else -> false
        }
    }

}
