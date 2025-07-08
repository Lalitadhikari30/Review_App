@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.review.Screens

import kotlinx.coroutines.launch
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import kotlinx.coroutines.delay

@Composable
fun VerifyPasswordScreen(
    onBackPressed: () -> Unit = {},
    onVerificationSuccess: (String) -> Unit = {},
    onResendCode: () -> Unit = {}
) {
    var verificationCode by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isResending by remember { mutableStateOf(false) }
    var isCodeValid by remember { mutableStateOf(true) }

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
                onClick = onBackPressed,
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
                text = "Verify Password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Email verification illustration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                EmailVerificationIllustration()
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Description text
            Text(
                text = "Enter the verification code we jet sent you",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "on you email address.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Verification code input (6 boxes)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(6) { index ->
                    CodeInputBox(
                        value = if (index < verificationCode.length) verificationCode[index].toString() else "",
                        isFocused = index == verificationCode.length,
                        isError = !isCodeValid,
                        modifier = Modifier.weight(1f)
                    )
                    if (index < 5) {
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
            }

            // Hidden text field for input handling
            TextField(
                value = verificationCode,
                onValueChange = { newValue ->
                    if (newValue.length <= 6 && newValue.all { it.isDigit() }) {
                        verificationCode = newValue
                        isCodeValid = true
                    }
                },
                modifier = Modifier
                    .size(0.dp)
                    .clip(RoundedCornerShape(0.dp)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Resend code link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Don't receive a code? ")
                        withStyle(style = SpanStyle(
                            color = Color(0xFFFF4444),
                            fontWeight = FontWeight.Medium
                        )) {
                            append("Resend")
                        }
                    },
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        if (!isResending) {
                            isResending = true
                            onResendCode()

                            // Simulate resend delay
                            kotlinx.coroutines.MainScope().launch {
                                delay(2000)
                                isResending = false
                                Toast.makeText(
                                    context,
                                    "Verification code resent!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )

                if (isResending) {
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = Color(0xFFFF4444),
                        strokeWidth = 2.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Verify button
            Button(
                onClick = {
                    if (verificationCode.length != 6) {
                        isCodeValid = false
                        Toast.makeText(
                            context,
                            "Please enter the complete 6-digit code",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    isLoading = true

                    // Simulate API call
                    kotlinx.coroutines.MainScope().launch {
                        delay(2000) // Simulate network delay
                        isLoading = false

                        // Mock verification - in real app, verify with server
                        if (verificationCode == "123456") {
                            onVerificationSuccess(verificationCode)
                            Toast.makeText(
                                context,
                                "Verification successful!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            isCodeValid = false
                            Toast.makeText(
                                context,
                                "Invalid verification code. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
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
                        text = "Verify",
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

@Composable
fun CodeInputBox(
    value: String,
    isFocused: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .background(
                color = Color.White.copy(alpha = 0.8f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = when {
                    isError -> Color(0xFFFF4444)
                    isFocused -> Color(0xFFFF4444)
                    else -> Color.Gray.copy(alpha = 0.3f)
                },
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun EmailVerificationIllustration() {
    Canvas(
        modifier = Modifier.size(180.dp)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Background circle (light gray)
        drawCircle(
            color = Color(0xFFE8E8E8),
            radius = 70.dp.toPx(),
            center = Offset(centerX, centerY)
        )

        // Main envelope (orange/yellow)
        drawEnvelope(
            centerX = centerX,
            centerY = centerY + 10.dp.toPx(),
            width = 80.dp.toPx(),
            height = 60.dp.toPx(),
            color = Color(0xFFFFAA00)
        )

        // Envelope flap
        drawEnvelopeFlap(
            centerX = centerX,
            centerY = centerY + 10.dp.toPx(),
            width = 80.dp.toPx(),
            height = 60.dp.toPx(),
            color = Color(0xFFFF8800)
        )

        // Paper airplane (orange)
        drawPaperAirplane(
            centerX = centerX - 40.dp.toPx(),
            centerY = centerY - 30.dp.toPx(),
            color = Color(0xFFFF8800)
        )

        // Chat bubble with checkmark (red)
        drawChatBubble(
            centerX = centerX + 45.dp.toPx(),
            centerY = centerY - 20.dp.toPx(),
            color = Color(0xFFFF4444)
        )

        // "@ CODE" text on envelope
        drawCodeText(
            centerX = centerX,
            centerY = centerY + 10.dp.toPx()
        )

        // Decorative dots
        drawDecorativeDots(centerX, centerY)
    }
}

fun DrawScope.drawEnvelope(centerX: Float, centerY: Float, width: Float, height: Float, color: Color) {
    val left = centerX - width / 2
    val top = centerY - height / 2
    val right = centerX + width / 2
    val bottom = centerY + height / 2

    // Envelope body
    drawRoundRect(
        color = color,
        topLeft = Offset(left, top),
        size = Size(width, height),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(8.dp.toPx())
    )
}

fun DrawScope.drawEnvelopeFlap(centerX: Float, centerY: Float, width: Float, height: Float, color: Color) {
    val path = Path()
    val left = centerX - width / 2
    val top = centerY - height / 2
    val right = centerX + width / 2

    path.moveTo(left, top)
    path.lineTo(centerX, centerY - 10.dp.toPx())
    path.lineTo(right, top)
    path.close()

    drawPath(path, color)
}

fun DrawScope.drawPaperAirplane(centerX: Float, centerY: Float, color: Color) {
    val path = Path()

    path.moveTo(centerX, centerY)
    path.lineTo(centerX - 15.dp.toPx(), centerY + 8.dp.toPx())
    path.lineTo(centerX - 8.dp.toPx(), centerY + 3.dp.toPx())
    path.lineTo(centerX - 12.dp.toPx(), centerY + 12.dp.toPx())
    path.lineTo(centerX - 6.dp.toPx(), centerY + 6.dp.toPx())
    path.lineTo(centerX + 8.dp.toPx(), centerY + 4.dp.toPx())
    path.close()

    drawPath(path, color)
}

fun DrawScope.drawChatBubble(centerX: Float, centerY: Float, color: Color) {
    // Chat bubble circle
    drawCircle(
        color = color,
        radius = 18.dp.toPx(),
        center = Offset(centerX, centerY)
    )

    // Chat bubble tail
    val path = Path()
    path.moveTo(centerX - 8.dp.toPx(), centerY + 12.dp.toPx())
    path.lineTo(centerX - 5.dp.toPx(), centerY + 20.dp.toPx())
    path.lineTo(centerX + 2.dp.toPx(), centerY + 15.dp.toPx())
    path.close()

    drawPath(path, color)

    // Checkmark inside
    val checkPath = Path()
    checkPath.moveTo(centerX - 8.dp.toPx(), centerY)
    checkPath.lineTo(centerX - 2.dp.toPx(), centerY + 6.dp.toPx())
    checkPath.lineTo(centerX + 8.dp.toPx(), centerY - 6.dp.toPx())

    drawPath(
        path = checkPath,
        color = Color.White,
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
    )
}

fun DrawScope.drawCodeText(centerX: Float, centerY: Float) {
    // "@" symbol
    drawCircle(
        color = Color(0xFF666666),
        radius = 3.dp.toPx(),
        center = Offset(centerX - 15.dp.toPx(), centerY + 5.dp.toPx())
    )

    // "CODE" text representation (simplified as rectangles)
    val letterWidth = 6.dp.toPx()
    val letterHeight = 8.dp.toPx()
    val letterSpacing = 8.dp.toPx()

    repeat(4) { index ->
        drawRoundRect(
            color = Color(0xFF666666),
            topLeft = Offset(
                centerX - 8.dp.toPx() + index * letterSpacing,
                centerY + 2.dp.toPx()
            ),
            size = Size(letterWidth, letterHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx())
        )
    }
}

fun DrawScope.drawDecorativeDots(centerX: Float, centerY: Float) {
    val positions = listOf(
        Offset(centerX - 70.dp.toPx(), centerY - 50.dp.toPx()),
        Offset(centerX + 60.dp.toPx(), centerY - 60.dp.toPx()),
        Offset(centerX - 80.dp.toPx(), centerY + 30.dp.toPx()),
        Offset(centerX + 70.dp.toPx(), centerY + 50.dp.toPx()),
        Offset(centerX - 50.dp.toPx(), centerY - 70.dp.toPx()),
        Offset(centerX + 45.dp.toPx(), centerY + 65.dp.toPx())
    )

    val colors = listOf(
        Color(0xFFFF4444), Color(0xFFFF8800), Color(0xFFCCCCCC),
        Color(0xFFFF4444), Color(0xFFFF8800), Color(0xFFCCCCCC)
    )

    positions.forEachIndexed { index, position ->
        drawCircle(
            color = colors[index],
            radius = 4.dp.toPx(),
            center = position
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyPasswordScreenPreview() {
    MaterialTheme {
        VerifyPasswordScreen()
    }
}

// Usage in your Activity/Fragment:
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourAppTheme {
                VerifyPasswordScreen(
                    onBackPressed = {
                        // Handle back navigation
                        finish() // or navigate back
                    },
                    onVerificationSuccess = { code ->
                        // Handle successful verification
                        // Navigate to next screen
                    },
                    onResendCode = {
                        // Handle resend code request
                        // Make API call to resend verification code
                    }
                )
            }
        }
    }
}
*/