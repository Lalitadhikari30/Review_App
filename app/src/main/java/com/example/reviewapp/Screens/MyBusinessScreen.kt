//@file:OptIn(ExperimentalMaterial3Api::class)
//
//package com.example.Screens
//
//import androidx.compose.material.icons.filled.SearchOff
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.ui.text.style.TextAlign
//import androidx.navigation.NavController
//import kotlinx.coroutines.delay
//
//// Custom Colors - Red Theme
//object BusinessColors {
//    val Primary = Color(0xFFDC2626) // Vibrant red
//    val Secondary = Color(0xFFB91C1C) // Darker red
//    val Background = Color(0xFFFEF2F2) // Very light red background
//    val Surface = Color(0xFFFFFFFF) // White surface
//    val OnSurface = Color(0xFF1F2937) // Dark text
//    val Accent = Color(0xFFEF4444) // Bright red accent
//    val CardBackground = Color(0xFFFEF7F7) // Light red card background
//    val Border = Color(0xFFFCA5A5) // Light red border
//    val Success = Color(0xFF10B981) // Green for success states
//    val Warning = Color(0xFFFCD34D) // Yellow for ratings
//
//    // Button colors
//    val ButtonPrimary = Color(0xFFDC2626) // Red-600
//    val ButtonSecondary = Color(0xFF374151) // Gray-700
//}
//
//data class BusinessHours(
//    val sunday: String = "Closed",
//    val monday: String = "09:00 - 20:00",
//    val tuesday: String = "09:00 - 20:00",
//    val wednesday: String = "09:00 - 20:00",
//    val thursday: String = "09:00 - 20:00",
//    val friday: String = "09:00 - 20:00",
//    val saturday: String = "09:00 - 20:00"
//)
//
//data class Business(
//    val id: Int,
//    val name: String,
//    val phone: String,
//    val email: String,
//    val website: String,
//    val category: String,
//    val location: String,
//    val hours: BusinessHours,
//    val rating: Float,
//    val reviewCount: Int,
//    val description: String,
//    val verified: Boolean,
//    val isOpen: Boolean = true // Added to track if business is currently open
//)
//
//@Composable
//fun MyBusinessScreen(navController: NavController) {
//    var businesses by remember {
//        mutableStateOf(
//            listOf(
//                Business(
//                    id = 1,
//                    name = "mrventerpris",
//                    phone = "+91 89204 12085",
//                    email = "info@printedgeindia.com",
//                    website = "https://printedgeindia.com",
//                    category = "Marketing Agency, Advertising Agency, Social Media Agency",
//                    location = "Dwarka, Sec-1, New Delhi",
//                    hours = BusinessHours(),
//                    rating = 4.5f,
//                    reviewCount = 127,
//                    description = "Professional marketing and advertising services with social media expertise.",
//                    verified = true,
//                    isOpen = true
//                ),
//                Business(
//                    id = 2,
//                    name = "Digital Solutions Hub",
//                    phone = "+91 98765 43210",
//                    email = "contact@digitalhub.com",
//                    website = "https://digitalhub.com",
//                    category = "Digital Marketing, Web Development, SEO Agency",
//                    location = "Gurgaon, Haryana",
//                    hours = BusinessHours(
//                        monday = "10:00 - 19:00",
//                        tuesday = "10:00 - 19:00",
//                        wednesday = "10:00 - 19:00",
//                        thursday = "10:00 - 19:00",
//                        friday = "10:00 - 19:00",
//                        saturday = "10:00 - 17:00"
//                    ),
//                    rating = 4.2f,
//                    reviewCount = 89,
//                    description = "Comprehensive digital solutions for modern businesses.",
//                    verified = true,
//                    isOpen = false
//                ),
//                Business(
//                    id = 3,
//                    name = "Creative Studio Pro",
//                    phone = "+91 87654 32109",
//                    email = "hello@creativestudio.com",
//                    website = "https://creativestudio.com",
//                    category = "Graphic Design, Branding, Creative Agency",
//                    location = "Mumbai, Maharashtra",
//                    hours = BusinessHours(
//                        monday = "09:30 - 18:30",
//                        tuesday = "09:30 - 18:30",
//                        wednesday = "09:30 - 18:30",
//                        thursday = "09:30 - 18:30",
//                        friday = "09:30 - 18:30",
//                        saturday = "10:00 - 16:00"
//                    ),
//                    rating = 4.8f,
//                    reviewCount = 234,
//                    description = "Award-winning creative solutions and brand identity design.",
//                    verified = false,
//                    isOpen = true
//                )
//            )
//        )
//    }
//
//    var selectedBusiness by remember { mutableStateOf<Business?>(null) }
//    var showAddBusinessDialog by remember { mutableStateOf(false) }
//    var showSearchBar by remember { mutableStateOf(false) }
//    var searchQuery by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }
//
//    val filteredBusinesses = remember(searchQuery, businesses) {
//        if (searchQuery.isBlank()) {
//            businesses
//        } else {
//            businesses.filter { business ->
//                business.name.contains(searchQuery, ignoreCase = true) ||
//                        business.category.contains(searchQuery, ignoreCase = true) ||
//                        business.location.contains(searchQuery, ignoreCase = true)
//            }
//        }
//    }
//
//    // Function to add new business with loading state
//    val addNewBusiness = { newBusiness: Business ->
//        isLoading = true
//        businesses = businesses + newBusiness
//        isLoading = false
//    }
//
//    var selectedNavIndex by remember { mutableStateOf(1) } // Set to 1 for "My Businesses" tab
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(BusinessColors.Background)
//    ) {
//        Column {
//            // Status Bar Spacer - adjusted for mobile notch
//            Spacer(modifier = Modifier.height(40.dp))
//
//            // Header
//            HeaderSection(
//                businessCount = filteredBusinesses.size,
//                totalReviews = businesses.sumOf { it.reviewCount },
//                onAddBusinessClick = { showAddBusinessDialog = true },
//                onSearchClick = {
//                    showSearchBar = !showSearchBar
//                    if (!showSearchBar) {
//                        searchQuery = ""
//                    }
//                },
//                showSearchBar = showSearchBar,
//                searchQuery = searchQuery,
//                onSearchQueryChange = { searchQuery = it }
//            )
//
//            // Search Results Info with animation
//            if (searchQuery.isNotBlank()) {
//                SearchResultsInfo(
//                    resultCount = filteredBusinesses.size,
//                    searchQuery = searchQuery,
//                    onClearSearch = {
//                        searchQuery = ""
//                        showSearchBar = false
//                    }
//                )
//            }
//
//            // Business List with loading state
//            if (isLoading) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .weight(1f),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator(color = BusinessColors.Primary)
//                }
//            } else {
//                LazyColumn(
//                    modifier = Modifier.weight(1f),
//                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 80.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(filteredBusinesses) { business ->
//                        EnhancedBusinessCard(
//                            business = business,
//                            onEditClick = { selectedBusiness = business }
//                        )
//                    }
//
//                    if (filteredBusinesses.isEmpty() && searchQuery.isNotBlank()) {
//                        item {
//                            NoResultsCard(searchQuery = searchQuery)
//                        }
//                    }
//                }
//            }
//        }
//
//        // Bottom Navigation Bar
//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
//        ) {
//            BusinessBottomNavigationBar(navController = navController)
//        }
//
//        // Business Detail Modal
//        selectedBusiness?.let { business ->
//            BusinessDetailDialog(
//                business = business,
//                onDismiss = { selectedBusiness = null }
//            )
//        }
//
//        // Add Business Dialog
//        if (showAddBusinessDialog) {
//            AddBusinessDialog(
//                onDismiss = { showAddBusinessDialog = false },
//                onAddBusiness = addNewBusiness
//            )
//        }
//    }
//}
//
//@Composable
//fun SearchResultsInfo(
//    resultCount: Int,
//    searchQuery: String,
//    onClearSearch: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(BusinessColors.Accent.copy(alpha = 0.1f))
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    Icons.Default.Search,
//                    contentDescription = null,
//                    tint = BusinessColors.Accent,
//                    modifier = Modifier.size(16.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = "Found $resultCount results for \"$searchQuery\"",
//                    fontSize = 14.sp,
//                    color = BusinessColors.OnSurface.copy(alpha = 0.8f)
//                )
//            }
//            TextButton(onClick = onClearSearch) {
//                Text(
//                    text = "Clear",
//                    color = BusinessColors.Accent,
//                    fontSize = 14.sp
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun EnhancedBusinessCard(business: Business, onEditClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onEditClick() },
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = BusinessColors.CardBackground
//        ),
//        border = BorderStroke(1.dp, BusinessColors.Border),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            // Header Row
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.Top
//            ) {
//                Row {
//                    // Profile Picture with status indicator
//                    Box {
//                        Box(
//                            modifier = Modifier
//                                .size(56.dp)
//                                .clip(CircleShape)
//                                .background(BusinessColors.Secondary)
//                                .border(2.dp, BusinessColors.Accent, CircleShape),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                Icons.Default.Business,
//                                contentDescription = null,
//                                tint = Color.White,
//                                modifier = Modifier.size(28.dp)
//                            )
//                        }
//
//                        // Open/Closed status indicator
//                        Box(
//                            modifier = Modifier
//                                .size(16.dp)
//                                .clip(CircleShape)
//                                .background(if (business.isOpen) BusinessColors.Success else BusinessColors.Primary)
//                                .align(Alignment.BottomEnd)
//                                .border(2.dp, Color.White, CircleShape)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(12.dp))
//
//                    // Business Info
//                    Column {
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(
//                                text = business.name,
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                color = BusinessColors.OnSurface
//                            )
//                            if (business.verified) {
//                                Spacer(modifier = Modifier.width(6.dp))
//                                Icon(
//                                    Icons.Default.Verified,
//                                    contentDescription = "Verified",
//                                    tint = BusinessColors.Success,
//                                    modifier = Modifier.size(16.dp)
//                                )
//                            }
//                        }
//
//                        // Status and Rating Row
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.padding(top = 4.dp)
//                        ) {
//                            // Open/Closed status
//                            Text(
//                                text = if (business.isOpen) "Open" else "Closed",
//                                fontSize = 12.sp,
//                                color = if (business.isOpen) BusinessColors.Success else BusinessColors.Primary,
//                                fontWeight = FontWeight.Medium,
//                                modifier = Modifier
//                                    .background(
//                                        if (business.isOpen) BusinessColors.Success.copy(alpha = 0.1f)
//                                        else BusinessColors.Primary.copy(alpha = 0.1f),
//                                        RoundedCornerShape(4.dp)
//                                    )
//                                    .padding(horizontal = 6.dp, vertical = 2.dp)
//                            )
//
//                            Spacer(modifier = Modifier.width(8.dp))
//
//                            // Rating
//                            Icon(
//                                Icons.Default.Star,
//                                contentDescription = null,
//                                tint = BusinessColors.Warning,
//                                modifier = Modifier.size(16.dp)
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Text(
//                                text = business.rating.toString(),
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.Medium,
//                                color = BusinessColors.Warning
//                            )
//                            Text(
//                                text = " (${business.reviewCount})",
//                                fontSize = 14.sp,
//                                color = BusinessColors.OnSurface.copy(alpha = 0.6f)
//                            )
//                        }
//                    }
//                }
//
//                IconButton(onClick = onEditClick) {
//                    Icon(
//                        Icons.Default.MoreVert,
//                        contentDescription = "More options",
//                        tint = BusinessColors.Accent,
//                        modifier = Modifier.size(20.dp)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Business Details with improved icons
//            BusinessDetailRow(Icons.Default.Category, business.category)
//            BusinessDetailRow(Icons.Default.Phone, business.phone)
//            BusinessDetailRow(Icons.Default.LocationOn, business.location)
//            BusinessDetailRow(Icons.Default.AccessTime, "Today: ${getCurrentDayHours(business.hours)}")
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Description
//            HorizontalDivider(color = BusinessColors.Border)
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(
//                text = business.description,
//                fontSize = 14.sp,
//                color = BusinessColors.OnSurface.copy(alpha = 0.7f),
//                lineHeight = 20.sp,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
//
//
//        }
//    }
//}
//
//
//
//@Composable
//fun HeaderSection(
//    businessCount: Int,
//    totalReviews: Int,
//    onAddBusinessClick: () -> Unit,
//    onSearchClick: () -> Unit,
//    showSearchBar: Boolean,
//    searchQuery: String,
//    onSearchQueryChange: (String) -> Unit
//) {
//    Column {
//        // Top Bar
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(BusinessColors.Surface)
//                .padding(horizontal = 20.dp, vertical = 16.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column {
//                    Text(
//                        text = "Business Directory",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = BusinessColors.OnSurface
//                    )
//                    Text(
//                        text = "Find & review local businesses",
//                        fontSize = 14.sp,
//                        color = BusinessColors.OnSurface.copy(alpha = 0.6f)
//                    )
//                }
//
//                IconButton(
//                    onClick = onSearchClick,
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(
//                            if (showSearchBar) BusinessColors.Primary.copy(alpha = 0.2f)
//                            else BusinessColors.Primary.copy(alpha = 0.1f),
//                            CircleShape
//                        )
//                ) {
//                    Icon(
//                        if (showSearchBar) Icons.Default.Close else Icons.Default.Search,
//                        contentDescription = if (showSearchBar) "Close Search" else "Search",
//                        tint = BusinessColors.Accent,
//                        modifier = Modifier.size(20.dp)
//                    )
//                }
//            }
//        }
//
//        // Search Bar (appears when search is clicked)
//        if (showSearchBar) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(BusinessColors.Surface)
//                    .padding(horizontal = 15.dp, vertical = 12.dp)
//            ) {
//                OutlinedTextField(
//                    value = searchQuery,
//                    onValueChange = onSearchQueryChange,
//                    placeholder = {
//                        Text(
//                            text = "Search businesses, categories.....",
//                            color = BusinessColors.OnSurface.copy(alpha = 0.5f)
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    leadingIcon = {
//                        Icon(
//                            Icons.Default.Search,
//                            contentDescription = null,
//                            tint = BusinessColors.Accent,
//                            modifier = Modifier.size(20.dp)
//                        )
//                    },
//                    trailingIcon = {
//                        if (searchQuery.isNotEmpty()) {
//                            IconButton(onClick = { onSearchQueryChange("") }) {
//                                Icon(
//                                    Icons.Default.Clear,
//                                    contentDescription = "Clear",
//                                    tint = BusinessColors.OnSurface.copy(alpha = 0.6f),
//                                    modifier = Modifier.size(18.dp)
//                                )
//                            }
//                        }
//                    },
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = BusinessColors.Primary,
//                        unfocusedBorderColor = BusinessColors.Border,
//                        focusedLeadingIconColor = BusinessColors.Primary,
//                        unfocusedLeadingIconColor = BusinessColors.Accent
//                    ),
//                    shape = RoundedCornerShape(12.dp),
//                    singleLine = true
//                )
//            }
//        }
//
//        // Stats and Action Section
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    Brush.horizontalGradient(
//                        colors = listOf(
//                            BusinessColors.Primary.copy(alpha = 0.3f),
//                            BusinessColors.Secondary.copy(alpha = 0.2f)
//                        )
//                    )
//                )
//                .padding(20.dp)
//        ) {
//            Column {
//                // Stats Cards
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    StatsCard(
//                        icon = Icons.Default.Business,
//                        value = businessCount.toString(),
//                        label = "Businesses",
//                        modifier = Modifier.weight(1f)
//                    )
//                    StatsCard(
//                        icon = Icons.Default.People,
//                        value = totalReviews.toString(),
//                        label = "Reviews",
//                        modifier = Modifier.weight(1f)
//                    )
//                    StatsCard(
//                        icon = Icons.Default.Star,
//                        value = "4.5",
//                        label = "Avg Rating",
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Add Business Button
//                OutlinedButton(
//                    onClick = onAddBusinessClick,
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = ButtonDefaults.outlinedButtonColors(
//                        containerColor = BusinessColors.Surface,
//                        contentColor = BusinessColors.ButtonPrimary
//                    ),
//                    border = BorderStroke(1.5.dp, BusinessColors.ButtonPrimary),
//                    shape = RoundedCornerShape(10.dp)
//                ) {
//                    Icon(
//                        Icons.Default.Add,
//                        contentDescription = null,
//                        modifier = Modifier.size(18.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "List Your Business",
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun StatsCard(
//    icon: ImageVector,
//    value: String,
//    label: String,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier,
//        colors = CardDefaults.cardColors(
//            containerColor = BusinessColors.Surface
//        ),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                icon,
//                contentDescription = null,
//                tint = BusinessColors.Accent,
//                modifier = Modifier.size(20.dp)
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = value,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = BusinessColors.OnSurface
//            )
//            Text(
//                text = label,
//                fontSize = 12.sp,
//                color = BusinessColors.OnSurface.copy(alpha = 0.7f)
//            )
//        }
//    }
//}
//
//@Composable
//fun BusinessDetailRow(icon: ImageVector, text: String) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.padding(vertical = 4.dp)
//    ) {
//        Icon(
//            icon,
//            contentDescription = null,
//            tint = BusinessColors.Accent,
//            modifier = Modifier.size(16.dp)
//        )
//        Spacer(modifier = Modifier.width(12.dp))
//        Text(
//            text = text,
//            fontSize = 14.sp,
//            color = BusinessColors.OnSurface.copy(alpha = 0.8f),
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
//    }
//}
//
//@Composable
//fun BusinessDetailDialog(business: Business, onDismiss: () -> Unit) {
//    Dialog(onDismissRequest = onDismiss) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .heightIn(max = 600.dp),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = BusinessColors.Surface
//            )
//        ) {
//            Column {
//                // Header
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(
//                            Brush.horizontalGradient(
//                                colors = listOf(BusinessColors.Primary, BusinessColors.Secondary)
//                            )
//                        )
//                        .padding(16.dp)
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Business Profile",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White
//                        )
//                        IconButton(onClick = onDismiss) {
//                            Icon(
//                                Icons.Default.Close,
//                                contentDescription = "Close",
//                                tint = Color.White
//                            )
//                        }
//                    }
//                }
//
//                LazyColumn(
//                    modifier = Modifier.padding(20.dp),
//                    verticalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    item {
//                        // Profile Section
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .size(80.dp)
//                                    .clip(CircleShape)
//                                    .background(BusinessColors.Secondary)
//                                    .border(2.dp, BusinessColors.Accent, CircleShape),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Icon(
//                                    Icons.Default.Business,
//                                    contentDescription = null,
//                                    tint = Color.White,
//                                    modifier = Modifier.size(40.dp)
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.height(12.dp))
//
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                Text(
//                                    text = business.name,
//                                    fontSize = 24.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = BusinessColors.OnSurface
//                                )
//                                if (business.verified) {
//                                    Spacer(modifier = Modifier.width(8.dp))
//                                    Icon(
//                                        Icons.Default.Verified,
//                                        contentDescription = "Verified",
//                                        tint = BusinessColors.Success,
//                                        modifier = Modifier.size(20.dp)
//                                    )
//                                }
//                            }
//
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                modifier = Modifier.padding(top = 8.dp)
//                            ) {
//                                Icon(
//                                    Icons.Default.Star,
//                                    contentDescription = null,
//                                    tint = BusinessColors.Warning,
//                                    modifier = Modifier.size(20.dp)
//                                )
//                                Spacer(modifier = Modifier.width(4.dp))
//                                Text(
//                                    text = business.rating.toString(),
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.Medium,
//                                    color = BusinessColors.Warning
//                                )
//                                Text(
//                                    text = " (${business.reviewCount} reviews)",
//                                    fontSize = 16.sp,
//                                    color = BusinessColors.OnSurface.copy(alpha = 0.6f)
//                                )
//                            }
//                        }
//                    }
//
//                    item {
//                        // Contact Details
//                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                            BusinessDetailRow(Icons.Default.Category, business.category)
//                            BusinessDetailRow(Icons.Default.Phone, business.phone)
//                            BusinessDetailRow(Icons.Default.LocationOn, business.location)
//                            BusinessDetailRow(Icons.Default.Email, business.email)
//                            BusinessDetailRow(Icons.Default.Language, business.website)
//                        }
//                    }
//
//                    item {
//                        // Business Hours
//                        Column {
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                Icon(
//                                    Icons.Default.AccessTime,
//                                    contentDescription = null,
//                                    tint = BusinessColors.Accent,
//                                    modifier = Modifier.size(20.dp)
//                                )
//                                Spacer(modifier = Modifier.width(8.dp))
//                                Text(
//                                    text = "Business Hours",
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.SemiBold,
//                                    color = BusinessColors.OnSurface
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.height(12.dp))
//
//                            BusinessHoursTable(business.hours)
//                        }
//                    }
//
//                    item {
//                        // Description
//                        Column {
//                            Text(
//                                text = "Business Description",
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                color = BusinessColors.OnSurface
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                text = business.description,
//                                fontSize = 14.sp,
//                                color = BusinessColors.OnSurface.copy(alpha = 0.7f),
//                                lineHeight = 20.sp
//                            )
//                        }
//                    }
//
//                    item {
//                        // Action Buttons
//                        HorizontalDivider(color = BusinessColors.Border)
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            Button(
//                                onClick = { /* Write review */ },
//                                modifier = Modifier.weight(1f),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = BusinessColors.ButtonPrimary
//                                ),
//                                shape = RoundedCornerShape(8.dp)
//                            ) {
//                                Text("Write Review")
//                            }
//
//                            Button(
//                                onClick = { /* Contact */ },
//                                modifier = Modifier.weight(1f),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = BusinessColors.ButtonSecondary
//                                ),
//                                shape = RoundedCornerShape(8.dp)
//                            ) {
//                                Text("Contact")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun BusinessHoursTable(hours: BusinessHours) {
//    val hoursMap = mapOf(
//        "Sunday" to hours.sunday,
//        "Monday" to hours.monday,
//        "Tuesday" to hours.tuesday,
//        "Wednesday" to hours.wednesday,
//        "Thursday" to hours.thursday,
//        "Friday" to hours.friday,
//        "Saturday" to hours.saturday
//    )
//
//    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//        hoursMap.forEach { (day, time) ->
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = day,
//                    fontSize = 14.sp,
//                    color = BusinessColors.OnSurface.copy(alpha = 0.8f)
//                )
//                Text(
//                    text = time,
//                    fontSize = 14.sp,
//                    color = if (time == "Closed") BusinessColors.ButtonPrimary else BusinessColors.Success,
//                    fontWeight = FontWeight.Medium
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun AddBusinessDialog(
//    onDismiss: () -> Unit,
//    onAddBusiness: (Business) -> Unit
//) {
//    var businessName by remember { mutableStateOf("") }
//    var businessPhone by remember { mutableStateOf("") }
//    var businessEmail by remember { mutableStateOf("") }
//    var businessWebsite by remember { mutableStateOf("") }
//    var businessCategory by remember { mutableStateOf("") }
//    var businessLocation by remember { mutableStateOf("") }
//    var businessDescription by remember { mutableStateOf("") }
//    var isSubmitting by remember { mutableStateOf(false) }
//
//    // Form validation
//    val isFormValid = businessName.isNotBlank() &&
//            businessPhone.isNotBlank() &&
//            businessCategory.isNotBlank() &&
//            businessLocation.isNotBlank()
//
//    Dialog(onDismissRequest = onDismiss) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .heightIn(max = 700.dp),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = BusinessColors.Surface
//            )
//        ) {
//            Column {
//                // Header
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(BusinessColors.Primary)
//                        .padding(16.dp)
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column {
//                            Text(
//                                text = "List Your Business",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.White
//                            )
//                            Text(
//                                text = "Join our business directory",
//                                fontSize = 14.sp,
//                                color = Color.White.copy(alpha = 0.8f)
//                            )
//                        }
//                        IconButton(onClick = onDismiss) {
//                            Icon(
//                                Icons.Default.Close,
//                                contentDescription = "Close",
//                                tint = Color.White
//                            )
//                        }
//                    }
//                }
//
//                LazyColumn(
//                    modifier = Modifier.padding(20.dp),
//                    verticalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    item {
//                        EnhancedTextField(
//                            value = businessName,
//                            onValueChange = { businessName = it },
//                            label = "Business Name",
//                            icon = Icons.Default.Business,
//                            isRequired = true
//                        )
//                    }
//                    item {
//                        EnhancedTextField(
//                            value = businessPhone,
//                            onValueChange = { businessPhone = it },
//                            label = "Phone Number",
//                            icon = Icons.Default.Phone,
//                            isRequired = true
//                        )
//                    }
//                    item {
//                        EnhancedTextField(
//                            value = businessEmail,
//                            onValueChange = { businessEmail = it },
//                            label = "Email Address",
//                            icon = Icons.Default.Email
//                        )
//                    }
//                    item {
//                        EnhancedTextField(
//                            value = businessWebsite,
//                            onValueChange = { businessWebsite = it },
//                            label = "Website",
//                            icon = Icons.Default.Language
//                        )
//                    }
//                    item {
//                        EnhancedTextField(
//                            value = businessCategory,
//                            onValueChange = { businessCategory = it },
//                            label = "Business Category",
//                            icon = Icons.Default.Category,
//                            isRequired = true
//                        )
//                    }
//                    item {
//                        EnhancedTextField(
//                            value = businessLocation,
//                            onValueChange = { businessLocation = it },
//                            label = "Location",
//                            icon = Icons.Default.LocationOn,
//                            isRequired = true
//                        )
//                    }
//                    item {
//                        EnhancedTextField(
//                            value = businessDescription,
//                            onValueChange = { businessDescription = it },
//                            label = "Business Description",
//                            icon = Icons.Default.Description,
//                            maxLines = 4
//                        )
//                    }
//
//                    item {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            OutlinedButton(
//                                onClick = onDismiss,
//                                modifier = Modifier.weight(1f),
//                                shape = RoundedCornerShape(10.dp),
//                                border = BorderStroke(1.2.dp, BusinessColors.Border),
//                                enabled = !isSubmitting
//                            ) {
//                                Text("Cancel")
//                            }
//
//                            Spacer(modifier = Modifier.width(12.dp))
//
//                            Button(
//                                onClick = {
//                                    if (isFormValid) {
//                                        isSubmitting = true
//                                        onAddBusiness(
//                                            Business(
//                                                id = (100000..999999).random(),
//                                                name = businessName,
//                                                phone = businessPhone,
//                                                email = businessEmail,
//                                                website = businessWebsite,
//                                                category = businessCategory,
//                                                location = businessLocation,
//                                                hours = BusinessHours(),
//                                                rating = 0f,
//                                                reviewCount = 0,
//                                                description = businessDescription.ifBlank { "New business listing" },
//                                                verified = false,
//                                                isOpen = true
//                                            )
//                                        )
//                                        onDismiss()
//                                    }
//                                },
//                                modifier = Modifier.weight(1f),
//                                shape = RoundedCornerShape(10.dp),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = BusinessColors.ButtonPrimary
//                                ),
//                                enabled = isFormValid && !isSubmitting
//                            ) {
//                                if (isSubmitting) {
//                                    CircularProgressIndicator(
//                                        modifier = Modifier.size(16.dp),
//                                        color = Color.White,
//                                        strokeWidth = 2.dp
//                                    )
//                                } else {
//                                    Text("Add Business")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun EnhancedTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    icon: ImageVector,
//    isRequired: Boolean = false,
//    maxLines: Int = 1
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(label)
//                if (isRequired) {
//                    Text(
//                        " *",
//                        color = BusinessColors.Primary,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//        },
//        leadingIcon = {
//            Icon(
//                icon,
//                contentDescription = null,
//                tint = BusinessColors.Accent,
//                modifier = Modifier.size(20.dp)
//            )
//        },
//        modifier = Modifier.fillMaxWidth(),
//        maxLines = maxLines,
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = BusinessColors.Primary,
//            unfocusedBorderColor = BusinessColors.Border,
//            focusedLeadingIconColor = BusinessColors.Primary,
//            unfocusedLeadingIconColor = BusinessColors.Accent,
//            focusedLabelColor = BusinessColors.Primary
//        ),
//        shape = RoundedCornerShape(12.dp)
//    )
//}
//
//@Composable
//fun NoResultsCard(searchQuery: String) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 16.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = BusinessColors.CardBackground
//        ),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(32.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                imageVector = Icons.Default.SearchOff,
//                contentDescription = null,
//                tint = BusinessColors.Accent.copy(alpha = 0.6f),
//                modifier = Modifier.size(64.dp)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = "No results found",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = BusinessColors.OnSurface,
//                textAlign = TextAlign.Center
//            )
//
//            Text(
//                text = "We couldn't find any businesses matching \"$searchQuery\"",
//                fontSize = 14.sp,
//                color = BusinessColors.OnSurface.copy(alpha = 0.6f),
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = "Try searching with different keywords or check your spelling",
//                fontSize = 12.sp,
//                color = BusinessColors.OnSurface.copy(alpha = 0.5f),
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}
//
//fun getCurrentDayHours(hours: BusinessHours): String {
//    val calendar = java.util.Calendar.getInstance()
//    return when (calendar.get(java.util.Calendar.DAY_OF_WEEK)) {
//        java.util.Calendar.SUNDAY -> hours.sunday
//        java.util.Calendar.MONDAY -> hours.monday
//        java.util.Calendar.TUESDAY -> hours.tuesday
//        java.util.Calendar.WEDNESDAY -> hours.wednesday
//        java.util.Calendar.THURSDAY -> hours.thursday
//        java.util.Calendar.FRIDAY -> hours.friday
//        java.util.Calendar.SATURDAY -> hours.saturday
//        else -> "Closed"
//    }
//}
//
//@Composable
//fun BusinessBottomNavigationBar(navController: NavController) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
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
//                isSelected = false,
//                onClick = { navController.navigate("HOMESCREEN") }
//            )
//            BottomNavItem(
//                icon = Icons.Default.Business,
//                label = "My Business",
//                isSelected = true,
//                onClick = { navController.navigate("MYBUSINESSSCREEN") }
//            )
//
//            BottomNavItem(
//                icon = Icons.Default.RateReview,
//                label = "My Reviews",
//                isSelected = false,
//                onClick = { navController.navigate("MYREVIEWSSCREEN") }
//            )
//
//            BottomNavItem(
//                icon = Icons.Default.Person,
//                label = "Profile",
//                isSelected = false,
//                onClick = { navController.navigate("PROFILESCREEN") }
//            )
//        }
//    }
//}
//@Composable
//private fun BottomNavItem(
//    icon: ImageVector,
//    label: String,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.clickable(onClick = onClick)
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = label,
//            tint = if (isSelected) Color(0xFFEF4444) else Color(0xFF9CA3AF),
//            modifier = Modifier.size(20.dp)
//        )
//
//        Spacer(modifier = Modifier.height(4.dp))
//
//        Text(
//            text = label,
//            fontSize = 10.sp,
//            color = if (isSelected) Color(0xFFEF4444) else Color(0xFF6B7280)
//        )
//    }
//}

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.reviewapp.Screens

import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext


object BusinessColors {
    val Primary = Color(0xFFDC2626)
    val Secondary = Color(0xFFB91C1C)
    val Background = Color(0xFFFEF2F2)
    val Surface = Color(0xFFFFFFFF)
    val OnSurface = Color(0xFF1F2937)
    val Accent = Color(0xFFEF4444)
    val CardBackground = Color(0xFFFEF7F7)
    val Border = Color(0xFFFCA5A5)
    val Success = Color(0xFF10B981)
    val Warning = Color(0xFFFCD34D)
    val ButtonPrimary = Color(0xFFDC2626)
    val ButtonSecondary = Color(0xFF374151)
}

data class BusinessHours(
    val sunday: String = "Closed",
    val monday: String = "09:00 - 20:00",
    val tuesday: String = "09:00 - 20:00",
    val wednesday: String = "09:00 - 20:00",
    val thursday: String = "09:00 - 20:00",
    val friday: String = "09:00 - 20:00",
    val saturday: String = "09:00 - 20:00"
)


data class Business(
    val id: Int = 0,
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val website: String = "",
    val category: String = "",
    val location: String = "",
    val hours: BusinessHours = BusinessHours(),
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val description: String = "",
    val verified: Boolean = false,
    val isOpen: Boolean = true
)

@Composable
fun MyBusinessScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    var businesses by remember {
        mutableStateOf(emptyList<Business>())
    }
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isLoading = true
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users")
                .document(uid)
                .collection("businesses")
                .get()
                .addOnSuccessListener { snapshot ->
                    val list = snapshot.documents.mapNotNull { it.toObject(Business::class.java) }
                    businesses = list
                    isLoading = false
                }
                .addOnFailureListener {
                    isLoading = false
                    Toast.makeText(context, "Failed to load businesses", Toast.LENGTH_SHORT).show()
                }
        } else {
            isLoading = false
        }
    }


    var selectedBusiness by remember { mutableStateOf<Business?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }

    val filteredBusinesses = remember(searchQuery, businesses) {
        if (searchQuery.isBlank()) businesses
        else businesses.filter { it.name.contains(searchQuery, true) || it.category.contains(searchQuery, true) || it.location.contains(searchQuery, true) }
    }



    Box(modifier = Modifier.fillMaxSize().background(BusinessColors.Background)) {
        Column {
            Spacer(modifier = Modifier.height(40.dp))
            HeaderSection(filteredBusinesses.size, businesses.sumOf { it.reviewCount }, { showAddDialog = true }, { showSearch = !showSearch; if (!showSearch) searchQuery = "" }, showSearch, searchQuery) { searchQuery = it }

            if (searchQuery.isNotBlank()) {
                SearchResultsInfo(filteredBusinesses.size, searchQuery) { searchQuery = ""; showSearch = false }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BusinessColors.Primary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredBusinesses) { business ->
                        EnhancedBusinessCard(business) { selectedBusiness = business }
                    }
                    if (filteredBusinesses.isEmpty() && searchQuery.isNotBlank()) {
                        item { NoResultsCard(searchQuery) }
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()) {
            BusinessBottomNavigationBar(navController)
        }

        selectedBusiness?.let { BusinessDetailDialog(it) { selectedBusiness = null } }
        if (showAddDialog) {
            AddBusinessDialog(
                onDismiss = { showAddDialog = false },
                onAddBusiness = { newBusiness ->
                    isLoading = true
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        db.collection("users")
                            .document(uid)
                            .collection("businesses")
                            .add(newBusiness)
                            .addOnSuccessListener {
                                businesses = businesses + newBusiness
                                isLoading = false
                            }
                            .addOnFailureListener {
                                isLoading = false
                                Toast.makeText(
                                    context,
                                    "Failed to add business",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        isLoading = false
                        Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Composable
fun SearchResultsInfo(resultCount: Int, searchQuery: String, onClearSearch: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().background(BusinessColors.Accent.copy(alpha = 0.1f)).padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Search, null, tint = BusinessColors.Accent, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Found $resultCount results for \"$searchQuery\"", fontSize = 14.sp, color = BusinessColors.OnSurface.copy(alpha = 0.8f))
            }
            TextButton(onClick = onClearSearch) { Text("Clear", color = BusinessColors.Accent, fontSize = 14.sp) }
        }
    }
}

@Composable
fun EnhancedBusinessCard(business: Business, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onEditClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BusinessColors.CardBackground),
        border = BorderStroke(1.dp, BusinessColors.Border),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Row {
                    Box {
                        Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(BusinessColors.Secondary).border(2.dp, BusinessColors.Accent, CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Business, null, tint = Color.White, modifier = Modifier.size(28.dp))
                        }
                        Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(if (business.isOpen) BusinessColors.Success else BusinessColors.Primary).align(Alignment.BottomEnd).border(2.dp, Color.White, CircleShape))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(business.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = BusinessColors.OnSurface)
                            if (business.verified) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(Icons.Default.Verified, "Verified", tint = BusinessColors.Success, modifier = Modifier.size(16.dp))
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                            Text(if (business.isOpen) "Open" else "Closed", fontSize = 12.sp, color = if (business.isOpen) BusinessColors.Success else BusinessColors.Primary, fontWeight = FontWeight.Medium, modifier = Modifier.background(if (business.isOpen) BusinessColors.Success.copy(alpha = 0.1f) else BusinessColors.Primary.copy(alpha = 0.1f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Default.Star, null, tint = BusinessColors.Warning, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(business.rating.toString(), fontSize = 14.sp, fontWeight = FontWeight.Medium, color = BusinessColors.Warning)
                            Text(" (${business.reviewCount})", fontSize = 14.sp, color = BusinessColors.OnSurface.copy(alpha = 0.6f))
                        }
                    }
                }
                IconButton(onClick = onEditClick) { Icon(Icons.Default.MoreVert, "More options", tint = BusinessColors.Accent, modifier = Modifier.size(20.dp)) }
            }

            Spacer(modifier = Modifier.height(16.dp))
            BusinessDetailRow(Icons.Default.Category, business.category)
            BusinessDetailRow(Icons.Default.Phone, business.phone)
            BusinessDetailRow(Icons.Default.LocationOn, business.location)
            BusinessDetailRow(Icons.Default.AccessTime, "Today: ${getCurrentDayHours(business.hours)}")
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = BusinessColors.Border)
            Spacer(modifier = Modifier.height(12.dp))
            Text(business.description, fontSize = 14.sp, color = BusinessColors.OnSurface.copy(alpha = 0.7f), lineHeight = 20.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun HeaderSection(businessCount: Int, totalReviews: Int, onAddBusinessClick: () -> Unit, onSearchClick: () -> Unit, showSearchBar: Boolean, searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    Column {
        Box(modifier = Modifier.fillMaxWidth().background(BusinessColors.Surface).padding(horizontal = 20.dp, vertical = 16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Business Directory", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = BusinessColors.OnSurface)
                    Text("Find & review local businesses", fontSize = 14.sp, color = BusinessColors.OnSurface.copy(alpha = 0.6f))
                }
                IconButton(onClick = onSearchClick, modifier = Modifier.size(40.dp).background(if (showSearchBar) BusinessColors.Primary.copy(alpha = 0.2f) else BusinessColors.Primary.copy(alpha = 0.1f), CircleShape)) {
                    Icon(if (showSearchBar) Icons.Default.Close else Icons.Default.Search, if (showSearchBar) "Close Search" else "Search", tint = BusinessColors.Accent, modifier = Modifier.size(20.dp))
                }
            }
        }

        if (showSearchBar) {
            Box(modifier = Modifier.fillMaxWidth().background(BusinessColors.Surface).padding(horizontal = 15.dp, vertical = 12.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Search businesses, categories.....", color = BusinessColors.OnSurface.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = BusinessColors.Accent, modifier = Modifier.size(20.dp)) },
                    trailingIcon = { if (searchQuery.isNotEmpty()) IconButton(onClick = { onSearchQueryChange("") }) { Icon(Icons.Default.Clear, "Clear", tint = BusinessColors.OnSurface.copy(alpha = 0.6f), modifier = Modifier.size(18.dp)) } },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BusinessColors.Primary, unfocusedBorderColor = BusinessColors.Border, focusedLeadingIconColor = BusinessColors.Primary, unfocusedLeadingIconColor = BusinessColors.Accent),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth().background(Brush.horizontalGradient(colors = listOf(BusinessColors.Primary.copy(alpha = 0.3f), BusinessColors.Secondary.copy(alpha = 0.2f)))).padding(20.dp)) {
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatsCard(Icons.Default.Business, businessCount.toString(), "Businesses", Modifier.weight(1f))
                    StatsCard(Icons.Default.People, totalReviews.toString(), "Reviews", Modifier.weight(1f))
                    StatsCard(Icons.Default.Star, "4.5", "Avg Rating", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(onClick = onAddBusinessClick, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(containerColor = BusinessColors.Surface, contentColor = BusinessColors.ButtonPrimary), border = BorderStroke(1.5.dp, BusinessColors.ButtonPrimary), shape = RoundedCornerShape(10.dp)) {
                    Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("List Your Business", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun StatsCard(icon: ImageVector, value: String, label: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = BusinessColors.Surface), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = BusinessColors.Accent, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BusinessColors.OnSurface)
            Text(label, fontSize = 12.sp, color = BusinessColors.OnSurface.copy(alpha = 0.7f))
        }
    }
}

@Composable
fun BusinessDetailRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, null, tint = BusinessColors.Accent, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, fontSize = 14.sp, color = BusinessColors.OnSurface.copy(alpha = 0.8f), maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun BusinessDetailDialog(business: Business, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth().heightIn(max = 600.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = BusinessColors.Surface)) {
            Column {
                Box(modifier = Modifier.fillMaxWidth().background(Brush.horizontalGradient(colors = listOf(BusinessColors.Primary, BusinessColors.Secondary))).padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Business Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, "Close", tint = Color.White) }
                    }
                }

                LazyColumn(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(BusinessColors.Secondary).border(2.dp, BusinessColors.Accent, CircleShape), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Business, null, tint = Color.White, modifier = Modifier.size(40.dp))
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(business.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = BusinessColors.OnSurface)
                                if (business.verified) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(Icons.Default.Verified, "Verified", tint = BusinessColors.Success, modifier = Modifier.size(20.dp))
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                                Icon(Icons.Default.Star, null, tint = BusinessColors.Warning, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(business.rating.toString(), fontSize = 16.sp, fontWeight = FontWeight.Medium, color = BusinessColors.Warning)
                                Text(" (${business.reviewCount} reviews)", fontSize = 16.sp, color = BusinessColors.OnSurface.copy(alpha = 0.6f))
                            }
                        }
                    }

                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            BusinessDetailRow(Icons.Default.Category, business.category)
                            BusinessDetailRow(Icons.Default.Phone, business.phone)
                            BusinessDetailRow(Icons.Default.LocationOn, business.location)
                            BusinessDetailRow(Icons.Default.Email, business.email)
                            BusinessDetailRow(Icons.Default.Language, business.website)
                        }
                    }

                    item {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AccessTime, null, tint = BusinessColors.Accent, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Business Hours", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = BusinessColors.OnSurface)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            BusinessHoursTable(business.hours)
                        }
                    }

                    item {
                        Column {
                            Text("Business Description", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = BusinessColors.OnSurface)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(business.description, fontSize = 14.sp, color = BusinessColors.OnSurface.copy(alpha = 0.7f), lineHeight = 20.sp)
                        }
                    }

                    item {
                        HorizontalDivider(color = BusinessColors.Border)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = BusinessColors.ButtonPrimary), shape = RoundedCornerShape(8.dp)) { Text("Write Review") }
                            Button(onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = BusinessColors.ButtonSecondary), shape = RoundedCornerShape(8.dp)) { Text("Contact") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessHoursTable(hours: BusinessHours) {
    val hoursMap = mapOf("Sunday" to hours.sunday, "Monday" to hours.monday, "Tuesday" to hours.tuesday, "Wednesday" to hours.wednesday, "Thursday" to hours.thursday, "Friday" to hours.friday, "Saturday" to hours.saturday)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        hoursMap.forEach { (day, time) ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(day, fontSize = 14.sp, color = BusinessColors.OnSurface.copy(alpha = 0.8f))
                Text(time, fontSize = 14.sp, color = if (time == "Closed") BusinessColors.ButtonPrimary else BusinessColors.Success, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun AddBusinessDialog(onDismiss: () -> Unit, onAddBusiness: (Business) -> Unit) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    val isFormValid = name.isNotBlank() && phone.isNotBlank() && category.isNotBlank() && location.isNotBlank()

    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth().heightIn(max = 700.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = BusinessColors.Surface)) {
            Column {
                Box(modifier = Modifier.fillMaxWidth().background(BusinessColors.Primary).padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("List Your Business", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Join our business directory", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                        }
                        IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, "Close", tint = Color.White) }
                    }
                }

                LazyColumn(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    item { EnhancedTextField(name, { name = it }, "Business Name", Icons.Default.Business, true) }
                    item { EnhancedTextField(phone, { phone = it }, "Phone Number", Icons.Default.Phone, true) }
                    item { EnhancedTextField(email, { email = it }, "Email Address", Icons.Default.Email) }
                    item { EnhancedTextField(website, { website = it }, "Website", Icons.Default.Language) }
                    item { EnhancedTextField(category, { category = it }, "Business Category", Icons.Default.Category, true) }
                    item { EnhancedTextField(location, { location = it }, "Location", Icons.Default.LocationOn, true) }
                    item { EnhancedTextField(description, { description = it }, "Business Description", Icons.Default.Description, maxLines = 4) }

                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp), border = BorderStroke(1.2.dp, BusinessColors.Border), enabled = !isSubmitting) { Text("Cancel") }
                            Spacer(modifier = Modifier.width(12.dp))
                            Button(onClick = {
                                if (isFormValid) {
                                    isSubmitting = true
                                    onAddBusiness(Business((100000..999999).random(), name, phone, email, website, category, location, BusinessHours(), 0f, 0, description.ifBlank { "New business listing" }, false, true))
                                    onDismiss()
                                }
                            }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = BusinessColors.ButtonPrimary), enabled = isFormValid && !isSubmitting) {
                                if (isSubmitting) CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White, strokeWidth = 2.dp)
                                else Text("Add Business")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EnhancedTextField(value: String, onValueChange: (String) -> Unit, label: String, icon: ImageVector, isRequired: Boolean = false, maxLines: Int = 1) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(label)
                if (isRequired) Text(" *", color = BusinessColors.Primary, fontWeight = FontWeight.Bold)
            }
        },
        leadingIcon = { Icon(icon, null, tint = BusinessColors.Accent, modifier = Modifier.size(20.dp)) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BusinessColors.Primary, unfocusedBorderColor = BusinessColors.Border, focusedLeadingIconColor = BusinessColors.Primary, unfocusedLeadingIconColor = BusinessColors.Accent, focusedLabelColor = BusinessColors.Primary),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun NoResultsCard(searchQuery: String) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), colors = CardDefaults.cardColors(containerColor = BusinessColors.CardBackground), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.SearchOff, null, tint = BusinessColors.Accent.copy(alpha = 0.6f), modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("No results found", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = BusinessColors.OnSurface, textAlign = TextAlign.Center)
            Text("We couldn't find any businesses matching \"$searchQuery\"", fontSize = 14.sp, color = BusinessColors.OnSurface.copy(alpha = 0.6f), textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Try searching with different keywords or check your spelling", fontSize = 12.sp, color = BusinessColors.OnSurface.copy(alpha = 0.5f), textAlign = TextAlign.Center)
        }
    }
}

fun getCurrentDayHours(hours: BusinessHours): String {
    val calendar = java.util.Calendar.getInstance()
    return when (calendar.get(java.util.Calendar.DAY_OF_WEEK)) {
        java.util.Calendar.SUNDAY -> hours.sunday
        java.util.Calendar.MONDAY -> hours.monday
        java.util.Calendar.TUESDAY -> hours.tuesday
        java.util.Calendar.WEDNESDAY -> hours.wednesday
        java.util.Calendar.THURSDAY -> hours.thursday
        java.util.Calendar.FRIDAY -> hours.friday
        java.util.Calendar.SATURDAY -> hours.saturday
        else -> "Closed"
    }
}

@Composable
fun BusinessBottomNavigationBar(navController: NavController) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), shape = RoundedCornerShape(0.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            BottomNavItem(Icons.Default.Home, "Home", false) { navController.navigate("HOMESCREEN") }
            BottomNavItem(Icons.Default.Business, "My Business", true) { navController.navigate("MYBUSINESSSCREEN") }
            BottomNavItem(Icons.Default.RateReview, "My Reviews", false) { navController.navigate("MYREVIEWSSCREEN") }
            BottomNavItem(Icons.Default.Person, "Profile", false) { navController.navigate("PROFILESCREEN") }
        }
    }
}

@Composable
private fun BottomNavItem(icon: ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(onClick = onClick)) {
        Icon(icon, label, tint = if (isSelected) Color(0xFFEF4444) else Color(0xFF9CA3AF), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 10.sp, color = if (isSelected) Color(0xFFEF4444) else Color(0xFF6B7280))
    }
}