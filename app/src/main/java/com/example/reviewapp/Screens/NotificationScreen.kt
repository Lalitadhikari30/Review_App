@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.reviewapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Composable
fun NotificationScreen(
    navController: NavController,
    onBackClick: () -> Unit = { navController.navigateUp() }
) {
    println("NotificationScreen NavController: $navController") // Verify navController exists
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()

    var notifications by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        user?.uid?.let { uid ->
            db.collection("users")
                .document(uid)
                .collection("notifications")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        isLoading = false
                        return@addSnapshotListener
                    }

                    val items = snapshot?.documents?.mapNotNull { it.data } ?: emptyList()
                    notifications = items
                    isLoading = false
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "Notifications",
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
                containerColor = Color(0xFFE53935)
            )
        )

        // Notifications list
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFE53935))
            }
        } else if (notifications.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No notifications yet.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(notifications) { notification ->
                    val notificationType = notification["type"]?.toString() ?: ""
                    val businessName = notification["businessName"]?.toString() ?: ""
                    val senderId = notification["senderId"]?.toString() ?: ""
                    val reviewerName = notification["reviewerName"]?.toString() ?: ""
                    val rating = notification["rating"] as? Long ?: 0
                    val title = notification["title"]?.toString() ?: ""
                    val reviewId = notification["reviewId"]?.toString() ?: ""

                    // Determine notification type if not explicitly set
                    val actualNotificationType = when {
                        notificationType.isNotEmpty() -> notificationType
                        title.contains("review request", ignoreCase = true) ||
                                title.contains("requested a review", ignoreCase = true) -> "review_request"
                        title.contains("review received", ignoreCase = true) ||
                                title.contains("gave your business", ignoreCase = true) ||
                                title.contains("reviewed your business", ignoreCase = true) -> "review_received"
                        else -> "review_request" // Default to review_request for existing notifications
                    }

                    // Determine icon based on notification type
                    val notificationIcon = when (actualNotificationType) {
                        "review_request" -> Icons.Default.RateReview
                        "review_received" -> Icons.Default.Star
                        else -> Icons.Default.Notifications
                    }

                    NotificationCard(
                        title = notification["title"].toString(),
                        message = notification["message"].toString(),
                        icon = notificationIcon,
                        notificationType = actualNotificationType,
                        onClick = {
                            println("Notification clicked! Type: $actualNotificationType, BusinessName: $businessName, SenderId: $senderId")

                            when (actualNotificationType) {
                                "review_request" -> {
                                    // Handle review request notifications
                                    if (businessName.isNotEmpty() && senderId.isNotEmpty()) {
                                        println("Attempting navigation to GIVEREVIEW/$businessName/$senderId")
                                        try {
                                            navController.navigate("GIVEREVIEW/$businessName/$senderId") {
                                                println("Navigation executed")
                                            }
                                        } catch (e: Exception) {
                                            println("Navigation error: ${e.message}")
                                        }
                                    } else {
                                        println("Navigation conditions not met for review_request")
                                    }
                                }
                                "review_received" -> {
                                    // Handle review received notifications
                                    println("Review received notification clicked")

                                    if (!reviewId.isNullOrEmpty()) {
                                        println("Navigating to ReviewsReceivedDetail for reviewId: $reviewId")
                                        navController.navigate("REVIEWS_RECEIVED_DETAIL/$reviewId")
                                    } else {
                                        println("Error: reviewId is missing for review_received notification")
                                    }

                                    // For review_received, just show a simple acknowledgment
                                    // and don't try to navigate to non-existent routes

                                    // Try to mark as read (with improved error handling)
                                    try {
                                        markNotificationAsReadSafely(db, user?.uid ?: "", notification)
                                    } catch (e: Exception) {
                                        println("Error marking notification as read: ${e.message}")
                                    }

                                    // Optional: You can add a toast or snackbar here to show acknowledgment
                                    println("Review notification acknowledged")
                                }
                                else -> {
                                    println("Unknown notification type: $actualNotificationType")
                                    // For unknown types, just mark as read and don't navigate
                                    markNotificationAsReadSafely(db, user?.uid ?: "", notification)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

// Helper function to handle fallback navigation for review_received notifications
private fun handleReviewReceivedFallback(navController: NavController, businessName: String) {
    try {
        // Navigate back to the previous screen or a safe screen that exists
        // You can replace "HOME" with any route that exists in your navigation graph
        navController.navigate("REVIEWS_RECEIVED_DETAIL") {
            println("Fallback navigation to HOME executed")
            popUpTo("REVIEWS_RECEIVED_DETAIL") { inclusive = false }
        }
    } catch (e: Exception) {
        println("Fallback navigation error: ${e.message}")
        // If even HOME doesn't work, try navigating back
        try {
            navController.navigateUp()
            println("Navigated back as fallback")
        } catch (backException: Exception) {
            println("Could not navigate back: ${backException.message}")
        }
    }
}

// Function to mark notification as read with better error handling
fun markNotificationAsReadSafely(
    db: FirebaseFirestore,
    userId: String,
    notification: Map<String, Any>
) {
    if (userId.isEmpty()) {
        println("Cannot mark notification as read - empty user ID")
        return
    }

    // Try to get notification ID from the map
    val notificationId = notification["notificationId"]?.toString()
        ?: notification["id"]?.toString()
        ?: notification["documentId"]?.toString()

    if (notificationId.isNullOrEmpty()) {
        println("Cannot mark notification as read - missing notification ID")
        return
    }

    // Check if notification is already marked as read
    val isAlreadyRead = notification["isRead"] as? Boolean ?: false
    if (isAlreadyRead) {
        println("Notification already marked as read")
        return
    }

    // Try to update the notification
    db.collection("users")
        .document(userId)
        .collection("notifications")
        .document(notificationId)
        .update(mapOf("isRead" to true))
        .addOnSuccessListener {
            println("Notification marked as read successfully")
        }
        .addOnFailureListener { exception ->
            println("Error marking notification as read: ${exception.message}")

            // If update fails due to permissions, try alternative approach
            if (exception.message?.contains("PERMISSION_DENIED") == true) {
                println("Permission denied - notification read status not updated")
                // You could store this locally or handle it differently
            }
        }
}

@Composable
fun NotificationCard(
    title: String,
    message: String,
    icon: ImageVector,
    notificationType: String = "",
    onClick: () -> Unit
) {
    // Determine card styling based on notification type
    val cardColor = when (notificationType) {
        "review_received" -> Color.White // White for review received
        "review_request" -> Color.White // White for review requests
        else -> Color.White // Default to white
    }

    val iconBackgroundColor = when (notificationType) {
        "review_received" -> Color(0xFFE53935) // Red for review received
        "review_request" -> Color(0xFFE53935) // Red for review requests
        else -> Color(0xFFE53935) // Default to red
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = message,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
            }

            // Show notification type indicator
            if (notificationType == "review_received") {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Review received",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}