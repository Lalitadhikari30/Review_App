@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.review.Screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.cos
import kotlin.math.sin


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("John_doe") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8F0F0),
                        Color(0xFFFFE8E8),
                        Color(0xFFF5F5F5)
                    )
                )
            )
    ) {
        // Background decorative elements
        GeometricShapes(screenWidth, screenHeight)

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(screenHeight * 0.12f))

            // Header with geometric shapes and image
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Geometric shapes background
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawGeometricHeader(size)
                }

                // Profile image placeholder (you can replace with actual image)
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    // Replace with actual image
                    Text(
                        text = "👤",
                        fontSize = 24.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Login Title
            Text(
                text = "Login Now",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "please enter your details below to continue",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Username field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5)
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Gray
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5)
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Forgot password
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Forgot Password?",
                    color = Color(0xFFEF4444),
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("FORGOTPASSWORD")
                        // Handle forgot password
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Login button
            Button(
                onClick = {
                    isLoading = true
                    // Simulate login process
                    // Handle login logic here
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444)
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
                        text = "Login",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Register link
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account?",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Register",
                    color = Color(0xFFEF4444),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        navController.navigate("REGISTER")
                        // Handle register navigation
                    }
                )
            }
        }
    }
}



@Composable
fun GeometricShapes(screenWidth: androidx.compose.ui.unit.Dp, screenHeight: androidx.compose.ui.unit.Dp) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top left dotted pattern
        drawDottedPattern(
            center = Offset(size.width * 0.1f, size.height * 0.1f),
            color = Color(0xFFEF4444).copy(alpha = 0.6f),
            dotCount = 20
        )

        // Top right dotted pattern
        drawDottedPattern(
            center = Offset(size.width * 0.9f, size.height * 0.1f),
            color = Color(0xFFEF4444).copy(alpha = 0.6f),
            dotCount = 20
        )

        // Bottom decorative elements
        drawDottedPattern(
            center = Offset(size.width * 0.2f, size.height * 0.9f),
            color = Color(0xFFEF4444).copy(alpha = 0.4f),
            dotCount = 15
        )

        drawDottedPattern(
            center = Offset(size.width * 0.8f, size.height * 0.9f),
            color = Color(0xFFEF4444).copy(alpha = 0.4f),
            dotCount = 15
        )
    }
}

fun DrawScope.drawGeometricHeader(size: androidx.compose.ui.geometry.Size) {
    // Dark gray circle
    drawCircle(
        color = Color(0xFF4A4A4A),
        radius = size.minDimension * 0.25f,
        center = Offset(size.width * 0.35f, size.height * 0.35f)
    )

    // Light gray hexagon
    drawHexagon(
        center = Offset(size.width * 0.6f, size.height * 0.6f),
        radius = size.minDimension * 0.2f,
        color = Color(0xFFE0E0E0)
    )

    // Red hexagon
    drawHexagon(
        center = Offset(size.width * 0.4f, size.height * 0.7f),
        radius = size.minDimension * 0.15f,
        color = Color(0xFFEF4444)
    )
}

fun DrawScope.drawHexagon(center: Offset, radius: Float, color: Color) {
    val path = Path()
    for (i in 0..6) {
        val angle = i * 60.0 * Math.PI / 180.0
        val x = center.x + radius * cos(angle).toFloat()
        val y = center.y + radius * sin(angle).toFloat()
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    drawPath(path, color)
}

fun DrawScope.drawDottedPattern(center: Offset, color: Color, dotCount: Int) {
    val maxRadius = 80f
    val dots = mutableListOf<Offset>()

    for (i in 0 until dotCount) {
        val angle = (i * 360f / dotCount) * Math.PI / 180f
        val radius = (20f + (i % 4) * 15f).coerceAtMost(maxRadius)
        val x = center.x + radius * cos(angle).toFloat()
        val y = center.y + radius * sin(angle).toFloat()
        dots.add(Offset(x, y))
    }

    dots.forEach { dot ->
        if (dot.x > 0 && dot.x < size.width && dot.y > 0 && dot.y < size.height) {
            drawCircle(
                color = color,
                radius = 2f,
                center = dot
            )
        }
    }
}