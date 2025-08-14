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
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
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
import com.google.firebase.firestore.ListenerRegistration
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
    var expandedReviewIds by remember { mutableStateOf(setOf<String>()) }

    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    // Store listeners to properly dispose them
    var givenReviewsListener by remember { mutableStateOf<ListenerRegistration?>(null) }
    var receivedReviewsListener by remember { mutableStateOf<ListenerRegistration?>(null) }

    // Cleanup listeners when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            givenReviewsListener?.remove()
            receivedReviewsListener?.remove()
        }
    }

    // Fetch reviews given by current user
    LaunchedEffect(Unit) {
        user?.uid?.let { uid ->
            try {
                // Fetch reviews given
                givenReviewsListener = db.collection("reviews")
                    .whereEqualTo("reviewerId", uid)
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            println("Error fetching reviews given: ${error.message}")
                            isLoading = false
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
                receivedReviewsListener = db.collection("reviews")
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
            } catch (e: Exception) {
                println("Error setting up listeners: ${e.message}")
                isLoading = false
            }
        } ?: run {
            isLoading = false
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
                        items(
                            items = reviewsGiven,
                            key = { it.id }
                        ) { review ->
                            ReviewGivenCard(
                                review = review,
                                isExpanded = review.id in expandedReviewIds,
                                onClick = {
                                    // Safe navigation with proper null checks
                                    try {
                                        if (review.id.isNotEmpty()) {
                                            navController.navigate("REVIEW_GIVEN_DETAIL/${review.id}")
                                        }
                                    } catch (e: Exception) {
                                        println("Navigation error: ${e.message}")
                                    }
                                },
                                onExpandToggle = {
                                    expandedReviewIds = if (review.id in expandedReviewIds) {
                                        expandedReviewIds - review.id
                                    } else {
                                        expandedReviewIds + review.id
                                    }
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
                        items(
                            items = reviewsReceived,
                            key = { it.id }
                        ) { receivedReview ->
                            ReviewReceivedCard(
                                receivedReview = receivedReview,
                                isExpanded = receivedReview.id in expandedReviewIds,
                                onClick = {
                                    // Safe navigation with proper null checks
                                    try {
                                        if (receivedReview.id.isNotEmpty()) {
                                            navController.navigate("REVIEW_RECEIVED_DETAIL/${receivedReview.id}")
                                        }
                                    } catch (e: Exception) {
                                        println("Navigation error: ${e.message}")
                                    }
                                },
                                onExpandToggle = {
                                    expandedReviewIds = if (receivedReview.id in expandedReviewIds) {
                                        expandedReviewIds - receivedReview.id
                                    } else {
                                        expandedReviewIds + receivedReview.id
                                    }
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
fun ReviewGivenDetailContent(review: ReviewGiven) {
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    val formattedDate = try {
        dateFormat.format(review.timestamp.toDate())
    } catch (e: Exception) {
        "Date unavailable"
    }

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
                text = review.businessName.takeIf { it.isNotBlank() } ?: "Business name not available",
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
                text = review.reviewText.takeIf { it.isNotBlank() } ?: "No review text available",
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
    val formattedDate = try {
        dateFormat.format(review.timestamp.toDate())
    } catch (e: Exception) {
        "Date unavailable"
    }

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
                    text = review.businessName.takeIf { it.isNotBlank() } ?: "Business name not available",
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
                    val reviewerName = review.reviewerName.takeIf { it.isNotBlank() } ?: "Anonymous"
                    Text(
                        text = reviewerName.take(1).uppercase(),
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
                        text = review.reviewerName.takeIf { it.isNotBlank() } ?: "Anonymous",
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
                text = review.reviewText.takeIf { it.isNotBlank() } ?: "No review text available",
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
    isExpanded: Boolean,
    onClick: () -> Unit,
    onExpandToggle: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = try {
        dateFormat.format(review.timestamp.toDate())
    } catch (e: Exception) {
        "Date unavailable"
    }

    // Safe text handling
    val safeReviewText = review.reviewText.takeIf { it.isNotBlank() } ?: "No review text"
    val shouldShowExpandButton = safeReviewText.length > 150
    val maxLines = if (isExpanded) Int.MAX_VALUE else 3

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (shouldShowExpandButton) {
                    onExpandToggle()
                } else {
                    onClick()
                }
            },
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
                text = review.businessName.takeIf { it.isNotBlank() } ?: "Business name not available",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Review text with expand/collapse functionality
            Text(
                text = safeReviewText,
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp,
                maxLines = maxLines,
                overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis
            )

            // Show expand/collapse button if text is long
            if (shouldShowExpandButton) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onExpandToggle() },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isExpanded) "Show Less" else "Show More",
                        fontSize = 12.sp,
                        color = Color(0xFFE53935),
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Show Less" else "Show More",
                        tint = Color(0xFFE53935),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

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
    isExpanded: Boolean,
    onClick: () -> Unit,
    onExpandToggle: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = try {
        dateFormat.format(receivedReview.timestamp.toDate())
    } catch (e: Exception) {
        "Date unavailable"
    }

    // Safe text handling
    val safeReviewText = receivedReview.reviewText.takeIf { it.isNotBlank() } ?: "No review text"
    val safeReviewerName = receivedReview.reviewerName.takeIf { it.isNotBlank() } ?: "Anonymous"
    val shouldShowExpandButton = safeReviewText.length > 150
    val maxLines = if (isExpanded) Int.MAX_VALUE else 3

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (shouldShowExpandButton) {
                    onExpandToggle()
                } else {
                    onClick()
                }
            },
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

                Text(
                    text = receivedReview.businessName.takeIf { it.isNotBlank() } ?: "Business name not available",
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
                            text = safeReviewerName.take(1).uppercase(),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = safeReviewerName,
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

            // Review description with expand/collapse
            Text(
                text = safeReviewText,
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp,
                maxLines = maxLines,
                overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis
            )

            // Show expand/collapse button if text is long
            if (shouldShowExpandButton) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onExpandToggle() },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isExpanded) "Show Less" else "Show More",
                        fontSize = 12.sp,
                        color = Color(0xFF9C27B0),
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Show Less" else "Show More",
                        tint = Color(0xFF9C27B0),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
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
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(reviewId) {
        if (reviewId.isNotEmpty()) {
            try {
                db.collection("reviews")
                    .document(reviewId)
                    .get()
                    .addOnSuccessListener { document ->
                        try {
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
                            } else {
                                errorMessage = "Review not found"
                            }
                        } catch (e: Exception) {
                            errorMessage = "Error loading review: ${e.message}"
                        }
                        isLoading = false
                    }
                    .addOnFailureListener { exception ->
                        errorMessage = "Failed to load review: ${exception.message}"
                        isLoading = false
                    }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                isLoading = false
            }
        } else {
            errorMessage = "Invalid review ID"
            isLoading = false
        }
    }

    ReviewDetailContent(
        navController = navController,
        title = "Review Details",
        isLoading = isLoading,
        errorMessage = errorMessage
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
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(reviewId) {
        if (reviewId.isNotEmpty()) {
            try {
                db.collection("reviews")
                    .document(reviewId)
                    .get()
                    .addOnSuccessListener { document ->
                        try {
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
                            } else {
                                errorMessage = "Review not found"
                            }
                        } catch (e: Exception) {
                            errorMessage = "Error loading review: ${e.message}"
                        }
                        isLoading = false
                    }
                    .addOnFailureListener { exception ->
                        errorMessage = "Failed to load review: ${exception.message}"
                        isLoading = false
                    }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                isLoading = false
            }
        } else {
            errorMessage = "Invalid review ID"
            isLoading = false
        }
    }

    ReviewDetailContent(
        navController = navController,
        title = "Review Details",
        isLoading = isLoading,
        errorMessage = errorMessage
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
    errorMessage: String? = null,
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
                    IconButton(onClick = {
                        try {
                            navController.popBackStack()
                        } catch (e: Exception) {
                            println("Navigation error: ${e.message}")
                        }
                    }) {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        color = Color(0xFFE53935),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                errorMessage != null -> {
                    Card(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.RateReview,
                                contentDescription = null,
                                tint = Color(0xFF9CA3AF),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Error",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE53935)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = errorMessage,
                                fontSize = 14.sp,
                                color = Color(0xFF6B7280),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(16.dp)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}