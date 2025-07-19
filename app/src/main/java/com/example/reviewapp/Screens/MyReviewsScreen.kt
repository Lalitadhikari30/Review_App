
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.reviewsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reviewapp.R

data class Review(
    val id: Int,
    val date: String,
    val title: String,
    val description: String,
    val imageRes: Int
)

@Composable
fun MyReviewsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("given") }

    val reviewsGiven = listOf(
        Review(
            id = 1,
            date = "2023-08-15",
            title = "Cozy Cabin Retreat",
            description = "Nestled in the woods, this cabin offers a serene escape. The fireplace and rustic charm made our stay unforgettable.",
            imageRes = R.drawable.cabin_image // Replace with actual cabin image
        ),
        Review(
            id = 2,
            date = "2023-07-22",
            title = "Urban Loft Experience",
            description = "A stylish loft in the heart of the city. Modern amenities and a vibrant neighborhood made for a perfect urban...",
            imageRes = R.drawable.urban_loft_image // Replace with actual urban loft image
        ),
        Review(
            id = 3,
            date = "2023-06-10",
            title = "Beachfront Villa",
            description = "Waking up to the sound of waves was pure bliss. This villa's private beach access and stunning views made it a dr...",
            imageRes = R.drawable.beach_villa_image // Replace with actual beach villa image
        ),
        Review(
            id = 4,
            date = "2023-05-05",
            title = "Mountain View Chalet",
            description = "The chalet's panoramic mountain views were breathtaking. A cozy retreat with hiking trails right at our doorstep.",
            imageRes = R.drawable.mountain_chalet_image // Replace with actual mountain chalet image
        ),
        Review(
            id = 5,
            date = "2023-04-18",
            title = "City Center Apartment",
            description = "Perfect location in the city center. Walking distance to all major attractions and excellent public transport...",
            imageRes = R.drawable.city_apartment_image // Replace with actual city apartment image
        )
    )

    val reviewsReceived = listOf(
        Review(
            id = 1,
            date = "2023-08-20",
            title = "Downtown Studio",
            description = "Great host! The studio was clean, modern, and exactly as described. Communication was excellent throughout.",
            imageRes = R.drawable.downtown_studio_image // Replace with actual studio image
        ),
        Review(
            id = 2,
            date = "2023-07-15",
            title = "Garden View Room",
            description = "Lovely peaceful room with a beautiful garden view. The host was very welcoming and provided great local tips.",
            imageRes = R.drawable.garden_room_image // Replace with actual garden room image
        ),
        Review(
            id = 3,
            date = "2023-06-28",
            title = "Rooftop Penthouse",
            description = "Amazing rooftop space with city views. The host was responsive and the place was spotless. Highly recommended!",
            imageRes = R.drawable.penthouse_image // Replace with actual penthouse image
        ),
        Review(
            id = 4,
            date = "2023-05-12",
            title = "Cozy Cottage",
            description = "Charming cottage in a quiet neighborhood. The host went above and beyond to make us feel welcome.",
            imageRes = R.drawable.cottage_image // Replace with actual cottage image
        )
    )

    val currentReviews = if (selectedTab == "given") reviewsGiven else reviewsReceived

    // Define colors
    val RedPrimary = Color(0xFFDC2626)
    val RedLight = Color(0xFFEF4444)
    val GrayLight = Color(0xFFF3F4F6)
    val GrayMedium = Color(0xFF6B7280)
    val GrayDark = Color(0xFF374151)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Reviews",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                    navController.navigate("HOMESCREEN")/* Handle back navigation */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = RedPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            Column {
                // Tab buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TabButton(
                        text = "Reviews Given",
                        isSelected = selectedTab == "given",
                        onClick = { selectedTab = "given" },
                        modifier = Modifier.weight(1f)
                    )
                    TabButton(
                        text = "Reviews Received",
                        isSelected = selectedTab == "received",
                        onClick = { selectedTab = "received" },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Bottom navigation
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding( 7.7.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    BottomNavItem(
                        icon = Icons.Default.Home,
                        label = "Home",
                        isSelected = false,
                        onClick = {
                        navController.navigate("HOMESCREEN")/* Handle home navigation */ }
                    )
                    BottomNavItem(
                        icon = Icons.Default.RateReview,
                        label = "My Reviews",
                        isSelected = true,
                        onClick = { /* Handle reviews navigation */ }
                    )
                    BottomNavItem(
                        icon = Icons.Default.Person,
                        label = "Profile",
                        isSelected = false,
                        onClick = {
                        navController.navigate("PROFILESCREEN")/* Handle profile navigation */ }
                    )
                }
            }
        },
        containerColor = GrayLight
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(currentReviews) { review ->
                ReviewCard(review = review)
            }
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle card click */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = review.date,
                    fontSize = 14.sp,
                    color = Color(0xFFDC2626),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = review.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = review.description,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    lineHeight = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Image(
                painter = painterResource(id = review.imageRes),
                contentDescription = review.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFDC2626) else Color(0xFFF3F4F6),
            contentColor = if (isSelected) Color.White else Color(0xFF6B7280)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFFDC2626) else Color(0xFF9CA3AF),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) Color(0xFFDC2626) else Color(0xFF9CA3AF)
        )
    }
}

