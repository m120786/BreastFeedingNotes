package com.vb.breastfeedingnotes.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vb.breastfeedingnotes.views.notesView.NotesScreen
import com.vb.breastfeedingnotes.views.notesView.addNote.AddNotePlus
import com.vb.breastfeedingnotes.views.weightScreen.WeightScreenView
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.time.ExperimentalTime

@InternalCoroutinesApi
@ExperimentalTime
@Composable
fun Navigation(navController: NavHostController) {





    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            NotesScreen(navController)
        }
        composable(route = Screen.DialogScreen.route) {
            AddNotePlus(navController = navController)
        }
        composable(route = Screen.WeightScreen.route) {
            WeightScreenView()
        }
        composable(route = Screen.AboutScreen.route) {
            AboutScreenComposable()
        }

    }
}





@Composable
fun AboutScreenComposable() {
    Text("About screen composable")
}