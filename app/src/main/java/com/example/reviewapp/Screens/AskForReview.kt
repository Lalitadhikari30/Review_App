
package com.example.reviewapp.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun AskForReviewTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFFE53E3E),
            secondary = Color(0xFFFF6B6B),
            background = Color.White,
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.Black,
            onSurface = Color.Black
        )
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskForReview(
    navController: NavController,
    userProfileImageUrl: String? = null // Parameter for user profile image
) {
    var postText by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var showUrlDialog by remember { mutableStateOf(false) }
    var urlText by remember { mutableStateOf("") }
    var isImageSelected by remember { mutableStateOf(false) }
    var isVideoSelected by remember { mutableStateOf(false) }
    var hasLink by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val urlPattern = Regex("https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+")

    // Gallery picker for images
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
        if (uri != null) {
            isImageSelected = true
            Toast.makeText(context, "Image selected", Toast.LENGTH_SHORT).show()
        }
    }

    // Gallery picker for videos
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedVideoUri = uri
        if (uri != null) {
            isVideoSelected = true
            Toast.makeText(context, "Video selected", Toast.LENGTH_SHORT).show()
        }
    }

    // URL Dialog
    if (showUrlDialog) {
        AlertDialog(
            onDismissRequest = { showUrlDialog = false },
            title = { Text("Add Link") },
            text = {
                TextField(
                    value = urlText,
                    onValueChange = { urlText = it },
                    placeholder = { Text("Enter URL...") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (urlText.isNotEmpty()) {
                            val formattedUrl = if (!urlText.startsWith("http://") && !urlText.startsWith("https://")) {
                                "https://$urlText"
                            } else {
                                urlText
                            }
                            postText += "\n$formattedUrl"
                            hasLink = true
                            Toast.makeText(context, "Link added", Toast.LENGTH_SHORT).show()
                        }
                        showUrlDialog = false
                        urlText = ""
                    }
                ) {
                    Text("Add", color = Color(0xFFE53E3E))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showUrlDialog = false
                    urlText = ""
                }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            },
            actions = {
                Button(
                    onClick = {
                        Toast.makeText(context, "Post submitted!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53E3E)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text(
                        "Post",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        // Profile Section with Text Input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE53E3E))
            ) {
                // Show user profile image if available, otherwise show default background
                if (!userProfileImageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = userProfileImageUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                // If no profile image, the red background will show
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Text Input Area
            Column(
                modifier = Modifier.weight(1f)
            ) {
                val annotatedString = remember(postText) {
                    if (postText.isNotEmpty() && urlPattern.containsMatchIn(postText)) {
                        buildAnnotatedString {
                            var lastIndex = 0
                            urlPattern.findAll(postText).forEach { matchResult ->
                                append(postText.substring(lastIndex, matchResult.range.first))
                                pushStringAnnotation(tag = "URL", annotation = matchResult.value)
                                withStyle(
                                    style = SpanStyle(
                                        color = Color(0xFFE53E3E),
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) {
                                    append(matchResult.value)
                                }
                                pop()
                                lastIndex = matchResult.range.last + 1
                            }
                            if (lastIndex < postText.length) {
                                append(postText.substring(lastIndex))
                            }
                        }
                    } else {
                        buildAnnotatedString { append(postText) }
                    }
                }

                if (postText.isNotEmpty() && urlPattern.containsMatchIn(postText)) {
                    ClickableText(
                        text = annotatedString,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { offset ->
                            annotatedString.getStringAnnotations(
                                tag = "URL",
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let { annotation ->
                                try {
                                    uriHandler.openUri(annotation.item)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Unable to open link", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                } else {
                    TextField(
                        value = postText,
                        onValueChange = { postText = it },
                        placeholder = {
                            Text(
                                "What's happening?",
                                color = Color.Gray,
                                fontSize = 18.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }

        // Spacer to push bottom bar down
        Spacer(modifier = Modifier.weight(1f))

        // Bottom Navigation Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image Picker Button
                IconButton(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        if (isImageSelected) Icons.Filled.Image else Icons.Outlined.Image,
                        contentDescription = "Select Image",
                        tint = Color(0xFFE53E3E),
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Video Picker Button
                IconButton(
                    onClick = {
                        videoPickerLauncher.launch("video/*")
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        if (isVideoSelected) Icons.Filled.Videocam else Icons.Outlined.Videocam,
                        contentDescription = "Select Video",
                        tint = Color(0xFFE53E3E),
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Link Button
                IconButton(
                    onClick = {
                        showUrlDialog = true
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        if (hasLink) Icons.Filled.Link else Icons.Outlined.Link,
                        contentDescription = "Add Link",
                        tint = Color(0xFFE53E3E),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}





