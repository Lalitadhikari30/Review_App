package com.example.reviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.reviewapp.Screens.OnboardingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Use your app theme here or MaterialTheme
            MaterialTheme {
                OnboardingScreen(
                    onGetStarted = {
                        // TODO: Navigate to next screen (use Navigation or Intent)
                    }
                )
            }
        }
    }
}