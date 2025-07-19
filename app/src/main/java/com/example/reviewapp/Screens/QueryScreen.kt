

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.reviewsapp.Screen.QueryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long,
    val isTyping: Boolean = false
)
@Preview
@Composable
fun QueryScreen() {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }
    var isTyping by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    // Colors
    val RedPrimary = Color(0xFFDC2626)
    val RedLight = Color(0xFFEF4444)
    val TealPrimary = Color(0xFF0F766E)
    val TealLight = Color(0xFF14B8A6)
    val GrayLight = Color(0xFFF3F4F6)
    val GrayMedium = Color(0xFF6B7280)

    // Initialize with welcome message
    LaunchedEffect(Unit) {
        messages = listOf(
            ChatMessage(
                id = "1",
                text = "Hi, you're in the right place for customer service support.\n\nYou can tell us what you need in your own words. How can we help you today?",
                isFromUser = false,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            delay(100)
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    suspend fun sendBotResponse(userMessage: String) {
        isTyping = true
        delay(1500) // Simulate typing delay

        val response = when {
            userMessage.contains("refund", ignoreCase = true) ->
                "I understand you need help with a refund. Let me connect you with our refund specialist who can assist you with processing your request."
            userMessage.contains("booking", ignoreCase = true) ->
                "I can help you with booking issues. Could you please provide your booking reference number so I can look into this for you?"
            userMessage.contains("review", ignoreCase = true) ->
                "I'm here to help with review-related questions. Are you having trouble leaving a review or is there an issue with a review you received?"
            userMessage.contains("payment", ignoreCase = true) ->
                "I can assist with payment issues. Are you having trouble with a payment that didn't go through or need help updating your payment method?"
            userMessage.contains("cancel", ignoreCase = true) ->
                "I can help you with cancellations. Please note that cancellation policies vary by property. Let me check your booking details."
            else ->
                "Thank you for reaching out. I'm reviewing your request and will provide you with the best assistance. Could you please provide more details about your specific issue?"
        }

        isTyping = false
        messages = messages + ChatMessage(
            id = UUID.randomUUID().toString(),
            text = response,
            isFromUser = false,
            timestamp = System.currentTimeMillis()
        )
    }

    @Composable
    fun sendMessage() {
        if (messageText.trim().isNotEmpty()) {
            val userMessage = messageText.trim()
            messages = messages + ChatMessage(
                id = UUID.randomUUID().toString(),
                text = userMessage,
                isFromUser = true,
                timestamp = System.currentTimeMillis()
            )
            messageText = ""

            // Simulate bot response
            LaunchedEffect(messages.size) {
                sendBotResponse(userMessage)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayLight)
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Customer Service Chat",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                "HELPCENTERSCREEN"/* Handle back navigation */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = RedPrimary
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* Handle more options */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = Color.Black
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        // Search Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = GrayMedium,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Search or ask a question...",
                    color = GrayMedium,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                )

//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    IconButton(
//                        onClick = { /* Handle camera */ },
//                        modifier = Modifier.size(32.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.CameraAlt,
//                            contentDescription = "Camera",
//                            tint = GrayMedium,
//                            modifier = Modifier.size(20.dp)
//                        )
//                    }
//
//                    IconButton(
//                        onClick = { /* Handle voice */ },
//                        modifier = Modifier.size(32.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Mic,
//                            contentDescription = "Voice",
//                            tint = GrayMedium,
//                            modifier = Modifier.size(20.dp)
//                        )
//                    }
//
//                    IconButton(
//                        onClick = { /* Handle QR */ },
//                        modifier = Modifier.size(32.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.QrCode,
//                            contentDescription = "QR Code",
//                            tint = GrayMedium,
//                            modifier = Modifier.size(20.dp)
//                        )
//                    }
//                }
            }
        }

        // Chat Messages
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(messages) { message ->
                ChatMessageItem(message = message)
            }

            // Typing indicator
            if (isTyping) {
                item {
                    TypingIndicator()
                }
            }
        }

        // Message Input
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text(
                            text = "Type something like, \"return an item\"",
                            color = GrayMedium,
                            fontSize = 14.sp
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = RedPrimary
                    ),
                    maxLines = 3
                )

                IconButton(
                    onClick = {  },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            if (messageText.trim().isNotEmpty()) RedPrimary else GrayMedium,
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    val RedPrimary = Color(0xFFDC2626)
    val TealPrimary = Color(0xFF0F766E)
    val GrayLight = Color(0xFFF3F4F6)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isFromUser) {
            // Bot avatar
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(TealPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "CS",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier.widthIn(max = 280.dp),
            horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start
        ) {
            Card(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (message.isFromUser) 16.dp else 4.dp,
                    bottomEnd = if (message.isFromUser) 4.dp else 16.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (message.isFromUser) RedPrimary else Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(12.dp),
                    color = if (message.isFromUser) Color.White else Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            // Timestamp
            Text(
                text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp)),
                fontSize = 12.sp,
                color = Color(0xFF9CA3AF),
                modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp)
            )
        }

        if (message.isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))

            // User avatar
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(RedPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "U",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TypingIndicator() {
    val TealPrimary = Color(0xFF0F766E)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        // Bot avatar
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(TealPrimary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CS",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Card(
            shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(3) { index ->
                    var alpha by remember { mutableStateOf(0.3f) }

                    LaunchedEffect(Unit) {
                        delay(index * 200L)
                        while (true) {
                            alpha = 1f
                            delay(600)
                            alpha = 0.3f
                            delay(600)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                Color.Gray.copy(alpha = alpha),
                                CircleShape
                            )
                    )
                }
            }
        }
    }
}