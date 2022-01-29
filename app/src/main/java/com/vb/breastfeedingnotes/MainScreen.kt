package com.vb.breastfeedingnotes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vb.breastfeedingnotes.BottomNavigation.BottomNavBar
import com.vb.breastfeedingnotes.navigation.Navigation
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.time.ExperimentalTime

@OptIn(InternalCoroutinesApi::class)
@ExperimentalTime
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNavBar(navController = navController) }) { barPadding ->
        Box(modifier = Modifier.padding(barPadding)) {
            Navigation(navController)
        }
    }
}