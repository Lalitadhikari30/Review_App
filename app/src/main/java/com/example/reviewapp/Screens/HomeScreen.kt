//@file:OptIn(ExperimentalMaterial3Api::class)
@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.reviewapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewapp.R

data class FeaturedBusiness(
    val name: String,
    val category: String,
    val imageRes: Int?,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    // Featured businesses with exact colors from image
    val featuredBusinesses = listOf(
        FeaturedBusiness(
            "Tech Solutions Inc.",
            "Innovative software development",
            imageRes = R.drawable.tech_building, // Replace with R.drawable.tech_building when you have the image
            Color(0xFF2E5266) // Dark blue-grey from building image
        ),
        FeaturedBusiness(
            "The Daily Grind Cafe",
            "Your daily dose of caffeine",
            imageRes = R.drawable.cafe_interior, // Replace with R.drawable.cafe_interior when you have the image
            Color(0xFFD4A574) // Warm brown from cafe image
        ),
        FeaturedBusiness(
            "Le Gourmet Bistro",
            "Fine dining experience",
            imageRes = R.drawable.restaurant, // Replace with R.drawable.restaurant when you have the image
            Color(0xFF8B9A6B) // Green from restaurant image
        )
    )

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
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        IconButton(onClick = { /* Handle add click */ }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.Red
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar()
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
                    items(featuredBusinesses) { business ->
                        FeaturedBusinessCard(business)
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
fun FeaturedBusinessCard(business: FeaturedBusiness) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(110.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(business.backgroundColor)
        ) {
            // Placeholder for actual images
            if (business.imageRes != null) {
                Image(
                    painter = painterResource(id = business.imageRes),
                    contentDescription = business.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Dark overlay for text readability
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
                    text = business.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = business.category,
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
                .fillMaxWidth()
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
fun BottomNavigationBar() {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.height(65.dp)
    ) {
        // Home
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = if (selectedItem == 0) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                Text(
                    "Home",
                    fontSize = 12.sp
                )
            },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray
            )
        )

        // Search
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = if (selectedItem == 1) Icons.Filled.RateReview else Icons.Outlined.RateReview,
                    contentDescription = "MyReviews",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                Text(
                    "My Reviews",
                    fontSize = 12.sp
                )
            },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1 },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray
            )
        )


        // Profile
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = if (selectedItem == 3) Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                Text(
                    "Profile",
                    fontSize = 12.sp
                )
            },
            selected = selectedItem == 3,
            onClick = { selectedItem = 3 },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray
            )
        )
    }
}


@Composable
fun ReviewsScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}


