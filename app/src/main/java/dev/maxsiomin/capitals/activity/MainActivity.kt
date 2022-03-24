package dev.maxsiomin.capitals.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.capitals.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        findNavController(R.id.fragmentContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            null -> super.onBackPressed()
            R.id.homeFragment -> super.onBackPressed()
            R.id.gameResultsFragment -> navController.popBackStack(R.id.homeFragment, false)
            else -> navController.popBackStack()
        }
    }
}
