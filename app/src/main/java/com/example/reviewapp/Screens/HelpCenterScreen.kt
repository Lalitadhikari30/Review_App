package com.example.reviewapp.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun HelpCenterScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        // Header
        HelpCenterHeader()

        // Hero Section
        HelpCenterHero()

        // Recent Activity Section
        RecentActivitySection()

        // Browse Topics Section
        BrowseTopicsSection(navController)

        // FAQ Section
        FAQSection()

//        // Recent Queries Section
//        RecentQueriesSection()

        // Contact Section
        ContactSection()

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpCenterHeader() {
    TopAppBar(
        title = {
            Text(
                text = "HELP CENTER",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF1F2937)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun HelpCenterHero() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFEF2F2), // red-50
                        Color(0xFFFEE2E2)  // red-100
                    )
                )
            )
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Help Center",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Please get in touch and we will be happy to help you",
                    fontSize = 16.sp,
                    color = Color(0xFF6B7280),
                    lineHeight = 24.sp
                )
            }

            // Support illustration placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFECACA), // red-200
                                Color(0xFFFCA5A5)  // red-300
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.HeadsetMic,
                    contentDescription = "Support",
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun RecentActivitySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
//        Text(
//            text = "NEED HELP WITH RECENT REVIEWS?",
//            fontSize = 12.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF6B7280),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )

//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            colors = CardDefaults.cardColors(containerColor = Color.White),
//            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Row(
//                modifier = Modifier.padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(
//                            color = Color(0xFF10B981),
//                            shape = CircleShape
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.CheckCircle,
//                        contentDescription = "Delivered",
//                        tint = Color.White,
//                        modifier = Modifier.size(20.dp)
//                    )
//                }

//                Spacer(modifier = Modifier.width(16.dp))

//                Column(
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text(
//                        text = "Review Posted",
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = Color(0xFF10B981)
//                    )
//                    Text(
//                        text = "On Fri, 23 Aug",
//                        fontSize = 12.sp,
//                        color = Color(0xFF6B7280)
//                    )

//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        text = "THE COZY CORNER",
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF1F2937)
//                    )
//                    Text(
//                        text = "Amazing dining experience with great service...",
//                        fontSize = 12.sp,
//                        color = Color(0xFF6B7280)
//                    )
//                    Text(
//                        text = "Rating: 5 stars",
//                        fontSize = 12.sp,
//                        color = Color(0xFF6B7280)
//                    )
//                }

//                Icon(
//                    imageVector = Icons.Default.ArrowForward,
//                    contentDescription = "View Details",
//                    tint = Color(0xFF9CA3AF),
//                    modifier = Modifier.size(20.dp)
//                )
            }
        }


@Composable
fun BrowseTopicsSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Browse Topics",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyHelpTopicsGrid(navController)
    }
}

@Composable
fun LazyHelpTopicsGrid(navController: NavController) {
    val topics = listOf(
        HelpTopic(Icons.Default.Person, "Account", Color(0xFFFEF2F2)),
        HelpTopic(Icons.Default.SwapHoriz, "Reviews & Ratings", Color(0xFFF0FDF4)),
        HelpTopic(Icons.Default.Star, "Business Listings", Color(0xFFFFFBEB)),
//        HelpTopic(Icons.Default.LocalOffer, "Offers", Color(0xFFF0F9FF)),
//        HelpTopic(Icons.Default.Payment, "Payments", Color(0xFFF5F3FF)),
        HelpTopic(Icons.Default.Cancel, "Report Issues", Color(0xFFFDF2F8))
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        topics.chunked(2).forEach { rowTopics ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowTopics.forEach { topic ->
                    HelpTopicCard(
                        topic = topic,
                        onTopicClick = {
                            // Handle topic click, e.g., navigate to a detailed screen
                            navController.navigate("QUERYSCREEN")
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowTopics.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun HelpTopicCard(
    topic: HelpTopic,
    onTopicClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(containerColor = topic.backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onTopicClick

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = topic.icon,
                contentDescription = topic.title,
                tint = Color(0xFFEF4444),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = topic.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1F2937),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FAQSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        val faqItems = listOf(
            "How do I write a review?",
            "How do I edit my review?",
            "Why is my review not showing up?",
            "How do I report inappropriate content?"
        )

        faqItems.forEach { question ->
            FAQItem(question = question)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun FAQItem(question: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = question,
                fontSize = 14.sp,
                color = Color(0xFF1F2937),
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "View Answer",
                tint = Color(0xFF9CA3AF),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

//@Composable
//fun RecentQueriesSection() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(24.dp)
//    ) {
//        Text(
//            text = "Recent Queries",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF1F2937),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        Text(
//            text = "There are no recent queries raised in Last 30 Days.",
//            fontSize = 14.sp,
//            color = Color(0xFF6B7280),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        OutlinedButton(
//            onClick = { },
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.outlinedButtonColors(
//                contentColor = Color(0xFF6B7280)
//            ),
//            border = ButtonDefaults.outlinedButtonBorder.copy(
//                brush = Brush.horizontalGradient(
//                    colors = listOf(Color(0xFFD1D5DB), Color(0xFFD1D5DB))
//                )
//            ),
//            shape = RoundedCornerShape(8.dp)
//        ) {
//            Text(
//                text = "SHOW OLDER QUERIES",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Medium
//            )
//        }
//    }
//}

@Composable
fun ContactSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Want to reach us the old way?",
            fontSize = 14.sp,
            color = Color(0xFF6B7280),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { }
        ) {
            Text(
                text = "CONTACT SUPPORT",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEF4444)
            )
        }
    }
}

data class HelpTopic(
    val icon: ImageVector,
    val title: String,
    val backgroundColor: Color
)