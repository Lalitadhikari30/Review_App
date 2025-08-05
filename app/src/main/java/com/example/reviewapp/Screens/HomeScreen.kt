@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.reviewapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.reviewapp.AuthViewModel
import com.example.reviewapp.R
import com.google.firebase.firestore.FirebaseFirestore
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User


data class FeaturedBusiness(
    val name: String,
    val category: String,
    val imageRes: Int?,
    val imageUrl: String = "",
    val backgroundColor: Color
)

data class Review(
    val userName: String,
    val timeAgo: String,
    val rating: Int,
    val reviewText: String,
    val businessName: String,
    val likeCount: Int,
    val commentCount: Int,
    val userImageRes: Int?
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel : AuthViewModel) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val context = LocalContext.current
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser
    var userBusinesses by remember { mutableStateOf<List<Business>>(emptyList()) }

    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid?.let { uid ->
            firestore.collection("users")
                .document(uid)
                .collection("businesses")
                .addSnapshotListener { snapshot, _ ->
                    if (snapshot != null) {
                        userBusinesses = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(Business::class.java)
                        }
                    }
                }
        }
    }



    val reviews = listOf(
        Review(
            userName = "Ethan Carter",
            timeAgo = "2 weeks ago",
            rating = 5,
            reviewText = "Tech Solutions Inc. transformed our workflow with their cutting-edge software. Highly recommend!",
            businessName = "Tech Solutions Inc.",
            likeCount = 12,
            commentCount = 2,
            userImageRes = R.drawable.user1
        ),
        Review(
            userName = "Sophia Bennett",
            timeAgo = "1 month ago",
            rating = 4,
            reviewText = "The Daily Grind Cafe is my go-to spot for a quick coffee break. Great atmosphere.",
            businessName = "The Daily Grind Cafe",
            likeCount = 8,
            commentCount = 1,
            userImageRes = R.drawable.user2
        ),
        Review(
            userName = "Liam Davis",
            timeAgo = "2 months ago",
            rating = 5,
            reviewText = "Le Gourmet Bistro offers an exquisite culinary journey. The service is impeccable.",
            businessName = "Le Gourmet Bistro",
            likeCount = 15,
            commentCount = 3,
            userImageRes = R.drawable.user3
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Explore",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { showBottomSheet = true }) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = Color.Red
                                )
                            }
                            IconButton(onClick = { navController.navigate("NOTIFICATIONSCREEN") }) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar
                businessSearchBar()

                Spacer(modifier = Modifier.height(20.dp))

                // Featured Businesses Title
                Text(
                    text = "Featured Businesses",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Featured Businesses Row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    items(userBusinesses) { business ->
                        FeaturedBusinessCard(
                            name = business.name,
                            description = business.description,
                            imageUrl = business.imageUrl ?: "",
                            backgroundColor = Color(0xFF8B9A6B),
                            onClick = {
                                navController.navigate("MYBUSINESSSCREEN/${business.id}")
                            }
                        )
                    }


                }

                // Recent Testimonials Title
                Text(
                    text = "Recent Testimonials",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            items(reviews) { review ->
                ReviewCard(review)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Bottom Sheet Modal
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = Color.White,
            contentColor = Color.Black,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(
                            Color.Gray.copy(alpha = 0.3f),
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        ) {
            AddOptionsBottomSheet(
                navController = navController,
                onDismiss = { showBottomSheet = false },
                onAddReview = {
                    showBottomSheet = false
                    // Handle add review navigation
                },
                onAddBusiness = {
                    showBottomSheet = false
                    // Handle add business navigation
                },
                onShowAskReview = {
                    showBottomSheet = false
                }
            )
        }
    }
}

@Composable
fun AddOptionsBottomSheet(
    navController: NavController,
    onDismiss: () -> Unit,
    onAddReview: () -> Unit,
    onAddBusiness: () -> Unit,
    onShowAskReview: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Add New",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            IconButton(onClick = onDismiss) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Review Option
        AddOptionItem(
            icon = Icons.Default.Reviews,
            title = "Ask for Review",
            subtitle = "Request reviews from your customers",
//            onClick = {
//                navController.navigate("ASKFORREVIEWSCREEN")
//                onDismiss()
//            }
            onClick = {
                println("🔴 Ask for Review clicked - attempting navigation")
                try {
                    navController.navigate("ASKFORREVIEWSCREEN")
                    println("✅ Navigation successful")
                    onDismiss()
                } catch (e: Exception) {
                    println("❌ Navigation failed: ${e.message}")
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Add Business Option
        AddOptionItem(
            icon = Icons.Default.RateReview,
            title = "Give Reviews",
            subtitle = "Leave a review in just a minute!",
            onClick = {
                navController.navigate("GIVEREVIEW")
                onDismiss()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}



@Composable
fun AddOptionItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.Red,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Go",
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun businessSearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        placeholder = {
            Text(
                "Search for a business",
                color = Color.Gray,
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Red
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray.copy(alpha = 0.5f),
            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5)
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun FeaturedBusinessCard(
    name: String,
    description: String,
    imageUrl: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(110.dp)
            .clickable { onClick() }, // Corrected clickable usage
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            if (imageUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f))
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User Avatar
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8E8E8)),
                    contentAlignment = Alignment.Center
                ) {
                    if (review.userImageRes != null) {
                        Image(
                            painter = painterResource(id = review.userImageRes),
                            contentDescription = "User avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(
                            text = review.userName.first().toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = review.userName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = review.timeAgo,
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rating Stars
            Row(
                modifier = Modifier.padding(start = 52.dp)
            ) {
                repeat(5) { index ->
                    Icon(
                        imageVector = if (index < review.rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (index < review.rating) Color(0xFFFF6B6B) else Color.Gray.copy(alpha = 0.4f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Review Text
            Text(
                text = review.reviewText,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 18.sp,
                modifier = Modifier.padding(start = 52.dp, end = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Action buttons
            Row(
                modifier = Modifier.padding(start = 52.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like button
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ThumbUp,
                        contentDescription = "Like",
                        tint = Color.Red,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = review.likeCount.toString(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Comment button
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = Color.Red,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = review.commentCount.toString(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                isSelected = true,
                onClick = { /* Already on Home screen */ }
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
                isSelected = false,
                onClick = { navController.navigate("MYREVIEWSSCREEN") }
            )

            BottomNavItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = false,
                onClick = { navController.navigate("PROFILESCREEN") }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFFEF4444) else Color(0xFF9CA3AF),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) Color(0xFFEF4444) else Color(0xFF6B7280)
        )
    }
}