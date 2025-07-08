
@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.review.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import java.util.regex.Pattern


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    onBackPressed: () -> Unit = {},
    onPasswordResetSent: (String) -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF0F0)) // Light pink background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Back button
            IconButton(
                onClick ={
                    navController.popBackStack()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "Forgot Password?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(48.dp))

            // Illustration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                // Question mark illustration (using text as placeholder)
                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "?",
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF4444), // Red color
                        textAlign = TextAlign.Center
                    )
                }

                // Character illustration (simplified)
                Box(
                    modifier = Modifier
                        .offset(x = (-40).dp, y = 20.dp)
                        .size(40.dp)
                        .background(
                            Color(0xFF4A90E2),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👤",
                        fontSize = 20.sp
                    )
                }

                // Small decorative elements
                Box(
                    modifier = Modifier
                        .offset(x = 60.dp, y = 40.dp)
                        .size(16.dp)
                        .background(
                            Color(0xFF4A90E2),
                            shape = RoundedCornerShape(8.dp)
                        )
                )

                Box(
                    modifier = Modifier
                        .offset(x = 50.dp, y = 60.dp)
                        .size(12.dp)
                        .background(
                            Color(0xFF4CAF50),
                            shape = RoundedCornerShape(6.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Description text
            Text(
                text = "Enter the registered email address",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We will email you a link to reset your password",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email input field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = isValidEmail(it) || it.isEmpty()
                },
                label = { Text("Email Address") },
                placeholder = { Text("Enter your email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = !isEmailValid,
                supportingText = if (!isEmailValid) {
                    { Text("Please enter a valid email address") }
                } else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF4444),
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                    focusedLabelColor = Color(0xFFFF4444),
                    cursorColor = Color(0xFFFF4444)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Send button
            Button(
                onClick = {
                    if (email.isEmpty()) {
                        Toast.makeText(context, "Please enter your email address", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (!isValidEmail(email)) {
                        isEmailValid = false
                        return@Button
                    }

                    isLoading = true

                    // Simulate API call
                    // In real app, replace with actual API call
                    kotlinx.coroutines.MainScope().launch {
                        kotlinx.coroutines.delay(2000) // Simulate network delay
                        isLoading = false
                        onPasswordResetSent(email)
                        Toast.makeText(
                            context,
                            "Password reset link sent to $email",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF4444)
                ),
                shape = RoundedCornerShape(28.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Send",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

// Email validation function
fun isValidEmail(email: String): Boolean {
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9+._%-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9-]{0,25})+"
    )
    return emailPattern.matcher(email).matches()
}


@Composable
fun ForgotPasswordScreenPreview() {
    MaterialTheme {
        ForgotPasswordScreen(rememberNavController())
    }
}

// Usage in your Activity/Fragment:
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourAppTheme {
                ForgotPasswordScreen(
                    onBackPressed = {
                        // Handle back navigation
                        finish() // or navigate back
                    },
                    onPasswordResetSent = { email ->
                        // Handle successful password reset request
                        // Navigate to success screen or show confirmation
                    }
                )
            }
        }
    }
}
*/