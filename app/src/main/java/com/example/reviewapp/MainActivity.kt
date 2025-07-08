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
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navcontroller=rememberNavController()
            NavHost(navController = navcontroller, startDestination = "ONBOARDINGSCREEN", builder={
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

            } )
            // Use your app theme here or MaterialTheme
//            MaterialTheme {
//                OnboardingScreen(
//                    onGetStarted = {
//                        // TODO: Navigate to next screen (use Navigation or Intent)
//                    }
//                )
//            }
        }
    }
}