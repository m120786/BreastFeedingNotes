package com.vb.breastfeedingnotes.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen(route = "main_screen")
    object DialogScreen: Screen(route = "dialog_screen")
    object WeightScreen: Screen(route= "weight_screen")
    object AboutScreen: Screen(route= "about_screen")

}
