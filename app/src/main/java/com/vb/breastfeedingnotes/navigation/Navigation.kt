package com.vb.breastfeedingnotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vb.breastfeedingnotes.notesView.NotesScreen
import com.vb.breastfeedingnotes.notesView.NotesViewModel
import com.vb.breastfeedingnotes.notesView.addNote.AddNotePlusViewModel
import com.vb.breastfeedingnotes.notesView.addNote.AddNotePlus
import com.vb.breastfeedingnotes.notesView.timer.TimerViewModel
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = Screen.MainScreen.route) {
            composable(route = Screen.MainScreen.route) {
                NotesScreen(navController)
            }
        composable(route = Screen.DialogScreen.route) {
            AddNotePlus(navController = navController)
        }
        }
}