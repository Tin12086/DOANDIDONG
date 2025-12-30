package projects_Screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onNavigateToHomePage = {
                    navController.navigate("menu_screen")
                }
            )
        }
        composable("register"){
            RegisterScreen(onNavigateToLogin = {
                navController.navigate("login"){
                    popUpTo("register"){
                        inclusive = true
                    }
                }
            })
        }
        composable("menu_screen"){
            MenuScreen()
        }
    }
}