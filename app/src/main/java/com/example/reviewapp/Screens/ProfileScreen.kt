package com.example.reviewapp.Screens

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

@Preview
@Composable
fun ProfileScreen() {
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

            // Contact Section
            ContactSection()

            // Testimonials Section
            TestimonialsSection()
        }

        // Bottom Navigation
        BottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter)
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
        // Settings/Edit Icon
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile",
                tint = Color(0xFFEF4444) // red-500
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(32.dp))

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

            // Name and Email
            Text(
                text = "The Cozy Corner",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937) // gray-800
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "CozyCorner@gmail.com",
                fontSize = 14.sp,
                color = Color(0xFF6B7280) // gray-600
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Edit Profile Button
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444) // red-500
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Text(
                    text = "Edit Profile",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ContactSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Contact",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1F2937), // gray-800
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ContactItem(
            icon = Icons.Default.Phone,
            label = "Phone",
            value = "+1(555)123-4567"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ContactItem(
            icon = Icons.Default.LocationOn,
            label = "Address",
            value = "123 Main St, Anytown, USA"
        )
    }
}

@Composable
fun ContactItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = Color(0xFFFEF2F2), // red-50
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFFEF4444), // red-500
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF6B7280) // gray-500
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1F2937) // gray-800
            )
        }
    }
}

@Composable
fun TestimonialsSection() {
    val ratingData = listOf(
        RatingItem(5, 70, 87),
        RatingItem(4, 20, 25),
        RatingItem(3, 5, 6),
        RatingItem(2, 3, 4),
        RatingItem(1, 2, 3)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF9FAFB)) // gray-50
            .padding(24.dp)
    ) {
        Text(
            text = "Testimonials",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1F2937), // gray-800
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Overall Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "4.8",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F2937) // gray-800
                        )

                        StarRating(rating = 4.8f)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "125 reviews",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280) // gray-500
                        )
                    }
                }

                // Rating Breakdown
                ratingData.forEach { item ->
                    RatingBar(
                        stars = item.stars,
                        percentage = item.percentage,
                        count = item.count
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun StarRating(rating: Float) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating.toInt()) Icons.Default.Star else Icons.Outlined.Star,
                contentDescription = "Star ${index + 1}",
                tint = if (index < rating.toInt()) Color(0xFFFBBF24) else Color(0xFFD1D5DB), // yellow-400 or gray-300
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun RatingBar(
    stars: Int,
    percentage: Int,
    count: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stars.toString(),
            fontSize = 12.sp,
            color = Color(0xFF6B7280), // gray-600
            modifier = Modifier.width(16.dp)
        )

        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            tint = Color(0xFFFBBF24), // yellow-400
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .background(
                    color = Color(0xFFE5E7EB), // gray-200
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage / 100f)
                    .background(
                        color = Color(0xFFEF4444), // red-500
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "$percentage%",
            fontSize = 12.sp,
            color = Color(0xFF6B7280), // gray-500
            modifier = Modifier.width(32.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier
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
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = false
            )

//            BottomNavItem(
//                icon = Icons.Default.Search,
//                label = "Search",
//                isSelected = false
//            )

            BottomNavItem(
                icon = Icons.Default.Favorite,
                label = "Favorites",
                isSelected = false
            )

            BottomNavItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = true
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
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

data class RatingItem(
    val stars: Int,
    val percentage: Int,
    val count: Int
)