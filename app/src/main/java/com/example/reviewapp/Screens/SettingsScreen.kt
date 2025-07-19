

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.reviewapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Preview
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onSettingClick: (String) -> Unit = {}
) {
    val redColor = Color(0xFFE53E3E)
    val backgroundColor = Color(0xFFF7FAFC)
    val cardColor = Color.White
    val textPrimary = Color(0xFF2D3748)
    val textSecondary = Color(0xFF718096)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = textPrimary
                    )
                }
            },
            actions = {
                IconButton(onClick = onHelpClick) {
                    Icon(
                        imageVector = Icons.Default.Help,
                        contentDescription = "Help",
                        tint = textSecondary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            // Account Section
            item {
                SettingsSection(
                    title = "ACCOUNT",
                    items = listOf(
                        SettingsItem(
                            icon = Icons.Default.Person,
                            title = "Profile",
                            subtitle = "Edit your profile information",
                            onClick = { onSettingClick("profile") }
//
//                        SettingsItem(
//                            icon = Icons.Default.Star,
//                            title = "My Reviews",
//                            subtitle = "View and manage your reviews",
//                            onClick = { onSettingClick("my_reviews") }
//                        ),
//                        SettingsItem(
//                            icon = Icons.Default.Home,
//                            title = "Add Home Location",
//                            subtitle = "Set your home address",
//                            onClick = { onSettingClick("home_location") }
//                        ),
//                        SettingsItem(
//                            icon = Icons.Default.Business,
//                            title = "Add Work Location",
//                            subtitle = "Set your work address",
//                            onClick = { onSettingClick("work_location") }
                        )
                    ),
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }

            // App Settings Section
            item {
                SettingsSection(
                    title = "APP SETTINGS",
                    items = listOf(
                        SettingsItem(
                            icon = Icons.Default.Notifications,
                            title = "Notifications",
                            subtitle = "Manage your notification preferences",
                            onClick = { onSettingClick("notifications") }
                        ),
//                        SettingsItem(
//                            icon = Icons.Default.Security,
//                            title = "Privacy",
//                            subtitle = "Control your privacy settings",
//                            onClick = { onSettingClick("privacy") }
//                        )
                    ),
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }

            // Support Section
            item {
                SettingsSection(
                    title = "SUPPORT",
                    items = listOf(
//                        SettingsItem(
//                            icon = Icons.Default.Download,
//                            title = "App Version",
//                            subtitle = "2.1.4",
//                            hasArrow = false,
//                            onClick = { }
//                        ),
                        SettingsItem(
                            icon = Icons.Default.HelpCenter,
                            title = "Help & Support",
                            subtitle = "Get help and contact support",
                            onClick = { onSettingClick("help_support") }
                        ),
                        SettingsItem(
                            icon = Icons.Default.Info,
                            title = "About",
                            subtitle = "Learn more about the app",
                            onClick = { onSettingClick("about") }
                        )
                    ),
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }


            // Account Actions Section
            // Account Actions Section
            item {
                SettingsSection(
                    title = "ACCOUNT ACTIONS",
                    items = listOf(
                        SettingsItem(
                            icon = Icons.Default.Logout,
                            title = "Sign Out",
                            subtitle = "Sign out of your account",
                            onClick = { onSettingClick("sign_out") }
                        ),
//                        SettingsItem(
//                            icon = Icons.Default.Delete,
//                            title = "Delete Account",
//                            subtitle = "Permanently delete your account",
//                            isDestructive = true,
//                            onClick = { onSettingClick("delete_account") }
//                        )
                    ),
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary,
                    redColor = redColor
                )
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    items: List<SettingsItem>,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    redColor: Color = Color.Red
) {
    Column {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = textSecondary,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    SettingsItemRow(
                        item = item,
                        textPrimary = textPrimary,
                        textSecondary = textSecondary,
                        redColor = redColor
                    )

                    if (index < items.size - 1) {
                        Divider(
                            color = Color(0xFFE2E8F0),
                            thickness = 0.5.dp,
                            modifier = Modifier.padding(horizontal = 56.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsItemRow(
    item: SettingsItem,
    textPrimary: Color,
    textSecondary: Color,
    redColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { item.onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading border for destructive items
        if (item.isDestructive) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(40.dp)
                    .background(redColor, RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
        }

        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = if (item.isDestructive) redColor else textSecondary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (item.isDestructive) redColor else textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (item.subtitle.isNotEmpty()) {
                Text(
                    text = item.subtitle,
                    fontSize = 14.sp,
                    color = textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        if (item.hasArrow) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = textSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

data class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String = "",
    val hasArrow: Boolean = true,
    val isDestructive: Boolean = false,
    val onClick: () -> Unit
)