package com.test.presentation


import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.test.R
import com.test.databinding.ActivityMainBinding
import com.test.presentation.base.BaseActivity
import com.test.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()
    lateinit var navHostFragment : NavHostFragment
    lateinit var navController : NavController
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(R.id.homeFragment)

        navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)


    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onBackPressed() {
        val currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (currentFragment is HomeFragment)
            finish()
        else
            navController.popBackStack()


    }

}