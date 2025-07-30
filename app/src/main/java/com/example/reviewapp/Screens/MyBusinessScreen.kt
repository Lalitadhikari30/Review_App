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
    val isOpen: Boolean = true,
    val imageUrl: String = ""
)

@Composable
fun MyBusinessScreen(navController: NavController,
                     highlightedBusinessId: String?) {
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