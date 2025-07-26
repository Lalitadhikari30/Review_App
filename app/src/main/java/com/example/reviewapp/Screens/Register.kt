
@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.reviewapp.Screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.rememberNavController
import com.example.reviewapp.AuthState
import com.example.reviewapp.AuthViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier, navController: NavController, authViewModel : AuthViewModel,
    onRegisterSuccess: (String, String, String) -> Unit = { _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Validation states
    var isUsernameValid by remember { mutableStateOf(true) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var isConfirmPasswordValid by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Loading -> isLoading = true
            is AuthState.Authenticated,
            is AuthState.Error -> isLoading = false
            else -> {}
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF0F0)) // Light pink background
    ) {
        // Decorative dots pattern
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            drawDecorativeDots()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Geometric illustration
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                GeometricIllustration()
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "Register",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "please enter your details below to continue",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Username field
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    isUsernameValid = it.length >= 3 || it.isEmpty()
                },
                label = { Text("Username") },
                placeholder = { Text("Enter your username") },
                isError = !isUsernameValid,
                supportingText = if (!isUsernameValid) {
                    { Text("Username must be at least 3 characters") }
                } else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (!isUsernameValid) 80.dp else 64.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF4444),
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                    focusedLabelColor = Color(0xFFFF4444),
                    cursorColor = Color(0xFFFF4444),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White.copy(alpha = 0.9f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email field
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
                    .height(if (!isEmailValid) 80.dp else 64.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF4444),
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                    focusedLabelColor = Color(0xFFFF4444),
                    cursorColor = Color(0xFFFF4444),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White.copy(alpha = 0.9f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = it.length >= 6 || it.isEmpty()
                },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = !isPasswordValid,
                supportingText = if (!isPasswordValid) {
                    { Text("Password must be at least 6 characters") }
                } else null,
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (!isPasswordValid) 80.dp else 64.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF4444),
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                    focusedLabelColor = Color(0xFFFF4444),
                    cursorColor = Color(0xFFFF4444),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White.copy(alpha = 0.9f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    isConfirmPasswordValid = it == password || it.isEmpty()
                },
                label = { Text("Confirm Password") },
                placeholder = { Text("Confirm your password") },
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = !isConfirmPasswordValid,
                supportingText = if (!isConfirmPasswordValid) {
                    { Text("Passwords do not match") }
                } else null,
                trailingIcon = {
                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(
                            imageVector = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isConfirmPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (!isConfirmPasswordValid) 80.dp else 64.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF4444),
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                    focusedLabelColor = Color(0xFFFF4444),
                    cursorColor = Color(0xFFFF4444),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White.copy(alpha = 0.9f)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Sign Up button
            Button(
                onClick = {
                    val isFormValid = validateForm(
                        username = username,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword,
                        onUsernameValidation = { isUsernameValid = it },
                        onEmailValidation = { isEmailValid = it },
                        onPasswordValidation = { isPasswordValid = it },
                        onConfirmPasswordValidation = { isConfirmPasswordValid = it }
                    )

                    if (isFormValid) {
                        isLoading = true
                        authViewModel.signup(username, email, password)

                        // optional feedback (remove if handled via LiveData)
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please fix the errors above", Toast.LENGTH_SHORT).show()
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
                        text = "Sign Up",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
//                        modifier = Modifier
//                            .clickable {
//                                authViewModel.signup(email, password)
//                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login link
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account? ")
                        withStyle(style = SpanStyle(
                            color = Color(0xFFFF4444),
                            fontWeight = FontWeight.Medium
                        )) {
                            append("Login")
                        }
                    },
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        onLoginClick()
                        navController.navigate("LOGIN")}
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun GeometricIllustration() {
    Canvas(
        modifier = Modifier.size(160.dp)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Dark gray circle (back)
        drawCircle(
            color = Color(0xFF666666),
            radius = 50.dp.toPx(),
            center = Offset(centerX - 20.dp.toPx(), centerY - 20.dp.toPx())
        )

        // Light gray hexagon (middle)
        drawHexagon(
            color = Color(0xFFCCCCCC),
            centerX = centerX,
            centerY = centerY,
            radius = 45.dp.toPx()
        )

        // Red hexagon (front)
        drawHexagon(
            color = Color(0xFFFF4444),
            centerX = centerX + 25.dp.toPx(),
            centerY = centerY + 25.dp.toPx(),
            radius = 35.dp.toPx()
        )

        // Woman silhouette (simplified representation)
        drawCircle(
            color = Color.White.copy(alpha = 0.9f),
            radius = 25.dp.toPx(),
            center = Offset(centerX - 10.dp.toPx(), centerY - 10.dp.toPx())
        )

        // Face representation
        drawCircle(
            color = Color(0xFF333333),
            radius = 8.dp.toPx(),
            center = Offset(centerX - 10.dp.toPx(), centerY - 15.dp.toPx())
        )
    }
}

fun DrawScope.drawHexagon(color: Color, centerX: Float, centerY: Float, radius: Float) {
    val path = Path()
    val angles = (0..5).map { it * 60f * Math.PI / 180f }

    angles.forEachIndexed { index, angle ->
        val x = centerX + radius * kotlin.math.cos(angle).toFloat()
        val y = centerY + radius * kotlin.math.sin(angle).toFloat()

        if (index == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()

    drawPath(path, color)
}

fun DrawScope.drawDecorativeDots() {
    val dotColor = Color(0xFFFF4444).copy(alpha = 0.3f)
    val dotSize = 2.dp.toPx()

    // Left side dots
    for (i in 0..10) {
        for (j in 0..20) {
            if ((i + j) % 3 == 0) {
                drawCircle(
                    color = dotColor,
                    radius = dotSize,
                    center = Offset(
                        i * 8.dp.toPx(),
                        j * 15.dp.toPx()
                    )
                )
            }
        }
    }

    // Right side dots
    for (i in 0..10) {
        for (j in 0..20) {
            if ((i + j) % 3 == 0) {
                drawCircle(
                    color = dotColor,
                    radius = dotSize,
                    center = Offset(
                        size.width - i * 8.dp.toPx(),
                        j * 15.dp.toPx()
                    )
                )
            }
        }
    }
}

fun validateForm(
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    onUsernameValidation: (Boolean) -> Unit,
    onEmailValidation: (Boolean) -> Unit,
    onPasswordValidation: (Boolean) -> Unit,
    onConfirmPasswordValidation: (Boolean) -> Unit
): Boolean {
    val isUsernameValid = username.length >= 3
    val isEmailValid = isValidEmail(email)
    val isPasswordValid = password.length >= 6
    val isConfirmPasswordValid = password == confirmPassword && confirmPassword.isNotEmpty()

    onUsernameValidation(isUsernameValid)
    onEmailValidation(isEmailValid)
    onPasswordValidation(isPasswordValid)
    onConfirmPasswordValidation(isConfirmPasswordValid)

    return isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9+._%-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9-]{0,25})+"
    )
    return emailPattern.matcher(email).matches()
}



