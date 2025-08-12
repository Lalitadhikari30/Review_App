//
//@file:OptIn(ExperimentalMaterial3Api::class)
//
//package com.example.reviewsapp
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Business
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.RateReview
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.reviewapp.R
//
//data class Review(
//    val id: Int,
//    val date: String,
//    val title: String,
//    val description: String,
//    val imageRes: Int
//)
//
//@Composable
//fun MyReviewsScreen(navController: NavController) {
//    var selectedTab by remember { mutableStateOf("given") }
//
//    val reviewsGiven = listOf(
//        Review(
//            id = 1,
//            date = "2023-08-15",
//            title = "Cozy Cabin Retreat",
//            description = "Nestled in the woods, this cabin offers a serene escape. The fireplace and rustic charm made our stay unforgettable.",
//            imageRes = R.drawable.cabin_image // Replace with actual cabin image
//        ),
//        Review(
//            id = 2,
//            date = "2023-07-22",
//            title = "Urban Loft Experience",
//            description = "A stylish loft in the heart of the city. Modern amenities and a vibrant neighborhood made for a perfect urban...",
//            imageRes = R.drawable.urban_loft_image // Replace with actual urban loft image
//        ),
//        Review(
//            id = 3,
//            date = "2023-06-10",
//            title = "Beachfront Villa",
//            description = "Waking up to the sound of waves was pure bliss. This villa's private beach access and stunning views made it a dr...",
//            imageRes = R.drawable.beach_villa_image // Replace with actual beach villa image
//        ),
//        Review(
//            id = 4,
//            date = "2023-05-05",
//            title = "Mountain View Chalet",
//            description = "The chalet's panoramic mountain views were breathtaking. A cozy retreat with hiking trails right at our doorstep.",
//            imageRes = R.drawable.mountain_chalet_image // Replace with actual mountain chalet image
//        ),
//        Review(
//            id = 5,
//            date = "2023-04-18",
//            title = "City Center Apartment",
//            description = "Perfect location in the city center. Walking distance to all major attractions and excellent public transport...",
//            imageRes = R.drawable.city_apartment_image // Replace with actual city apartment image
//        )
//    )
//
//    val reviewsReceived = listOf(
//        Review(
//            id = 1,
//            date = "2023-08-20",
//            title = "Downtown Studio",
//            description = "Great host! The studio was clean, modern, and exactly as described. Communication was excellent throughout.",
//            imageRes = R.drawable.downtown_studio_image // Replace with actual studio image
//        ),
//        Review(
//            id = 2,
//            date = "2023-07-15",
//            title = "Garden View Room",
//            description = "Lovely peaceful room with a beautiful garden view. The host was very welcoming and provided great local tips.",
//            imageRes = R.drawable.garden_room_image // Replace with actual garden room image
//        ),
//        Review(
//            id = 3,
//            date = "2023-06-28",
//            title = "Rooftop Penthouse",
//            description = "Amazing rooftop space with city views. The host was responsive and the place was spotless. Highly recommended!",
//            imageRes = R.drawable.penthouse_image // Replace with actual penthouse image
//        ),
//        Review(
//            id = 4,
//            date = "2023-05-12",
//            title = "Cozy Cottage",
//            description = "Charming cottage in a quiet neighborhood. The host went above and beyond to make us feel welcome.",
//            imageRes = R.drawable.cottage_image // Replace with actual cottage image
//        )
//    )
//
//    val currentReviews = if (selectedTab == "given") reviewsGiven else reviewsReceived
//
//    // Define colors
//    val RedPrimary = Color(0xFFDC2626)
//    val RedLight = Color(0xFFEF4444)
//    val GrayLight = Color(0xFFF3F4F6)
//    val GrayMedium = Color(0xFF6B7280)
//    val GrayDark = Color(0xFF374151)
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "My Reviews",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        color = Color.Black
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = {
//                    navController.navigate("HOMESCREEN")/* Handle back navigation */ }) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Back",
//                            tint = RedPrimary
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White
//                )
//            )
//        },
//        bottomBar = {
//            Column {
//                // Tab buttons
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.White)
//                        .padding(horizontal = 16.dp, vertical = 12.dp),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    TabButton(
//                        text = "Reviews Given",
//                        isSelected = selectedTab == "given",
//                        onClick = { selectedTab = "given" },
//                        modifier = Modifier.weight(1f)
//                    )
//                    TabButton(
//                        text = "Reviews Received",
//                        isSelected = selectedTab == "received",
//                        onClick = { selectedTab = "received" },
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//
//                // Bottom navigation
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.White)
//                        .padding(vertical = 8.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    BottomNavItem(
//                        icon = Icons.Default.Home,
//                        label = "Home",
//                        isSelected = false,
//                        onClick = {
//                        navController.navigate("HOMESCREEN")/* Handle home navigation */ }
//                    )
//                    BottomNavItem(
//                        icon = Icons.Default.Business,
//                        label = "My Business",
//                        isSelected = false,
//                        onClick = {
//                            navController.navigate("MYBUSINESSSCREEN")/* Handle home navigation */ }
//                    )
//                    BottomNavItem(
//                        icon = Icons.Default.RateReview,
//                        label = "My Reviews",
//                        isSelected = true,
//                        onClick = { /* Handle reviews navigation */ }
//                    )
//                    BottomNavItem(
//                        icon = Icons.Default.Person,
//                        label = "Profile",
//                        isSelected = false,
//                        onClick = {
//                        navController.navigate("PROFILESCREEN")/* Handle profile navigation */ }
//                    )
//                }
//            }
//        },
//        containerColor = GrayLight
//    ) { paddingValues ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(horizontal = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            contentPadding = PaddingValues(vertical = 16.dp)
//        ) {
//            items(currentReviews) { review ->
//                ReviewCard(review = review)
//            }
//        }
//    }
//}
//
//@Composable
//fun ReviewCard(review: Review) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { /* Handle card click */ },
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Text(
//                    text = review.date,
//                    fontSize = 14.sp,
//                    color = Color(0xFFDC2626),
//                    fontWeight = FontWeight.Medium
//                )
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                Text(
//                    text = review.title,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = Color.Black
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(
//                    text = review.description,
//                    fontSize = 14.sp,
//                    color = Color(0xFF6B7280),
//                    lineHeight = 20.sp,
//                    maxLines = 3,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//
//            Image(
//                painter = painterResource(id = review.imageRes),
//                contentDescription = review.title,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                contentScale = ContentScale.Crop
//            )
//        }
//    }
//}
//
//@Composable
//fun TabButton(
//    text: String,
//    isSelected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Button(
//        onClick = onClick,
//        modifier = modifier.height(48.dp),
//        shape = RoundedCornerShape(8.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = if (isSelected) Color(0xFFDC2626) else Color(0xFFF3F4F6),
//            contentColor = if (isSelected) Color.White else Color(0xFF6B7280)
//        ),
//        elevation = ButtonDefaults.buttonElevation(
//            defaultElevation = if (isSelected) 4.dp else 0.dp
//        )
//    ) {
//        Text(
//            text = text,
//            fontSize = 14.sp,
//            fontWeight = FontWeight.Medium
//        )
//    }
//}
//
//@Composable
//fun BottomNavItem(
//    icon: androidx.compose.ui.graphics.vector.ImageVector,
//    label: String,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .clickable { onClick() }
//            .padding(vertical = 8.dp)
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = label,
//            tint = if (isSelected) Color(0xFFDC2626) else Color(0xFF9CA3AF),
//            modifier = Modifier.size(20.dp)
//        )
//
//        Spacer(modifier = Modifier.height(4.dp))
//
//        Text(
//            text = label,
//            fontSize = 10.sp,
//            color = if (isSelected) Color(0xFFDC2626) else Color(0xFF9CA3AF)
//        )
//    }
//}



@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.reviewsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reviewapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

// Updated data classes based on Firebase structure
data class ReviewGiven(
    val id: String,
    val businessName: String,
    val businessOwnerId: String,
    val rating: Long,
    val reviewText: String,
    val timestamp: com.google.firebase.Timestamp,
    val reviewerName: String
)

data class ReviewReceived(
    val id: String,
    val businessName: String,
    val reviewerName: String,
    val reviewerId: String,
    val rating: Long,
    val reviewText: String,
    val timestamp: com.google.firebase.Timestamp,
    val businessOwnerId: String
)

@Composable
fun MyReviewsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("given") }
    var reviewsGiven by remember { mutableStateOf<List<ReviewGiven>>(emptyList()) }
    var reviewsReceived by remember { mutableStateOf<List<ReviewReceived>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    // Fetch reviews given by current user
    LaunchedEffect(Unit) {
        user?.uid?.let { uid ->
            // Fetch reviews given
            db.collection("reviews")
                .whereEqualTo("reviewerId", uid)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        println("Error fetching reviews given: ${error.message}")
                        return@addSnapshotListener
                    }

                    val givenReviews = snapshot?.documents?.mapNotNull { doc ->
                        try {
                            ReviewGiven(
                                id = doc.id,
                                businessName = doc.getString("businessName") ?: "",
                                businessOwnerId = doc.getString("businessOwnerId") ?: "",
                                rating = doc.getLong("rating") ?: 0L,
                                reviewText = doc.getString("reviewText") ?: "",
                                timestamp = doc.getTimestamp("timestamp") ?: com.google.firebase.Timestamp.now(),
                                reviewerName = doc.getString("reviewerName") ?: ""
                            )
                        } catch (e: Exception) {
                            println("Error parsing review given: ${e.message}")
                            null
                        }
                    } ?: emptyList()

                    reviewsGiven = givenReviews
                    isLoading = false
                }

            // Fetch reviews received for current user's businesses
            db.collection("reviews")
                .whereEqualTo("businessOwnerId", uid)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        println("Error fetching reviews received: ${error.message}")
                        return@addSnapshotListener
                    }

                    val receivedReviews = snapshot?.documents?.mapNotNull { doc ->
                        try {
                            ReviewReceived(
                                id = doc.id,
                                businessName = doc.getString("businessName") ?: "",
                                reviewerName = doc.getString("reviewerName") ?: "",
                                reviewerId = doc.getString("reviewerId") ?: "",
                                rating = doc.getLong("rating") ?: 0L,
                                reviewText = doc.getString("reviewText") ?: "",
                                timestamp = doc.getTimestamp("timestamp") ?: com.google.firebase.Timestamp.now(),
                                businessOwnerId = doc.getString("businessOwnerId") ?: ""
                            )
                        } catch (e: Exception) {
                            println("Error parsing review received: ${e.message}")
                            null
                        }
                    } ?: emptyList()

                    reviewsReceived = receivedReviews
                }
        }
    }

    // Define colors
    val RedPrimary = Color(0xFFE53935)
    val RedLight = Color(0xFFEF4444)
    val GrayLight = Color(0xFFF8F9FA)
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
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("HOMESCREEN")
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = RedPrimary
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
                        text = "Reviews Given (${reviewsGiven.size})",
                        isSelected = selectedTab == "given",
                        onClick = { selectedTab = "given" },
                        modifier = Modifier.weight(1f)
                    )
                    TabButton(
                        text = "Reviews Received (${reviewsReceived.size})",
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
                        onClick = { navController.navigate("HOMESCREEN") }
                    )
                    BottomNavItem(
                        icon = Icons.Default.Business,
                        label = "My Business",
                        isSelected = false,
                        onClick = { navController.navigate("MYBUSINESSSCREEN") }
                    )
                    BottomNavItem(
                        icon = Icons.Default.RateReview,
                        label = "My Reviews",
                        isSelected = true,
                        onClick = { }
                    )
                    BottomNavItem(
                        icon = Icons.Default.Person,
                        label = "Profile",
                        isSelected = false,
                        onClick = { navController.navigate("PROFILESCREEN") }
                    )
                }
            }
        },
        containerColor = GrayLight
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = RedPrimary,
                    modifier = Modifier.size(48.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                if (selectedTab == "given") {
                    if (reviewsGiven.isEmpty()) {
                        item {
                            EmptyStateCard("No reviews given yet", "You haven't written any reviews.")
                        }
                    } else {
                        items(reviewsGiven) { review ->
                            ReviewGivenCard(
                                review = review,
                                onClick = {
                                    // Navigate to review detail or business page
                                    navController.navigate("REVIEW_GIVEN_DETAIL/${review.id}")
                                }
                            )
                        }
                    }
                } else {
                    if (reviewsReceived.isEmpty()) {
                        item {
                            EmptyStateCard("No reviews received yet", "Your business hasn't received any reviews.")
                        }
                    } else {
                        items(reviewsReceived) { receivedReview ->
                            ReviewReceivedCard(
                                receivedReview = receivedReview,
                                onClick = {
                                    navController.navigate("REVIEW_RECEIVED_DETAIL/${receivedReview.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.RateReview,
                contentDescription = null,
                tint = Color(0xFF9CA3AF),
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ReviewGivenCard(
    review: ReviewGiven,
    onClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(review.timestamp.toDate())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with date and business name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedDate,
                    fontSize = 14.sp,
                    color = Color(0xFFE53935),
                    fontWeight = FontWeight.Medium
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFB000),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = review.rating.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF374151)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Business name
            Text(
                text = review.businessName,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Review text preview
            Text(
                text = review.reviewText,
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Rating stars
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(5) { index ->
                    Icon(
                        imageVector = if (index < review.rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Star",
                        tint = if (index < review.rating) Color(0xFFFFB000) else Color(0xFFE5E7EB),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewReceivedCard(
    receivedReview: ReviewReceived,
    onClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(receivedReview.timestamp.toDate())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with date and business name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedDate,
                    fontSize = 14.sp,
                    color = Color(0xFF9C27B0),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = receivedReview.businessName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Reviewer info and rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Reviewer avatar (placeholder circle)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF9C27B0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = receivedReview.reviewerName.take(1).uppercase(),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = receivedReview.reviewerName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }

                // Star rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < receivedReview.rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Star",
                            tint = if (index < receivedReview.rating) Color(0xFFFFB000) else Color(0xFFE5E7EB),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = receivedReview.rating.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF374151),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Review description
            Text(
                text = receivedReview.reviewText,
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Review Detail Screens
@Composable
fun ReviewGivenDetailScreen(
    navController: NavController,
    reviewId: String
) {
    var review by remember { mutableStateOf<ReviewGiven?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(reviewId) {
        db.collection("reviews")
            .document(reviewId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    review = ReviewGiven(
                        id = document.id,
                        businessName = document.getString("businessName") ?: "",
                        businessOwnerId = document.getString("businessOwnerId") ?: "",
                        rating = document.getLong("rating") ?: 0L,
                        reviewText = document.getString("reviewText") ?: "",
                        timestamp = document.getTimestamp("timestamp") ?: com.google.firebase.Timestamp.now(),
                        reviewerName = document.getString("reviewerName") ?: ""
                    )
                }
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    ReviewDetailContent(
        navController = navController,
        title = "Review Details",
        isLoading = isLoading
    ) {
        review?.let { reviewData ->
            ReviewGivenDetailContent(reviewData)
        }
    }
}

@Composable
fun ReviewReceivedDetailScreen(
    navController: NavController,
    reviewId: String
) {
    var review by remember { mutableStateOf<ReviewReceived?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(reviewId) {
        db.collection("reviews")
            .document(reviewId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    review = ReviewReceived(
                        id = document.id,
                        businessName = document.getString("businessName") ?: "",
                        reviewerName = document.getString("reviewerName") ?: "",
                        reviewerId = document.getString("reviewerId") ?: "",
                        rating = document.getLong("rating") ?: 0L,
                        reviewText = document.getString("reviewText") ?: "",
                        timestamp = document.getTimestamp("timestamp") ?: com.google.firebase.Timestamp.now(),
                        businessOwnerId = document.getString("businessOwnerId") ?: ""
                    )
                }
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    ReviewDetailContent(
        navController = navController,
        title = "Review Details",
        isLoading = isLoading
    ) {
        review?.let { reviewData ->
            ReviewReceivedDetailContent(reviewData)
        }
    }
}

@Composable
fun ReviewDetailContent(
    navController: NavController,
    title: String,
    isLoading: Boolean,
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE53935)
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFE53935))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun ReviewGivenDetailContent(review: ReviewGiven) {
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    val formattedDate = dateFormat.format(review.timestamp.toDate())

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Business name
            Text(
                text = review.businessName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Review given on $formattedDate",
                fontSize = 14.sp,
                color = Color(0xFFE53935),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Rating section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < review.rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Star",
                            tint = if (index < review.rating) Color(0xFFFFB000) else Color(0xFFE5E7EB),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Text(
                    text = "${review.rating} out of 5 stars",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF374151)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Review text
            Text(
                text = "Your Review",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.reviewText,
                fontSize = 16.sp,
                color = Color(0xFF374151),
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun ReviewReceivedDetailContent(review: ReviewReceived) {
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    val formattedDate = dateFormat.format(review.timestamp.toDate())

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Business name and date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.businessName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Text(
                text = "Review received on $formattedDate",
                fontSize = 14.sp,
                color = Color(0xFF9C27B0),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Reviewer section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF9C27B0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = review.reviewerName.take(1).uppercase(),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column {
                    Text(
                        text = "Review by",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                    Text(
                        text = review.reviewerName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Rating section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < review.rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Star",
                            tint = if (index < review.rating) Color(0xFFFFB000) else Color(0xFFE5E7EB),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Text(
                    text = "${review.rating} out of 5 stars",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF374151)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Review text
            Text(
                text = "Review",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.reviewText,
                fontSize = 16.sp,
                color = Color(0xFF374151),
                lineHeight = 24.sp
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
            containerColor = if (isSelected) Color(0xFFE53935) else Color(0xFFF3F4F6),
            contentColor = if (isSelected) Color.White else Color(0xFF6B7280)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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
            tint = if (isSelected) Color(0xFFE53935) else Color(0xFF9CA3AF),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) Color(0xFFE53935) else Color(0xFF9CA3AF)
        )
    }
}