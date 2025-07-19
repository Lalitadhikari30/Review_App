package com.example.reviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.reviewapp.Screens.OnboardingScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.review.Screens.ForgotPasswordScreen
import com.example.review.Screens.LoginScreen
import com.example.review.Screens.RegisterScreen
import com.example.review.Screens.VerifyPasswordScreen
import com.example.reviewapp.Screens.AskForReview
import com.example.reviewapp.Screens.EditProfileScreen
import com.example.reviewapp.Screens.GiveReview
import com.example.reviewapp.Screens.HelpCenterScreen
import com.example.reviewapp.Screens.HomeScreen
import com.example.reviewapp.Screens.ProfileScreen
import com.example.reviewapp.Screens.SettingsScreen
import com.example.reviewsapp.MyReviewsScreen
import com.example.reviewsapp.Screen.QueryScreen.QueryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navcontroller=rememberNavController()
            NavHost(navController = navcontroller, startDestination = "HOMESCREEN", builder={
                composable("ONBOARDINGSCREEN",){
                    OnboardingScreen(navcontroller)
                }
                composable("LOGIN",){
                    LoginScreen(navcontroller)
                }
                composable("FORGOTPASSWORD",){
                    ForgotPasswordScreen(navcontroller)
                }
                composable("VERIFYPASSWORD",){
                    VerifyPasswordScreen()
                }
                composable("REGISTER",){
                    RegisterScreen(navcontroller)
                }
                composable("PROFILESCREEN",){
                    ProfileScreen(navcontroller)
                }
                composable("EDITPROFILESCREEN",){
                    EditProfileScreen()
                }
                composable("HELPCENTERSCREEN",){
                    HelpCenterScreen(navcontroller)
                }
                composable("HOMESCREEN",){
                    HomeScreen(navcontroller)
                }
                composable("SETTINGSSCREEN",){
                    SettingsScreen()
                }
                composable("MYREVIEWSSCREEN",){
                    MyReviewsScreen(navController = navcontroller)
                }
                composable("QUERYSCREEN",){
                    QueryScreen()
                }
                composable("ASKFORREVIEW",){
                    AskForReview(navcontroller)
                }
                composable("GIVEREVIEW",){
                    GiveReview(navcontroller)
                }

            }
            )

        }
    }
}