package com.example.reviewapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reviewapp.Screens.MyBusinessScreen
import com.example.review.Screens.ForgotPasswordScreen
import com.example.reviewapp.Screens.LoginScreen
import com.example.reviewapp.Screens.RegisterScreen
import com.example.review.Screens.VerifyPasswordScreen
import com.example.reviewapp.Screens.AboutScreen
import com.example.reviewapp.Screens.AskForReviewScreen
import com.example.reviewapp.Screens.EditProfileScreen
import com.example.reviewapp.Screens.GiveReview
import com.example.reviewapp.Screens.HelpCenterScreen
import com.example.reviewapp.Screens.HomeScreen
import com.example.reviewapp.Screens.NotificationScreen
import com.example.reviewapp.Screens.OnboardingScreen
import com.example.reviewapp.Screens.ProfileScreen
import com.example.reviewapp.Screens.SettingsScreen
import com.example.reviewsapp.MyReviewsScreen
import com.example.reviewsapp.Screen.QueryScreen.QueryScreen

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "ONBOARDINGSCREEN") {
        composable("ONBOARDINGSCREEN") {
            OnboardingScreen(navController)
        }
        composable("LOGIN") {
            LoginScreen(modifier, navController, authViewModel)
        }
        composable("FORGOTPASSWORD") {
            ForgotPasswordScreen(navController)
        }
        composable("VERIFYPASSWORD") {
            VerifyPasswordScreen()
        }
        composable("REGISTER") {
            RegisterScreen(modifier, navController, authViewModel)
        }
        composable("PROFILESCREEN") {
            ProfileScreen(navController)
        }
        composable("EDITPROFILESCREEN") {
            EditProfileScreen(navController)
        }
        composable("HELPCENTERSCREEN") {
            HelpCenterScreen(navController)
        }
        composable("HOMESCREEN") {
            HomeScreen(modifier, navController, authViewModel)
        }
        composable("SETTINGSSCREEN") {
            SettingsScreen(navController)
        }
        composable("MYREVIEWSSCREEN") {
            MyReviewsScreen(navController)
        }
        composable("QUERYSCREEN") {
            QueryScreen()
        }
        composable("ASKFORREVIEWSCREEN") {
            AskForReviewScreen(navController = navController)
        }
        composable("GIVEREVIEW") {
            GiveReview(navController)
        }
        composable("MyBusinessScreen") {
            MyBusinessScreen(navController = navController, highlightedBusinessId = null)
        }
        composable("NOTIFICATIONSCREEN") {
            NotificationScreen(navController)
        }
        composable("ABOUTSCREEN") {
            AboutScreen(navController)
        }
    }
}