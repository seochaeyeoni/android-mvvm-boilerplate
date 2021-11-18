package com.alice.androidmvvm.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.alice.androidmvvm.ui.detail.DetailScreen
import com.alice.androidmvvm.ui.main.MainScreen
import com.alice.androidmvvm.ui.theme.AndroidMvvmTheme

@Composable
fun TodoApp() {
    AndroidMvvmTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Destination.MAIN
        ) {
            composable(Destination.MAIN) {
                MainScreen(
                    onItemClick = { date, isNew ->
                        navController.navigate("${Destination.DETAIL}/$date/$isNew")
                    }
                )
            }
            composable(
                route = "${Destination.DETAIL}/{date}/{isNew}",
                arguments = listOf(
                    navArgument("date") {
                        type = NavType.IntType
                    },
                    navArgument("isNew") {
                        type = NavType.BoolType
                    }
                )
            ) { entry ->
                val date = entry.arguments?.getInt("date")
                val isNew = entry.arguments?.getBoolean("isNew")
                if (date != null && isNew != null) {
                    DetailScreen(date, isNew)
                }
            }
        }
    }
}

object Destination {
    const val MAIN = "main"
    const val DETAIL = "detail"
}
