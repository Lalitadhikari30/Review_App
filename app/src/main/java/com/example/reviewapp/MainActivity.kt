



package com.example.reviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            reviewappTheme {
                MainScreen(authViewModel = authViewModel)
            }
        }
    }
}

@Composable
fun reviewappTheme(content: @Composable () -> Unit) {
    content()
}

@Composable
fun MainScreen(authViewModel: AuthViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            MyAppNavigation(modifier = Modifier.padding(innerPadding), authViewModel = authViewModel)
        }
    )
}