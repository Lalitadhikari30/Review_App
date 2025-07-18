//
//package com.example.reviewapp
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.RateReview
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import kotlin.text.contains
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//
//// Data classes
//data class Review(
//    val id: String,
//    val date: String,
//    val title: String,
//    val description: String,
//    val imageRes: Int // In real app, this would be a URL
//)
//
//data class BottomNavItem(
//    val label: String,
//    val icon: ImageVector,
//    val isSelected: Boolean = false
//)
//
//// Sample data
//val sampleReviews = listOf(
//    Review(
//        id = "1",
//        date = "2023-08-15",
//        title = "Cozy Cabin Retreat",
//        description = "Nestled in the woods, this cabin offers a serene escape. The fireplace and rustic charm made our stay unforgettable.",
//        imageRes = R.drawable.cabinwoods // Placeholder
//    ),
//    Review(
//        id = "2",
//        date = "2023-07-22",
//        title = "Urban Loft Experience",
//        description = "A stylish loft in the heart of the city. Modern amenities and a vibrant neighborhood made for a perfect urban getaway.",
//        imageRes = R.drawable.lavishhotel // Placeholder
//    ),
//    Review(
//        id = "3",
//        date = "2023-06-10",
//        title = "Beachfront Villa",
//        description = "Waking up to the sound of waves was pure bliss. This villa's private beach access and stunning views made it a dream vacation.",
//        imageRes = R.drawable.villa // Placeholder
//    ),
//    Review(
//        id = "4",
//        date = "2023-05-05",
//        title = "Mountain View Chalet",
//        description = "The chalet's panoramic mountain views were breathtaking. A cozy retreat with hiking trails right at our doorstep.",
//        imageRes = R.drawable.mountainviewchalet // Placeholder
//    ),
//    Review(
//        id = "5",
//        date = "2023-04-18",
//        title = "City Center Apartment",
//        description = "Perfectly located for exploring the city. This apartment was comfortable and had everything we needed for a city break.",
//        imageRes = R.drawable.citycenterapartment // Placeholder
//    )
//)
//
//@OptIn(ExperimentalMaterial3Api::class)
////@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MyReviewsScreen(navController: NavController) {
//    var currentScreen by remember { mutableStateOf("reviews") }
//
//    when (currentScreen) {
//        "reviews" -> ReviewsScreen(
//            onNavigateToHome = { navController.navigate("HOMESCREEN") },
//            onNavigateToProfile = { navController.navigate("PROFILESCREEN") }
//        )
//        "home" -> HomeScreen(
//            onNavigateToReviews = { currentScreen = "reviews" },
//            onNavigateToProfile = { currentScreen = "profile" }
//        )
//        "profile" -> ProfileScreen(
//            onNavigateToHome = { currentScreen = "home" },
//            onNavigateToReviews = { currentScreen = "reviews" }
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ReviewsScreen(
//    onNavigateToHome: () -> Unit,
//    onNavigateToProfile: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 80.dp)
//        ) {
//            // Top App Bar
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "My Reviews",
//                        color = Color.Black,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = { /* Handle back navigation */ }) {
//                        Icon(
//                            Icons.Default.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color.Red
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White
//                )
//            )
//
//            // Reviews List
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFFF5F5F5)),
//                contentPadding = PaddingValues(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                items(sampleReviews) { review ->
//                    ReviewCard(review = review)
//                }
//            }
//        }
//
//        // Bottom Navigation (ProfileScreen style)
//        BottomNavigation(
//            modifier = Modifier.align(Alignment.BottomCenter),
//            selectedTab = "reviews",
//            onHomeClick = onNavigateToHome,
//            onReviewsClick = { },
//            onProfileClick = onNavigateToProfile
//        )
//    }
//}
//
//@Composable
//fun ReviewCard(review: Review) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { /* Handle review click */ },
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            // Left side content
//            Column(
//                modifier = Modifier.weight(1f),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Text(
//                    text = review.date,
//                    fontSize = 12.sp,
//                    color = Color.Red,
//                    fontWeight = FontWeight.Normal
//                )
//
//                Text(
//                    text = review.title,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = Color.Black
//                )
//
//                Text(
//                    text = review.description,
//                    fontSize = 13.sp,
//                    color = Color.Gray,
//                    lineHeight = 18.sp,
//                    maxLines = 3,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//
//            // Right side image
//            Box(
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(getReviewBackgroundColor(review.id))
//            ) {
//                // Placeholder for image - in real app, use AsyncImage or similar
//                Image(
//                    painter = painterResource(id = review.imageRes),
//                    contentDescription = review.title,
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                )
//            }
//        }
//    }
//}
//
//// Updated Bottom Navigation to match ProfileScreen style
//@Composable
//fun BottomNavigation(
//    modifier: Modifier = Modifier,
//    selectedTab: String,
//    onHomeClick: () -> Unit,
//    onReviewsClick: () -> Unit,
//    onProfileClick: () -> Unit
//) {
//    Card(
//        modifier = modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//        shape = RoundedCornerShape(0.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            BottomNavItem(
//                icon = Icons.Default.Home,
//                label = "Home",
//                isSelected = selectedTab == "home",
//                onClick = onHomeClick
//            )
//
//            BottomNavItem(
//                icon = Icons.Default.RateReview,
//                label = "My Reviews",
//                isSelected = selectedTab == "reviews",
//                onClick = onReviewsClick
//            )
//
//            BottomNavItem(
//                icon = Icons.Default.Person,
//                label = "Profile",
//                isSelected = selectedTab == "profile",
//                onClick = onProfileClick
//            )
//        }
//    }
//}
//
//@Composable
//fun BottomNavItem(
//    icon: ImageVector,
//    label: String,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.clickable { onClick() }
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = label,
//            tint = if (isSelected) Color(0xFFEF4444) else Color(0xFF9CA3AF), // red-500 or gray-400
//            modifier = Modifier.size(20.dp)
//        )
//
//        Spacer(modifier = Modifier.height(4.dp))
//
//        Text(
//            text = label,
//            fontSize = 10.sp,
//            color = if (isSelected) Color(0xFFEF4444) else Color(0xFF6B7280) // red-500 or gray-500
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(
//    onNavigateToReviews: () -> Unit,
//    onNavigateToProfile: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 80.dp)
//        ) {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Home",
//                        color = Color.Black,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White
//                )
//            )
//
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFFF5F5F5)),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Home Screen",
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                )
//            }
//        }
//
//        BottomNavigation(
//            modifier = Modifier.align(Alignment.BottomCenter),
//            selectedTab = "home",
//            onHomeClick = { },
//            onReviewsClick = onNavigateToReviews,
//            onProfileClick = onNavigateToProfile
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileScreen(
//    onNavigateToHome: () -> Unit,
//    onNavigateToReviews: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 80.dp)
//        ) {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Profile",
//                        color = Color.Black,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White
//                )
//            )
//
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFFF5F5F5)),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Profile Screen",
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                )
//            }
//        }
//
//        BottomNavigation(
//            modifier = Modifier.align(Alignment.BottomCenter),
//            selectedTab = "profile",
//            onHomeClick = onNavigateToHome,
//            onReviewsClick = onNavigateToReviews,
//            onProfileClick = { }
//        )
//    }
//}
//
//// Helper functions
//fun getReviewBackgroundColor(reviewId: String): Color {
//    return when (reviewId) {
//        "1" -> Color(0xFF2D5A3D) // Dark green for cabin
//        "2" -> Color(0xFF4A5568) // Dark gray for urban loft
//        "3" -> Color(0xFF4A90E2) // Blue for beachfront
//        "4" -> Color(0xFF8B4513) // Brown for mountain
//        "5" -> Color(0xFF6B7280) // Gray for city apartment
//        else -> Color.Gray
//    }
//}
//
//fun getReviewIcon(title: String): ImageVector {
//    return when {
//        title.contains("Cabin") -> Icons.Default.Home
//        title.contains("Urban") || title.contains("City") -> Icons.Default.Person
//        title.contains("Beach") -> Icons.Default.Home
//        title.contains("Mountain") -> Icons.Default.Home
//        else -> Icons.Default.Home
//    }
//}
//
//// Main composable to use in your Activity
//@Composable
//fun MainApp() {
//    MaterialTheme {
//        MyReviewsScreen(rememberNavController())
//    }
//}


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
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
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
                .padding(horizontal = 16.dp),
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
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFFDC2626) else Color(0xFF9CA3AF)
        )
    }
}

// For the images, you'll need to add these drawable resources to your res/drawable folder:
// R.drawable.cabin_image - A cozy wooden cabin in the woods
// R.drawable.urban_loft_image - Modern city loft/apartment building
// R.drawable.beach_villa_image - Beachfront villa/house
// R.drawable.mountain_chalet_image - Mountain chalet with scenic views
// R.drawable.city_apartment_image - City center apartment building
// R.drawable.downtown_studio_image - Modern downtown studio
// R.drawable.garden_room_image - Room with garden view
// R.drawable.penthouse_image - Rooftop penthouse
// R.drawable.cottage_image - Cozy cottage

// Add these to your build.gradle (Module: app) dependencies:
/*
dependencies {
    implementation "androidx.compose.ui:ui:$compose_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"
    implementation "androidx.compose.material3:material3:1.1.2"
    implementation "androidx.activity:activity-compose:1.8.0"
    implementation "androidx.compose.material:material-icons-extended:$compose_version"
}
*/