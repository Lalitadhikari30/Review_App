package com.example.reviewapp.Screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.let
import kotlin.text.isEmpty

// Function to submit review and send notification
fun submitReview(
    businessName: String,
    businessOwnerId: String,
    stars: Int,
    reviewText: String,
    titleText: String,
    mediaUri: Uri?,
    db: FirebaseFirestore,
    navController: NavController,
    onComplete: () -> Unit
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    if (currentUser == null) {
        println("Error: User not authenticated")
        onComplete()
        return
    }

    val reviewerId = currentUser.uid
    val reviewId = db.collection("reviews").document().id

    // Get reviewer's name first
    db.collection("users").document(reviewerId)
        .get()
        .addOnSuccessListener { reviewerDoc ->
            val reviewerName = reviewerDoc.getString("name") ?: "Anonymous User"

            // Create review document
            val reviewData = hashMapOf(
                "reviewId" to reviewId,
                "businessName" to businessName,
                "businessOwnerId" to businessOwnerId,
                "reviewerId" to reviewerId,
                "reviewerName" to reviewerName,
                "rating" to stars,
                "title" to titleText,
                "reviewText" to reviewText,
                "mediaUri" to (mediaUri?.toString() ?: ""),
                "timestamp" to FieldValue.serverTimestamp(),
                "isApproved" to true // You can set this to false if you want manual approval
            )

            // Save review to reviews collection
            db.collection("reviews").document(reviewId)
                .set(reviewData)
                .addOnSuccessListener {
                    println("Review saved successfully!")

                    // Send notification to business owner
                    sendReviewNotification(
                        db = db,
                        businessOwnerId = businessOwnerId,
                        reviewerName = reviewerName,
                        businessName = businessName,
                        rating = stars,
                        title = titleText
                    )

                    onComplete()
                    // Navigate back or show success message
                    navController.popBackStack()
                }
                .addOnFailureListener { exception ->
                    println("Error saving review: ${exception.message}")
                    onComplete()
                }
        }
        .addOnFailureListener { exception ->
            println("Error getting reviewer info: ${exception.message}")
            onComplete()
        }
}

// Function to send notification to business owner
fun sendReviewNotification(
    db: FirebaseFirestore,
    businessOwnerId: String,
    reviewerName: String,
    businessName: String,
    rating: Int,
    title: String
) {
    val notificationId = db.collection("notifications").document().id

    // Create notification data
    val notificationData = hashMapOf(
        "notificationId" to notificationId,
        "type" to "review_received",
        "title" to "New Review Received!",
        "message" to "$reviewerName gave your business '$businessName' a $rating-star review: \"$title\"",
        "businessName" to businessName,
        "reviewerName" to reviewerName,
        "rating" to rating,
        "reviewTitle" to title,
        "senderId" to businessOwnerId, // The business owner receives this
        "senderName" to reviewerName,
        "timestamp" to FieldValue.serverTimestamp(),
        "isRead" to false
    )

    // Add notification to business owner's notifications subcollection
    db.collection("users")
        .document(businessOwnerId)
        .collection("notifications")
        .document(notificationId)
        .set(notificationData)
        .addOnSuccessListener {
            println("Notification sent successfully to business owner!")
        }
        .addOnFailureListener { exception ->
            println("Error sending notification: ${exception.message}")
        }
}

@Composable
fun GiveReview(
    navController: NavController,
    businessName: String, // Changed from businessId to businessName to match navigation
    senderId: String
) {
    var selectedStars by remember { mutableStateOf(0) }
    var selectedMediaUri by remember { mutableStateOf<Uri?>(null) }
    var showMediaOptions by remember { mutableStateOf(false) }
    var reviewText by remember { mutableStateOf("") }
    var titleText by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) } // Add loading state for submission

    // Business details - directly use the passed businessName from navigation
    var displayBusinessName by remember { mutableStateOf(businessName) }
    var businessCategory by remember { mutableStateOf("Business") }
    var isLoading by remember { mutableStateOf(false) } // No loading needed since we have the name

    val db = FirebaseFirestore.getInstance()

    // Optional: Fetch additional business details if needed
    LaunchedEffect(senderId) {
        if (senderId.isNotEmpty()) {
            // Try to get additional business info like category from user document
            db.collection("users").document(senderId)
                .get()
                .addOnSuccessListener { userDoc ->
                    if (userDoc.exists()) {
                        // Update category if available in user document
                        businessCategory = userDoc.getString("businessCategory")
                            ?: userDoc.getString("businessType")
                                    ?: userDoc.getString("category")
                                    ?: "Business"
                    }
                }
                .addOnFailureListener { exception ->
                    println("Error fetching additional business details: ${exception.message}")
                    // Keep default values, no need to show error to user
                }
        }
    }

    val context = LocalContext.current

    // Create URI for camera capture
    val photoUri = remember {
        val photoFile = File(
            context.cacheDir,
            "photo_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
        )
        FileProvider.getUriForFile(context, "${context.packageName}.provider", photoFile)
    }

    val videoUri = remember {
        val videoFile = File(
            context.cacheDir,
            "video_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.mp4"
        )
        FileProvider.getUriForFile(context, "${context.packageName}.provider", videoFile)
    }

    // Image picker launcher (Gallery)
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedMediaUri = uri
    }

    // Video picker launcher (Gallery)
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedMediaUri = uri
    }

    // Camera photo launcher
    val cameraPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedMediaUri = photoUri
        }
    }

    // Camera video launcher
    val cameraVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { success ->
        if (success) {
            selectedMediaUri = videoUri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE57373)) // Light red background
    ) {
        // Status Bar Space
        Spacer(modifier = Modifier.height(40.dp))

        // Top Bar with Business Name
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Arrow
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Business Name Section
            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (isLoading) {
                    // Loading state
                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .fillMaxWidth(0.6f)
                            .background(
                                Color.White.copy(alpha = 0.3f),
                                RoundedCornerShape(4.dp)
                            )
                    )
                } else {
                    Text(
                        text = displayBusinessName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                if (isLoading) {
                    // Loading state for category
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(0.4f)
                            .background(
                                Color.White.copy(alpha = 0.2f),
                                RoundedCornerShape(4.dp)
                            )
                    )
                } else {
                    Text(
                        text = if (businessCategory.isNotEmpty()) businessCategory else "Business",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White.copy(alpha = 0.85f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(24.dp)
        ) {
            // Rate Business Header
            Text(
                text = "How was your experience?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = if (isLoading) "Share your thoughts..." else "Share your thoughts about $displayBusinessName",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Interactive Star Rating
            Row(
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(5) { index ->
                    Icon(
                        imageVector = if (index < selectedStars) Icons.Default.Star else Icons.Outlined.Star,
                        contentDescription = "Star ${index + 1}",
                        tint = if (index < selectedStars) Color(0xFFD32F2F) else Color.Gray,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                selectedStars = index + 1
                            }
                    )
                    if (index < 4) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            // Write a review
            Text(
                text = "Write a review",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Review Text Field
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0))
            ) {
                BasicTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    decorationBox = { innerTextField ->
                        if (reviewText.isEmpty()) {
                            Text(
                                text = "What should other customers know?",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Share a video or photo with camera icon
            Text(
                text = "Share a video or photo",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Photo/Video Upload Area with Camera Icon
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clickable {
                        showMediaOptions = true
                    },
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Camera",
                            tint = Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title your review
            Text(
                text = "Title your review (required)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Title Text Field
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0))
            ) {
                BasicTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (titleText.isEmpty()) {
                            Text(
                                text = "What's most important to know?",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Show selected media info
            selectedMediaUri?.let { uri ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E8))
                ) {
                    Text(
                        text = "Media selected: ${uri.lastPathSegment}",
                        modifier = Modifier.padding(12.dp),
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            // Submit Button
            Button(
                onClick = {
                    if (!isSubmitting) {
                        isSubmitting = true

                        // Handle submit with all form data
                        println("Business: $displayBusinessName")
                        println("Stars: $selectedStars")
                        println("Review: $reviewText")
                        println("Title: $titleText")
                        println("Media: $selectedMediaUri")

                        // Submit the review and send notification
                        submitReview(
                            businessName = displayBusinessName,
                            businessOwnerId = senderId,
                            stars = selectedStars,
                            reviewText = reviewText,
                            titleText = titleText,
                            mediaUri = selectedMediaUri,
                            db = db,
                            navController = navController,
                            onComplete = { isSubmitting = false }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(28.dp),
                enabled = titleText.isNotEmpty() && selectedStars > 0 && !isLoading && !isSubmitting
            ) {
                if (isSubmitting) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Submitting...",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Text(
                        text = "Submit Review",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    // Media Options Dialog
    if (showMediaOptions) {
        Dialog(onDismissRequest = { showMediaOptions = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Select Media",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = "Choose how you want to add media",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    // Take Photo
                    Button(
                        onClick = {
                            cameraPhotoLauncher.launch(photoUri)
                            showMediaOptions = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Take Photo")
                    }

                    // Record Video
                    Button(
                        onClick = {
                            cameraVideoLauncher.launch(videoUri)
                            showMediaOptions = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Icon(Icons.Default.Videocam, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Record Video")
                    }

                    // Choose from Gallery
                    Button(
                        onClick = {
                            imagePickerLauncher.launch("image/*")
                            showMediaOptions = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Choose Photo from Gallery")
                    }

                    Button(
                        onClick = {
                            videoPickerLauncher.launch("video/*")
                            showMediaOptions = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Choose Video from Gallery")
                    }

                    // Cancel button
                    TextButton(
                        onClick = { showMediaOptions = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cancel", color = Color.Gray)
                    }
                }
            }
        }
    }
}