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
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.displayName ?: "No Name"


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light gray background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
                .padding(top = 40.dp, bottom = 80.dp) // Added top padding for status bar
        ) {

            // Back Button (separate from card)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
//                        .background(
//                            Color.White,
//                            RoundedCornerShape(8.dp)
//                        )
                        .clickable {
                            navController.popBackStack() // Navigate back
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Changed to back arrow
                        contentDescription = "Back",
                        tint = Color(0xFFEF4444), // Red color
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Enhanced Header Section with ID Card Style
            EnhancedProfileHeader()

            Spacer(modifier = Modifier.height(24.dp))

            // Menu Options Section
            EnhancedMenuOptionsSection(navController)
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
fun EnhancedProfileHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp), // Reduced horizontal padding
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEF4444) // Red theme maintained
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFEF4444), // red-500
                            Color(0xFFDC2626)  // red-600
                        )
                    )
                )
                .padding(16.dp) // Reduced padding inside the card
        ) {
            // App branding (top right)
            Text(
                text = "ReviewApp iD",
                color = Color.White,
                fontSize = 14.sp, // Reduced font size
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp) // Reduced top padding since we removed the arrow
            ) {
                // Profile Picture with border
                Box(
                    modifier = Modifier.size(90.dp), // Reduced size
                    contentAlignment = Alignment.Center
                ) {
                    // Outer white border
                    Box(
                        modifier = Modifier
                            .size(90.dp) // Reduced size
                            .background(Color.White, CircleShape)
                    )
                    // Inner profile image area
                    Box(
                        modifier = Modifier
                            .size(82.dp) // Reduced size
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
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            tint = Color(0xFFDC2626), // red-600
                            modifier = Modifier.size(40.dp) // Reduced size
                        )
                    }

                    // Edit icon (bottom right of profile picture)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(22.dp) // Reduced size
                            .background(Color(0xFF333333), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Verification Badge
                Surface(
                    modifier = Modifier,
                    color = Color(0xFF4CAF50), // Green verification badge
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Verified",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Name
//                Text(
//                    text = "Jonathan Smith",
                val currentUser = remember { FirebaseAuth.getInstance().currentUser }
                val displayName = currentUser?.displayName ?: "Guest User"
                Text(
                    text = displayName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Institution/Role
                Text(
                    text = "Review App User",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Verification validity
                Text(
                    text = "Verified until December 2025",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Decorative elements (bottom right)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(50.dp) // Reduced size
                    .background(
                        Color.White.copy(alpha = 0.1f),
                        RoundedCornerShape(topStart = 25.dp)
                    )
            )
        }
    }
}

@Composable
fun EnhancedMenuOptionsSection(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            EnhancedMenuOption(
                icon = Icons.Default.Person,
                title = "Edit Profile",
                onClick = {
                    navController.navigate("EDITPROFILESCREEN")
                }
            )

            Divider(
                color = Color.Gray.copy(alpha = 0.2f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            EnhancedMenuOption(
                icon = Icons.Default.Settings,
                title = "Settings",
                onClick = {
                    navController.navigate("SETTINGSSCREEN")
                }
            )

            Divider(
                color = Color.Gray.copy(alpha = 0.2f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            EnhancedMenuOption(
                icon = Icons.Default.Help,
                title = "Help center",
                onClick = {
                    navController.navigate("HELPCENTERSCREEN")
                }
            )

            Divider(
                color = Color.Gray.copy(alpha = 0.2f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            EnhancedMenuOption(
                icon = Icons.Default.Description,
                title = "About",
                onClick = { /* Handle Legal navigation */ }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // App version info
    Text(
        text = "App Version 1.0.0.1",
        fontSize = 12.sp,
        color = Color.Gray,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Logout Button
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = {
            navController.navigate("LOGIN")
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Log out",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFEF4444) // red-500
            )
        }
    }
}

@Composable
fun EnhancedMenuOption(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color(0xFF333333),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Arrow",
            tint = Color(0xFF9CA3AF),
            modifier = Modifier.size(16.dp)
        )
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