package com.example.reviewapp.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun ProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 80.dp)
        ) {
            // Header Section
            ProfileHeader()
            MenuOptionsSection(navController)

        }

        // Bottom Navigation
        ProfileBottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter),
            onHomeClick = { navController.navigate("HOMESCREEN") },
            onReviewsClick = { navController.navigate("MYREVIEWSSCREEN") },
            onProfileClick = { /* Already on Profile, do nothing */ }
        )
    }
}

@Composable
fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFEF2F2), // red-50
                        Color(0xFFFEE2E2)  // red-100
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Picture
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFECACA), // red-200
                                Color(0xFFFCA5A5)  // red-300
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFED7AA), // orange-200
                                    Color(0xFFFECACA)  // red-200
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        tint = Color(0xFFDC2626), // red-600
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name
            Text(
                text = "Jonathan Smith",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937) // gray-800
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun MenuOptionsSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        MenuOption(
            icon = Icons.Default.Edit,
            title = "Edit Profile",
            onClick =  {
                navController.navigate("EDITPROFILESCREEN") // Navigate to Edit Profile Screen
            }
        )

//        MenuOption(
//            icon = Icons.Default.CreditCard,
//            title = "Payment Methods",
//            onClick = { }
//        )

//        MenuOption(
//            icon = Icons.Default.Favorite,
//            title = "Favourites",
//            onClick = { }
//        )

//        MenuOption(
//            icon = Icons.Default.Receipt,
//            title = "Transactions",
//            onClick = { }
//        )

        MenuOption(
            icon = Icons.Default.Help,
            title = "Help Center",
            onClick = {
                navController.navigate("HELPCENTERSCREEN")
            }
        )

        MenuOption(
            icon = Icons.Default.Settings,
            title = "Settings",
            onClick = {
                navController.navigate("SETTINGSSCREEN")
            }
        )

        MenuOption(
            icon = Icons.Default.ExitToApp,
            title = "Logout",
            onClick = { }
        )
    }
}

@Composable
fun MenuOption(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFFFEF2F2), // red-50
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFFEF4444), // red-500
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1F2937), // gray-800
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow",
                tint = Color(0xFF9CA3AF), // gray-400
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ProfileBottomNavigation(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    onReviewsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileBottomNavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = false,
                onClick = onHomeClick
            )

            ProfileBottomNavItem(
                icon = Icons.Default.RateReview,
                label = "My Reviews",
                isSelected = false,
                onClick = onReviewsClick
            )

            ProfileBottomNavItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = true,
                onClick = onProfileClick
            )
        }
    }
}

@Composable
fun ProfileBottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFFEF4444) else Color(0xFF9CA3AF), // red-500 or gray-400
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) Color(0xFFEF4444) else Color(0xFF6B7280) // red-500 or gray-500
        )
    }
}