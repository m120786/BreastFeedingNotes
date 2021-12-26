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
    val viewModel: NotesViewModel = viewModel()
    val timerViewModel: TimerViewModel = viewModel()
    val addNotePlusViewModel: AddNotePlusViewModel = viewModel()
    val context = LocalContext.current


    NavHost(navController = navController,
        startDestination = Screen.MainScreen.route) {
            composable(route = Screen.MainScreen.route) {
                NotesScreen(viewModel, timerViewModel, navController)
            }
        composable(route = Screen.DialogScreen.route) {
            AddNotePlus(addNotePlusViewModel = addNotePlusViewModel, context = context, navController = navController)
        }
        }
}