package com.factus.app.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.factus.app.ui.facture.FactureScreen
import com.factus.app.ui.facture.FactureViewModel
import com.factus.app.ui.information.HomeScreen
import com.factus.app.ui.login.Login
import com.factus.app.ui.login.LoginViewModel
import com.factus.app.ui.navigation.RouteFactus
import com.factus.app.ui.theme.FactusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FactusTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) { innerPadding ->
                    MainNavigation(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun MainNavigation(modifier: Modifier) {
        val navController = rememberNavController()
        NavigationHost(navController = navController, modifier)
    }

    @Composable
    fun NavigationHost(navController: NavHostController, modifier: Modifier) {
        val loginViewModel = hiltViewModel<LoginViewModel>()
        val factureViewModel = hiltViewModel<FactureViewModel>()

        NavHost(navController = navController, startDestination = RouteFactus.Login.route) {
            composable(route = RouteFactus.Home.route) {
                HomeScreen(navController, modifier)
            }
            composable(route = RouteFactus.Login.route) {
                Login(loginViewModel, navController, modifier)
            }
            composable(route = RouteFactus.Facture.route) {
                FactureScreen(factureViewModel, navController, modifier)
            }
        }
    }

    private fun getArgument(navBackStackEntry: NavBackStackEntry, key: String): String? {
        return navBackStackEntry.arguments?.getString(key)
    }
}