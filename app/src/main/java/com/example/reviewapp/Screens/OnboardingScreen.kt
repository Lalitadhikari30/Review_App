package com.example.reviewapp.Screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val icon: ImageVector
)

@Composable
fun OnboardingScreen(navController: NavController,
    onGetStarted: () -> Unit = {}
) {
    val pages = listOf(
        OnboardingPage(
            title = "List your Business",
            subtitle = "Discover products and services through authentic reviews from real users",
            icon = Icons.Default.Search
        ),
        OnboardingPage(
            title = "Ask for reviews",
            subtitle = "Help others make informed decisions by sharing your honest reviews",
            icon = Icons.Outlined.ChatBubbleOutline
        ),
        OnboardingPage(
            title = "Get Reviewed and increase your Testimonials",
            subtitle = "Quick setup, instant access to thousands of reviews and ratings",
            icon = Icons.Default.ThumbUp
        )
    )

    var currentPage by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFEBEE),
                        Color(0xFFFFCDD2)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            OnboardingHeader(navController = navController,
                currentPage = currentPage,
                totalPages = pages.size,
                onPrevious = { if (currentPage > 0) currentPage-- },
                onSkip = { currentPage = pages.size - 1 }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Main Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Illustration
                OnboardingIllustration(
                    page = pages[currentPage],
                    pageIndex = currentPage
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Title
                Text(
                    text = pages[currentPage].title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF424242),
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Subtitle
                Text(
                    text = pages[currentPage].subtitle,
                    fontSize = 16.sp,
                    color = Color(0xFF757575),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Page Indicators
                PageIndicators(
                    currentPage = currentPage,
                    totalPages = pages.size,
                    onPageClick = { currentPage = it }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Action Button
            Button(
                onClick = {
                    if (currentPage < pages.size - 1) {
                        currentPage++
                    } else {
                        onGetStarted()
                        navController.navigate("LOGIN") //Navigate to Login Screen
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (currentPage == pages.size - 1) "Get Started" else "Next",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                if (currentPage != pages.size - 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

        // Bottom Wave Decoration
        BottomWaveDecoration()
    }
}

@Composable
fun OnboardingHeader(navController: NavController,
    currentPage: Int,
    totalPages: Int,
    onPrevious: () -> Unit,
    onSkip: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (currentPage > 0) {
            IconButton(
                onClick = onPrevious,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Previous",
                    tint = Color(0xFF757575)
                )
            }
        } else {
            Spacer(modifier = Modifier.size(40.dp))
        }

        Text(
            text = "${currentPage + 1} of $totalPages",
            fontSize = 14.sp,
            color = Color(0xFF757575),
            fontWeight = FontWeight.Medium
        )

        if (currentPage < totalPages - 1) {
            Text(
                text = "Skip",
                fontSize = 14.sp,
                color = Color(0xFFE53935),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { onSkip()
                        navController.navigate("LOGIN")}
                    .padding(8.dp)
            )
        } else {
            Spacer(modifier = Modifier.size(40.dp))
        }
    }
}
@Composable
fun OnboardingIllustration(
    page: OnboardingPage,
    pageIndex: Int
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(600)
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .scale(scale),
        contentAlignment = Alignment.Center
    ) {
        when (pageIndex) {
            0 -> SearchIllustration()
            1 -> ReviewIllustration()
            2 -> QuickStartIllustration()
        }
    }
}

@Composable
fun SearchIllustration() {
    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background circles
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFFFFCDD2).copy(alpha = 0.2f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color(0xFFEF5350).copy(alpha = 0.3f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFFE53935).copy(alpha = 0.4f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color(0xFFD32F2F), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        // Floating elements
        Box(
            modifier = Modifier
                .offset(x = 60.dp, y = (-60).dp)
                .size(24.dp)
                .background(Color(0xFFFFC107), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }

        Box(
            modifier = Modifier
                .offset(x = (-60).dp, y = 60.dp)
                .size(20.dp)
                .background(Color(0xFF4CAF50), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(10.dp)
            )
        }
    }
}

@Composable
fun ReviewIllustration() {
    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background card (rotated)
        Box(
            modifier = Modifier
                .size(160.dp)
                .rotate(3f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFE53935), Color(0xFFD32F2F))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )

        // Main card
        Card(
            modifier = Modifier.size(150.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // User avatar and name placeholder
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFFE53935), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "U",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .background(Color(0xFFE0E0E0), RoundedCornerShape(4.dp))
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Star rating
                Row {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (index < 4) Color(0xFFFFC107) else Color(0xFFE0E0E0),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Review text placeholders
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(3.dp))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(6.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(3.dp))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(6.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(3.dp))
                )
            }
        }
    }
}

@Composable
fun QuickStartIllustration() {
    var bounceState by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            bounceState = !bounceState
        }
    }

    val bounceOffset by animateFloatAsState(
        targetValue = if (bounceState) -10f else 0f,
        animationSpec = tween(600)
    )

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Main circle
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFE53935), Color(0xFFD32F2F))
                    ),
                    shape = CircleShape
                )
        )

        // Inner white circle
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "5",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE53935)
                )
                Text(
                    text = "mins",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }
        }

        // Floating thumbs up
        Box(
            modifier = Modifier
                .offset(y = bounceOffset.dp)
                .size(36.dp)
                .background(Color(0xFF4CAF50), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun PageIndicators(
    currentPage: Int,
    totalPages: Int,
    onPageClick: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            val isActive = index == currentPage
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(if (isActive) 32.dp else 12.dp)
                    .background(
                        color = if (isActive) Color(0xFFE53935) else Color(0xFFBDBDBD),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable { onPageClick(index) }
            )
        }
    }
}

@Composable
fun BottomWaveDecoration() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFE53935).copy(alpha = 0.1f),
                        Color(0xFFD32F2F).copy(alpha = 0.2f),
                        Color(0xFFB71C1C).copy(alpha = 0.1f)
                    )
                )
            )
    )
}
//
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(rememberNavController())
}