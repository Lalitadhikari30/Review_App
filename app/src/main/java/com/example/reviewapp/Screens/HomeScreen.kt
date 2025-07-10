@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.reviewapp.Screens

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun HomeScreen(navController: NavController) {
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
            HomeHeader()
            SearchBar()
            PopularReviewsSection()
            BrowseCategoriesSection()
        }

        AppBottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter),
            onProfileClick = { navController.navigate("ProfileScreen") }
        )
    }
}


@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Reviews",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937) // gray-800
        )

//        IconButton(
//            onClick = { },
//            modifier = Modifier
//                .background(
//                    color = Color(0xFFF9FAFB), // gray-50
//                    shape = CircleShape
//                )
//                .size(40.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Settings,
//                contentDescription = "Settings",
//                tint = Color(0xFF6B7280) // gray-500
//            )
//        }
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = {
            Text(
                text = "Search for businesses",
                color = Color(0xFF9CA3AF) // gray-400
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF9CA3AF) // gray-400
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFEF4444), // red-500
            unfocusedBorderColor = Color(0xFFE5E7EB), // gray-200
            cursorColor = Color(0xFFEF4444), // red-500
            focusedTextColor = Color(0xFF1F2937), // gray-800
            unfocusedTextColor = Color(0xFF1F2937) // gray-800
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    )
}

@Composable
fun PopularReviewsSection() {
    val popularReviews = listOf(
        ReviewItem(
            id = 1,
            title = "Product Review 1",
            reviewer = "Reviewer 1",
            color = Color(0xFF4F7942), // Dark green
            imageIcon = Icons.Default.ShoppingCart
        ),
        ReviewItem(
            id = 2,
            title = "Business Review 1",
            reviewer = "Reviewer 2",
            color = Color(0xFF374151), // Dark gray
            imageIcon = Icons.Default.Business
        ),
        ReviewItem(
            id = 3,
            title = "Other Review 1",
            reviewer = "Reviewer 3",
            color = Color(0xFFEA580C), // Orange
            imageIcon = Icons.Default.Star
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Popular Reviews",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937), // gray-800
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(popularReviews) { review ->
                PopularReviewCard(review = review)
            }
        }
    }
}

@Composable
fun PopularReviewCard(review: ReviewItem) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(180.dp)
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = review.color,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = review.imageIcon,
                    contentDescription = review.title,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = review.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1F2937), // gray-800
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

//                Text(
//                    text = review.reviewer,
//                    fontSize = 12.sp,
//                    color = Color(0xFF6B7280), // gray-500
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
            }
        }
    }
}

@Composable
fun BrowseCategoriesSection() {
    val categories = listOf(
        CategoryItem("Products", Icons.Default.ShoppingBag, Color(0xFFEF4444)),
        CategoryItem("Restaurants", Icons.Default.Restaurant, Color(0xFFF59E0B)),
        CategoryItem("Services", Icons.Default.Build, Color(0xFF10B981)),
        CategoryItem("Entertainment", Icons.Default.Movie, Color(0xFF8B5CF6)),
        CategoryItem("Technology", Icons.Default.Computer, Color(0xFF06B6D4)),
        CategoryItem("Health", Icons.Default.LocalHospital, Color(0xFFEC4899)),
        CategoryItem("Education", Icons.Default.School, Color(0xFF84CC16)),
        CategoryItem("Travel", Icons.Default.Flight, Color(0xFFF97316))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        Text(
            text = "Browse Categories",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937), // gray-800
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.height(400.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category = category)
            }
        }
    }
}

@Composable
fun CategoryCard(category: com.example.reviewapp.Screens.CategoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = category.color.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = category.name,
                    tint = category.color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = category.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1F2937), // gray-800
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun AppBottomNavigation(
    modifier: Modifier = Modifier,
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
            AppBottomNavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = true,
                onClick = {}
            )
            AppBottomNavigationItem(
                icon = Icons.Default.Search,
                label = "Search",
                isSelected = false,
                onClick = {}
            )
            AppBottomNavigationItem(
                icon = Icons.Default.Favorite,
                label = "Favorites",
                isSelected = false,
                onClick = {}
            )
            AppBottomNavigationItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = false,
                onClick = onProfileClick
            )
        }
    }
}


@Composable
fun AppBottomNavigationItem(
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


data class ReviewItem(
    val id: Int,
    val title: String,
    val reviewer: String,
    val color: Color,
    val imageIcon: ImageVector
)

data class CategoryItem(
    val name: String,
    val icon: ImageVector,
    val color: Color
)