package com.example.reviewapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
import com.example.reviewsapp.ReviewGivenDetailScreen
import com.example.reviewsapp.ReviewReceivedDetailScreen
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
        composable("MYREVIEWS") {
            MyReviewsScreen(navController)
        }

        composable("REVIEWS_GIVEN_DETAIL/{reviewId}") { backStackEntry ->
            val reviewId = backStackEntry.arguments?.getString("reviewId") ?: ""
            ReviewGivenDetailScreen(
                navController = navController,
                reviewId = reviewId
            )
        }

        composable("REVIEWS_RECEIVED_DETAIL/{reviewId}") { backStackEntry ->
            val reviewId = backStackEntry.arguments?.getString("reviewId") ?: ""
            ReviewReceivedDetailScreen(
                navController = navController,
                reviewId = reviewId
            )
        }

        composable("QUERYSCREEN") {
            QueryScreen()
        }
        composable("ASKFORREVIEWSCREEN") {
            AskForReviewScreen(navController)
        }
        composable(
            route = "GIVEREVIEW/{businessName}/{senderId}",
            arguments = listOf(
                navArgument("businessName") { type = NavType.StringType
                    nullable = false },
                navArgument("senderId") { type = NavType.StringType
                nullable =  false}
            )
        ) { backStackEntry ->
            GiveReview(
                navController = navController,
                businessName= backStackEntry.arguments?.getString("businessName") ?: run {
                    navController.popBackStack()  // Handle missing businessId
                    return@composable
                },
                senderId = backStackEntry.arguments?.getString("senderId") ?: run {
                    navController.popBackStack()  // Handle missing senderId
                    return@composable
                }
            )

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