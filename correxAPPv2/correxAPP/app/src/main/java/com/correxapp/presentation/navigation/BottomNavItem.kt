package com.correxapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Class
import androidx.compose.material.icons.outlined.Description
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Exams : BottomNavItem(Screen.Dashboard.route, Icons.Outlined.Description, "Provas")
    object Classes : BottomNavItem(Screen.Classes.route, Icons.Outlined.Class, "Turmas")
}