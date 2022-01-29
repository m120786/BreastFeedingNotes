package com.vb.breastfeedingnotes.BottomNavigation

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vb.breastfeedingnotes.navigation.Screen

@Composable
fun BottomNavBar(
    navController: NavController,
) {
    val items = listOf(
        BottomNavItem("Home", Screen.MainScreen.route, Icons.Default.Home),
        BottomNavItem("Weight", Screen.WeightScreen.route, Icons.Default.Info),
        BottomNavItem("About", Screen.AboutScreen.route, Icons.Default.Settings))

    BottomNavigation(
        backgroundColor = Color.DarkGray,
        contentColor = Color.White,
        elevation = 3.dp
    ) {
        items.forEach { item ->
            BottomNavigationItem(selected = item.route == navController.currentDestination?.route,
                onClick = { navController.navigate(item.route) },
                icon = {Icon(imageVector = item.icon, contentDescription = item.name)})
        }
    }
}