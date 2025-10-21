package com.unie.historialMedico.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.unie.historialMedico.ui.records.MedicalRecordDetailScreen
import com.unie.historialMedico.ui.records.MedicalRecordsScreen
import com.unie.historialMedico.ui.welcome.WelcomeScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = "welcome") {
            composable("welcome") {
                WelcomeScreen(
                    modifier = modifier,
                    onNavigateToRecords = { navController.navigate("records") }
                )
            }
            composable("records") {
                this@SharedTransitionLayout.MedicalRecordsScreen(
                    modifier = modifier,
                    animatedVisibilityScope = this@composable,
                    onRecordClick = { record ->
                        navController.navigate("recordDetail/${record.id}/${record.description}")
                    }
                )
            }
            composable(
                route = "recordDetail/{recordId}/{description}",
                arguments = listOf(
                    navArgument("recordId") { type = NavType.IntType },
                    navArgument("description") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                this@SharedTransitionLayout.MedicalRecordDetailScreen(
                    recordId = backStackEntry.arguments?.getInt("recordId") ?: 0,
                    description = backStackEntry.arguments?.getString("description") ?: "",
                    animatedVisibilityScope = this@composable,
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}
