package com.correxapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType // <-- IMPORT CORRIGIDO ADICIONADO
import androidx.navigation.compose.*
import androidx.navigation.navArgument // <-- IMPORT CORRIGIDO ADICIONADO
import com.correxapp.presentation.ui.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(BottomNavItem.Exams, BottomNavItem.Classes)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Exams.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Telas principais (abas)
            composable(BottomNavItem.Exams.route) { DashboardScreen(navController = navController) }
            composable(BottomNavItem.Classes.route) { ClassesScreen(navController = navController) }

            // Telas secundárias (que não aparecem na barra de navegação)
            composable(Screen.CreateExam.route) { CreateExamScreen(navController = navController) }
            composable(
                route = Screen.ExamDetails.route,
                arguments = listOf(navArgument("examId") { type = NavType.LongType })
            ) { ExamDetailsScreen(navController = navController) }
            composable(
                route = Screen.ScanSheet.route,
                arguments = listOf(navArgument("examId") { type = NavType.LongType })
            ) { ScanScreen(navController = navController) }
            composable(
                route = Screen.ClassDetails.route,
                arguments = listOf(navArgument("classId") { type = NavType.LongType })
            ) { Text("Tela de Detalhes da Turma em breve") } // Placeholder
        }
    }
}