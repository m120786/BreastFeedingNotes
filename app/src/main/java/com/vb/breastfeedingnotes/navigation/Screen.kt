package com.vb.breastfeedingnotes.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen(route = "main_screen")
    object DialogScreen: Screen(route = "dialog_screen")

}
