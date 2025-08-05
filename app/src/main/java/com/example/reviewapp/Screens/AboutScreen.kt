@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.reviewapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AboutScreen(
    navController: NavController,
    onBackClick: () -> Unit = { navController.navigateUp() }
) {
    val redColor = Color(0xFFE53935)
    val backgroundColor = Color(0xFFF8F9FA)
    val cardColor = Color.White
    val textPrimary = Color(0xFF2D3748)
    val textSecondary = Color(0xFF718096)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "About",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = redColor
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // App Logo and Name Section
                AppInfoCard(
                    cardColor = cardColor,
                    redColor = redColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }

            item {
                // App Description
                AppDescriptionCard(
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }

            item {
                // Features Section
                FeaturesCard(
                    cardColor = cardColor,
                    redColor = redColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }

            item {
                // App Info Section
                AppDetailsCard(
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }

            item {
                // Contact & Support Section
                ContactCard(
                    cardColor = cardColor,
                    redColor = redColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary,
                    navController = navController
                )
            }

            item {
                // Credits Section
                CreditsCard(
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }
        }
    }
}

@Composable
fun AppInfoCard(
    cardColor: Color,
    redColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo/Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(redColor, Color(0xFFD32F2F))
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "App Logo",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // App Name
            Text(
                text = "ReviewApp",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Tagline
            Text(
                text = "Your trusted review companion",
                fontSize = 16.sp,
                color = textSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AppDescriptionCard(
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "About ReviewApp",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = textPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "ReviewApp is your go-to platform for discovering and sharing authentic reviews about local businesses. Whether you're looking for the best restaurant in town or want to share your experience with others, ReviewApp connects you with real reviews from real people.",
                fontSize = 14.sp,
                color = textSecondary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Our mission is to help you make informed decisions while helping businesses grow through genuine customer feedback.",
                fontSize = 14.sp,
                color = textSecondary,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun FeaturesCard(
    cardColor: Color,
    redColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Key Features",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = textPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            val features = listOf(
                FeatureItem(Icons.Default.Search, "Discover Businesses", "Find local businesses with authentic reviews"),
                FeatureItem(Icons.Default.RateReview, "Write Reviews", "Share your experiences and help others"),
                FeatureItem(Icons.Default.Business, "Business Listings", "List your business and connect with customers"),
                FeatureItem(Icons.Default.Notifications, "Stay Updated", "Get notified about review responses and updates")
            )

            features.forEach { feature ->
                FeatureRow(
                    feature = feature,
                    redColor = redColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
                if (feature != features.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun FeatureRow(
    feature: FeatureItem,
    redColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(redColor.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                tint = redColor,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feature.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = textPrimary
            )
            Text(
                text = feature.description,
                fontSize = 12.sp,
                color = textSecondary,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun AppDetailsCard(
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "App Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = textPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            val appDetails = listOf(
                "Version" to "2.1.4",
                "Release Date" to "January 2025",
                "Size" to "25.6 MB",
                "Compatibility" to "Android 7.0+",
                "Developer" to "ReviewApp Team"
            )

            appDetails.forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        color = textSecondary
                    )
                    Text(
                        text = value,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = textPrimary
                    )
                }
                if (appDetails.last() != (label to value)) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ContactCard(
    cardColor: Color,
    redColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    navController: NavController
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Contact & Support",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = textPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            ContactItem(
                icon = Icons.Default.Email,
                title = "Email Support",
                subtitle = "support@reviewapp.com",
                redColor = redColor,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                onClick = { /* Handle email */ }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContactItem(
                icon = Icons.Default.Language,
                title = "Website",
                subtitle = "www.reviewapp.com",
                redColor = redColor,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                onClick = { /* Handle website */ }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContactItem(
                icon = Icons.Default.Policy,
                title = "Privacy Policy",
                subtitle = "Read our privacy policy",
                redColor = redColor,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                onClick = { navController.navigate("privacy_policy") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContactItem(
                icon = Icons.Default.Description,
                title = "Terms of Service",
                subtitle = "Read our terms of service",
                redColor = redColor,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                onClick = { navController.navigate("terms_of_service") }
            )
        }
    }
}

@Composable
fun ContactItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    redColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = redColor,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = textPrimary
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = textSecondary
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Navigate",
            tint = textSecondary,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun CreditsCard(
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Made with ❤️ by ReviewApp Team",
                fontSize = 14.sp,
                color = textSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "© 2025 ReviewApp. All rights reserved.",
                fontSize = 12.sp,
                color = textSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class FeatureItem(
    val icon: ImageVector,
    val title: String,
    val description: String
)

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(navController = rememberNavController())
}