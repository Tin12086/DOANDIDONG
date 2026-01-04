package projects_Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import projects_asset.UserSession

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
                    // Clear back stack khi đăng nhập thành công
                    navController.navigate("menu_screen") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register"){
            RegisterScreen(onNavigateToLogin = {
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
            })
        }
        composable("menu_screen"){
            MenuScreen(navController = navController)
        }
        composable("update_profile"){
            // Kiểm tra đăng nhập trước khi vào profile
            if (UserSession.isLoggedIn) {
                UpdateProfileScreen(
                    onNavigateToLogin = {
                        UserSession.logout()
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            } else {
                // Nếu chưa đăng nhập, quay về login
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
    }
}