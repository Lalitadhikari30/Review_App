@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.Screens

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
import kotlinx.coroutines.delay


// Add this data class for country codes
data class CountryCode(
    val country: String,
    val code: String,
    val flag: String
)

// Country codes data
val countryCodes = listOf(
    CountryCode("India", "+91", "🇮🇳"),
    CountryCode("United States", "+1", "🇺🇸"),
    CountryCode("United Kingdom", "+44", "🇬🇧"),
    CountryCode("Canada", "+1", "🇨🇦"),
    CountryCode("Australia", "+61", "🇦🇺"),
    CountryCode("Germany", "+49", "🇩🇪"),
    CountryCode("France", "+33", "🇫🇷"),
    CountryCode("Italy", "+39", "🇮🇹"),
    CountryCode("Spain", "+34", "🇪🇸"),
    CountryCode("Japan", "+81", "🇯🇵"),
    CountryCode("China", "+86", "🇨🇳"),
    CountryCode("South Korea", "+82", "🇰🇷"),
    CountryCode("Brazil", "+55", "🇧🇷"),
    CountryCode("Mexico", "+52", "🇲🇽"),
    CountryCode("Russia", "+7", "🇷🇺"),
    CountryCode("South Africa", "+27", "🇿🇦"),
    CountryCode("Netherlands", "+31", "🇳🇱"),
    CountryCode("Switzerland", "+41", "🇨🇭"),
    CountryCode("Sweden", "+46", "🇸🇪"),
    CountryCode("Norway", "+47", "🇳🇴"),
    CountryCode("Denmark", "+45", "🇩🇰"),
    CountryCode("Finland", "+358", "🇫🇮"),
    CountryCode("Belgium", "+32", "🇧🇪"),
    CountryCode("Austria", "+43", "🇦🇹"),
    CountryCode("Poland", "+48", "🇵🇱"),
    CountryCode("Turkey", "+90", "🇹🇷"),
    CountryCode("UAE", "+971", "🇦🇪"),
    CountryCode("Saudi Arabia", "+966", "🇸🇦"),
    CountryCode("Singapore", "+65", "🇸🇬"),
    CountryCode("Malaysia", "+60", "🇲🇾"),
    CountryCode("Thailand", "+66", "🇹🇭"),
    CountryCode("Indonesia", "+62", "🇮🇩"),
    CountryCode("Philippines", "+63", "🇵🇭"),
    CountryCode("Vietnam", "+84", "🇻🇳"),
    CountryCode("Bangladesh", "+880", "🇧🇩"),
    CountryCode("Pakistan", "+92", "🇵🇰"),
    CountryCode("Sri Lanka", "+94", "🇱🇰"),
    CountryCode("Nepal", "+977", "🇳🇵"),
    CountryCode("Egypt", "+20", "🇪🇬"),
    CountryCode("Nigeria", "+234", "🇳🇬"),
    CountryCode("Kenya", "+254", "🇰🇪"),
    CountryCode("Ghana", "+233", "🇬🇭"),
    CountryCode("Argentina", "+54", "🇦🇷"),
    CountryCode("Chile", "+56", "🇨🇱"),
    CountryCode("Colombia", "+57", "🇨🇴"),
    CountryCode("Peru", "+51", "🇵🇪"),
    CountryCode("New Zealand", "+64", "🇳🇿")
)

@Composable
fun AddBusinessDialog(
    onDismiss: () -> Unit,
    onAddBusiness: (Business) -> Unit
) {
    var businessName by remember { mutableStateOf("") }
    var businessPhone by remember { mutableStateOf("") }
    var businessEmail by remember { mutableStateOf("") }
    var businessWebsite by remember { mutableStateOf("") }
    var businessCategory by remember { mutableStateOf("") }
    var businessLocation by remember { mutableStateOf("") }
    var businessDescription by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    // Country code selection state
    var selectedCountryCode by remember { mutableStateOf(countryCodes[0]) } // Default to India
    var showCountryCodePicker by remember { mutableStateOf(false) }

    // Form validation
    val isFormValid = businessName.isNotBlank() &&
            businessPhone.isNotBlank() &&
            businessCategory.isNotBlank() &&
            businessLocation.isNotBlank()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 700.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = BusinessColors.Surface
            )
        ) {
            Column {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BusinessColors.Primary)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "List Your Business",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Join our business directory",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        EnhancedTextField(
                            value = businessName,
                            onValueChange = { businessName = it },
                            label = "Business Name",
                            icon = Icons.Default.Business,
                            isRequired = true
                        )
                    }

                    item {
                        // Phone Number with Country Code Picker
                        PhoneNumberInputField(
                            phoneNumber = businessPhone,
                            onPhoneNumberChange = { businessPhone = it },
                            selectedCountryCode = selectedCountryCode,
                            onCountryCodeClick = { showCountryCodePicker = true },
                            isRequired = true
                        )
                    }

                    item {
                        EnhancedTextField(
                            value = businessEmail,
                            onValueChange = { businessEmail = it },
                            label = "Email Address",
                            icon = Icons.Default.Email
                        )
                    }
                    item {
                        EnhancedTextField(
                            value = businessWebsite,
                            onValueChange = { businessWebsite = it },
                            label = "Website",
                            icon = Icons.Default.Language
                        )
                    }
                    item {
                        EnhancedTextField(
                            value = businessCategory,
                            onValueChange = { businessCategory = it },
                            label = "Business Category",
                            icon = Icons.Default.Category,
                            isRequired = true
                        )
                    }
                    item {
                        EnhancedTextField(
                            value = businessLocation,
                            onValueChange = { businessLocation = it },
                            label = "Location",
                            icon = Icons.Default.LocationOn,
                            isRequired = true
                        )
                    }
                    item {
                        EnhancedTextField(
                            value = businessDescription,
                            onValueChange = { businessDescription = it },
                            label = "Business Description",
                            icon = Icons.Default.Description,
                            maxLines = 4
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                onClick = onDismiss,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.2.dp, BusinessColors.Border),
                                enabled = !isSubmitting
                            ) {
                                Text("Cancel")
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Button(
                                onClick = {
                                    if (isFormValid) {
                                        isSubmitting = true
                                        val fullPhoneNumber = "${selectedCountryCode.code} $businessPhone"
                                        onAddBusiness(
                                            Business(
                                                id = (100000..999999).random(),
                                                name = businessName,
                                                phone = fullPhoneNumber,
                                                email = businessEmail,
                                                website = businessWebsite,
                                                category = businessCategory,
                                                location = businessLocation,
                                                hours = BusinessHours(),
                                                rating = 0f,
                                                reviewCount = 0,
                                                description = businessDescription.ifBlank { "New business listing" },
                                                verified = false,
                                                isOpen = true
                                            )
                                        )
                                        onDismiss()
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BusinessColors.ButtonPrimary
                                ),
                                enabled = isFormValid && !isSubmitting
                            ) {
                                if (isSubmitting) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text("Add Business")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Country Code Picker Dialog
    if (showCountryCodePicker) {
        CountryCodePickerDialog(
            countryCodes = countryCodes,
            selectedCountryCode = selectedCountryCode,
            onCountryCodeSelected = { countryCode ->
                selectedCountryCode = countryCode
                showCountryCodePicker = false
            },
            onDismiss = { showCountryCodePicker = false }
        )
    }
}

@Composable
fun PhoneNumberInputField(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    selectedCountryCode: CountryCode,
    onCountryCodeClick: () -> Unit,
    isRequired: Boolean = false
) {
    Column {
        Text(
            text = if (isRequired) "Phone Number *" else "Phone Number",
            fontSize = 14.sp,
            color = BusinessColors.OnSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Country Code Picker Button
            Card(
                modifier = Modifier
                    .clickable { onCountryCodeClick() }
                    .padding(end = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = BusinessColors.CardBackground
                ),
                border = BorderStroke(1.dp, BusinessColors.Border),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedCountryCode.flag,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = selectedCountryCode.code,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = BusinessColors.OnSurface
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select country code",
                        tint = BusinessColors.Accent,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Phone Number Input
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
                placeholder = {
                    Text(
                        text = "Enter phone number",
                        color = BusinessColors.OnSurface.copy(alpha = 0.5f)
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        tint = BusinessColors.Accent,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BusinessColors.Primary,
                    unfocusedBorderColor = BusinessColors.Border,
                    focusedLeadingIconColor = BusinessColors.Primary,
                    unfocusedLeadingIconColor = BusinessColors.Accent
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }
    }
}

@Composable
fun CountryCodePickerDialog(
    countryCodes: List<CountryCode>,
    selectedCountryCode: CountryCode,
    onCountryCodeSelected: (CountryCode) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredCountryCodes = remember(searchQuery, countryCodes) {
        if (searchQuery.isBlank()) {
            countryCodes
        } else {
            countryCodes.filter { country ->
                country.country.contains(searchQuery, ignoreCase = true) ||
                        country.code.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 600.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = BusinessColors.Surface
            )
        ) {
            Column {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BusinessColors.Primary)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Select Country Code",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Search country or code...",
                            color = BusinessColors.OnSurface.copy(alpha = 0.5f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = BusinessColors.Accent,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = BusinessColors.OnSurface.copy(alpha = 0.6f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BusinessColors.Primary,
                        unfocusedBorderColor = BusinessColors.Border
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Country List
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(filteredCountryCodes) { countryCode ->
                        CountryCodeItem(
                            countryCode = countryCode,
                            isSelected = countryCode == selectedCountryCode,
                            onSelect = { onCountryCodeSelected(countryCode) }
                        )

                        if (countryCode != filteredCountryCodes.last()) {
                            HorizontalDivider(
                                color = BusinessColors.Border,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }

                    if (filteredCountryCodes.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.SearchOff,
                                    contentDescription = null,
                                    tint = BusinessColors.Accent.copy(alpha = 0.6f),
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "No countries found",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = BusinessColors.OnSurface,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Try searching with different keywords",
                                    fontSize = 14.sp,
                                    color = BusinessColors.OnSurface.copy(alpha = 0.6f),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountryCodeItem(
    countryCode: CountryCode,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .background(
                if (isSelected) BusinessColors.Primary.copy(alpha = 0.1f)
                else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = countryCode.flag,
            fontSize = 20.sp,
            modifier = Modifier.padding(end = 12.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = countryCode.country,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = BusinessColors.OnSurface
            )
            Text(
                text = countryCode.code,
                fontSize = 14.sp,
                color = BusinessColors.OnSurface.copy(alpha = 0.7f)
            )
        }

        if (isSelected) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Selected",
                tint = BusinessColors.Primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
// Custom Colors - Red Theme
object BusinessColors {
    val Primary = Color(0xFFDC2626) // Vibrant red
    val Secondary = Color(0xFFB91C1C) // Darker red
    val Background = Color(0xFFFEF2F2) // Very light red background
    val Surface = Color(0xFFFFFFFF) // White surface
    val OnSurface = Color(0xFF1F2937) // Dark text
    val Accent = Color(0xFFEF4444) // Bright red accent
    val CardBackground = Color(0xFFFEF7F7) // Light red card background
    val Border = Color(0xFFFCA5A5) // Light red border
    val Success = Color(0xFF10B981) // Green for success states
    val Warning = Color(0xFFFCD34D) // Yellow for ratings

    // Button colors
    val ButtonPrimary = Color(0xFFDC2626) // Red-600
    val ButtonSecondary = Color(0xFF374151) // Gray-700
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
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val website: String,
    val category: String,
    val location: String,
    val hours: BusinessHours,
    val rating: Float,
    val reviewCount: Int,
    val description: String,
    val verified: Boolean,
    val isOpen: Boolean = true // Added to track if business is currently open
)

@Composable
fun MyBusinessScreen(navController: NavController) {
    var businesses by remember {
        mutableStateOf(
            listOf(
                Business(
                    id = 1,
                    name = "mrventerpris",
                    phone = "+91 89204 12085",
                    email = "info@printedgeindia.com",
                    website = "https://printedgeindia.com",
                    category = "Marketing Agency, Advertising Agency, Social Media Agency",
                    location = "Dwarka, Sec-1, New Delhi",
                    hours = BusinessHours(),
                    rating = 4.5f,
                    reviewCount = 127,
                    description = "Professional marketing and advertising services with social media expertise.",
                    verified = true,
                    isOpen = true
                ),
                Business(
                    id = 2,
                    name = "Digital Solutions Hub",
                    phone = "+91 98765 43210",
                    email = "contact@digitalhub.com",
                    website = "https://digitalhub.com",
                    category = "Digital Marketing, Web Development, SEO Agency",
                    location = "Gurgaon, Haryana",
                    hours = BusinessHours(
                        monday = "10:00 - 19:00",
                        tuesday = "10:00 - 19:00",
                        wednesday = "10:00 - 19:00",
                        thursday = "10:00 - 19:00",
                        friday = "10:00 - 19:00",
                        saturday = "10:00 - 17:00"
                    ),
                    rating = 4.2f,
                    reviewCount = 89,
                    description = "Comprehensive digital solutions for modern businesses.",
                    verified = true,
                    isOpen = false
                ),
                Business(
                    id = 3,
                    name = "Creative Studio Pro",
                    phone = "+91 87654 32109",
                    email = "hello@creativestudio.com",
                    website = "https://creativestudio.com",
                    category = "Graphic Design, Branding, Creative Agency",
                    location = "Mumbai, Maharashtra",
                    hours = BusinessHours(
                        monday = "09:30 - 18:30",
                        tuesday = "09:30 - 18:30",
                        wednesday = "09:30 - 18:30",
                        thursday = "09:30 - 18:30",
                        friday = "09:30 - 18:30",
                        saturday = "10:00 - 16:00"
                    ),
                    rating = 4.8f,
                    reviewCount = 234,
                    description = "Award-winning creative solutions and brand identity design.",
                    verified = false,
                    isOpen = true
                )
            )
        )
    }

    var selectedBusiness by remember { mutableStateOf<Business?>(null) }
    var showAddBusinessDialog by remember { mutableStateOf(false) }
    var showSearchBar by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val filteredBusinesses = remember(searchQuery, businesses) {
        if (searchQuery.isBlank()) {
            businesses
        } else {
            businesses.filter { business ->
                business.name.contains(searchQuery, ignoreCase = true) ||
                        business.category.contains(searchQuery, ignoreCase = true) ||
                        business.location.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    // Function to add new business with loading state
    val addNewBusiness = { newBusiness: Business ->
        isLoading = true
        businesses = businesses + newBusiness
        isLoading = false
    }

    var selectedNavIndex by remember { mutableStateOf(1) } // Set to 1 for "My Businesses" tab

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BusinessColors.Background)
    ) {
        Column {
            // Status Bar Spacer - adjusted for mobile notch
            Spacer(modifier = Modifier.height(40.dp))

            // Header
            HeaderSection(
                businessCount = filteredBusinesses.size,
                totalReviews = businesses.sumOf { it.reviewCount },
                onAddBusinessClick = { showAddBusinessDialog = true },
                onSearchClick = {
                    showSearchBar = !showSearchBar
                    if (!showSearchBar) {
                        searchQuery = ""
                    }
                },
                showSearchBar = showSearchBar,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it }
            )

            // Search Results Info with animation
            if (searchQuery.isNotBlank()) {
                SearchResultsInfo(
                    resultCount = filteredBusinesses.size,
                    searchQuery = searchQuery,
                    onClearSearch = {
                        searchQuery = ""
                        showSearchBar = false
                    }
                )
            }

            // Business List with loading state
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = BusinessColors.Primary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredBusinesses) { business ->
                        EnhancedBusinessCard(
                            business = business,
                            onEditClick = { selectedBusiness = business }
                        )
                    }

                    if (filteredBusinesses.isEmpty() && searchQuery.isNotBlank()) {
                        item {
                            NoResultsCard(searchQuery = searchQuery)
                        }
                    }
                }
            }
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BusinessBottomNavigationBar(navController = navController)
        }

        // Business Detail Modal
        selectedBusiness?.let { business ->
            BusinessDetailDialog(
                business = business,
                onDismiss = { selectedBusiness = null }
            )
        }

        // Add Business Dialog
        if (showAddBusinessDialog) {
            AddBusinessDialog(
                onDismiss = { showAddBusinessDialog = false },
                onAddBusiness = addNewBusiness
            )
        }
    }
}

@Composable
fun SearchResultsInfo(
    resultCount: Int,
    searchQuery: String,
    onClearSearch: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BusinessColors.Accent.copy(alpha = 0.1f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = BusinessColors.Accent,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Found $resultCount results for \"$searchQuery\"",
                    fontSize = 14.sp,
                    color = BusinessColors.OnSurface.copy(alpha = 0.8f)
                )
            }
            TextButton(onClick = onClearSearch) {
                Text(
                    text = "Clear",
                    color = BusinessColors.Accent,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun EnhancedBusinessCard(business: Business, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEditClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = BusinessColors.CardBackground
        ),
        border = BorderStroke(1.dp, BusinessColors.Border),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row {
                    // Profile Picture with status indicator
                    Box {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(BusinessColors.Secondary)
                                .border(2.dp, BusinessColors.Accent, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Business,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        // Open/Closed status indicator
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(if (business.isOpen) BusinessColors.Success else BusinessColors.Primary)
                                .align(Alignment.BottomEnd)
                                .border(2.dp, Color.White, CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Business Info
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = business.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BusinessColors.OnSurface
                            )
                            if (business.verified) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    tint = BusinessColors.Success,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        // Status and Rating Row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            // Open/Closed status
                            Text(
                                text = if (business.isOpen) "Open" else "Closed",
                                fontSize = 12.sp,
                                color = if (business.isOpen) BusinessColors.Success else BusinessColors.Primary,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .background(
                                        if (business.isOpen) BusinessColors.Success.copy(alpha = 0.1f)
                                        else BusinessColors.Primary.copy(alpha = 0.1f),
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Rating
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = BusinessColors.Warning,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = business.rating.toString(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = BusinessColors.Warning
                            )
                            Text(
                                text = " (${business.reviewCount})",
                                fontSize = 14.sp,
                                color = BusinessColors.OnSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }

                IconButton(onClick = onEditClick) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = BusinessColors.Accent,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Business Details with improved icons
            BusinessDetailRow(Icons.Default.Category, business.category)
            BusinessDetailRow(Icons.Default.Phone, business.phone)
            BusinessDetailRow(Icons.Default.LocationOn, business.location)
            BusinessDetailRow(Icons.Default.AccessTime, "Today: ${getCurrentDayHours(business.hours)}")

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            HorizontalDivider(color = BusinessColors.Border)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = business.description,
                fontSize = 14.sp,
                color = BusinessColors.OnSurface.copy(alpha = 0.7f),
                lineHeight = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )


        }
    }
}



@Composable
fun HeaderSection(
    businessCount: Int,
    totalReviews: Int,
    onAddBusinessClick: () -> Unit,
    onSearchClick: () -> Unit,
    showSearchBar: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Column {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BusinessColors.Surface)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Business Directory",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = BusinessColors.OnSurface
                    )
                    Text(
                        text = "Find & review local businesses",
                        fontSize = 14.sp,
                        color = BusinessColors.OnSurface.copy(alpha = 0.6f)
                    )
                }

                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            if (showSearchBar) BusinessColors.Primary.copy(alpha = 0.2f)
                            else BusinessColors.Primary.copy(alpha = 0.1f),
                            CircleShape
                        )
                ) {
                    Icon(
                        if (showSearchBar) Icons.Default.Close else Icons.Default.Search,
                        contentDescription = if (showSearchBar) "Close Search" else "Search",
                        tint = BusinessColors.Accent,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Search Bar (appears when search is clicked)
        if (showSearchBar) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BusinessColors.Surface)
                    .padding(horizontal = 15.dp, vertical = 12.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = {
                        Text(
                            text = "Search businesses, categories.....",
                            color = BusinessColors.OnSurface.copy(alpha = 0.5f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = BusinessColors.Accent,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = BusinessColors.OnSurface.copy(alpha = 0.6f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BusinessColors.Primary,
                        unfocusedBorderColor = BusinessColors.Border,
                        focusedLeadingIconColor = BusinessColors.Primary,
                        unfocusedLeadingIconColor = BusinessColors.Accent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        // Stats and Action Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            BusinessColors.Primary.copy(alpha = 0.3f),
                            BusinessColors.Secondary.copy(alpha = 0.2f)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                // Stats Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatsCard(
                        icon = Icons.Default.Business,
                        value = businessCount.toString(),
                        label = "Businesses",
                        modifier = Modifier.weight(1f)
                    )
                    StatsCard(
                        icon = Icons.Default.People,
                        value = totalReviews.toString(),
                        label = "Reviews",
                        modifier = Modifier.weight(1f)
                    )
                    StatsCard(
                        icon = Icons.Default.Star,
                        value = "4.5",
                        label = "Avg Rating",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Add Business Button
                OutlinedButton(
                    onClick = onAddBusinessClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = BusinessColors.Surface,
                        contentColor = BusinessColors.ButtonPrimary
                    ),
                    border = BorderStroke(1.5.dp, BusinessColors.ButtonPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "List Your Business",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun StatsCard(
    icon: ImageVector,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = BusinessColors.Surface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = BusinessColors.Accent,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BusinessColors.OnSurface
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = BusinessColors.OnSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun BusinessDetailRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = BusinessColors.Accent,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = BusinessColors.OnSurface.copy(alpha = 0.8f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun BusinessDetailDialog(business: Business, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 600.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = BusinessColors.Surface
            )
        ) {
            Column {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(BusinessColors.Primary, BusinessColors.Secondary)
                            )
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Business Profile",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Profile Section
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(BusinessColors.Secondary)
                                    .border(2.dp, BusinessColors.Accent, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Business,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = business.name,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BusinessColors.OnSurface
                                )
                                if (business.verified) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        Icons.Default.Verified,
                                        contentDescription = "Verified",
                                        tint = BusinessColors.Success,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = BusinessColors.Warning,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = business.rating.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = BusinessColors.Warning
                                )
                                Text(
                                    text = " (${business.reviewCount} reviews)",
                                    fontSize = 16.sp,
                                    color = BusinessColors.OnSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }

                    item {
                        // Contact Details
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            BusinessDetailRow(Icons.Default.Category, business.category)
                            BusinessDetailRow(Icons.Default.Phone, business.phone)
                            BusinessDetailRow(Icons.Default.LocationOn, business.location)
                            BusinessDetailRow(Icons.Default.Email, business.email)
                            BusinessDetailRow(Icons.Default.Language, business.website)
                        }
                    }

                    item {
                        // Business Hours
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.AccessTime,
                                    contentDescription = null,
                                    tint = BusinessColors.Accent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Business Hours",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = BusinessColors.OnSurface
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            BusinessHoursTable(business.hours)
                        }
                    }

                    item {
                        // Description
                        Column {
                            Text(
                                text = "Business Description",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BusinessColors.OnSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = business.description,
                                fontSize = 14.sp,
                                color = BusinessColors.OnSurface.copy(alpha = 0.7f),
                                lineHeight = 20.sp
                            )
                        }
                    }

                    item {
                        // Action Buttons
                        HorizontalDivider(color = BusinessColors.Border)
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { /* Write review */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BusinessColors.ButtonPrimary
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Write Review")
                            }

                            Button(
                                onClick = { /* Contact */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BusinessColors.ButtonSecondary
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Contact")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessHoursTable(hours: BusinessHours) {
    val hoursMap = mapOf(
        "Sunday" to hours.sunday,
        "Monday" to hours.monday,
        "Tuesday" to hours.tuesday,
        "Wednesday" to hours.wednesday,
        "Thursday" to hours.thursday,
        "Friday" to hours.friday,
        "Saturday" to hours.saturday
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        hoursMap.forEach { (day, time) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = day,
                    fontSize = 14.sp,
                    color = BusinessColors.OnSurface.copy(alpha = 0.8f)
                )
                Text(
                    text = time,
                    fontSize = 14.sp,
                    color = if (time == "Closed") BusinessColors.ButtonPrimary else BusinessColors.Success,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@Composable
fun EnhancedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isRequired: Boolean = false,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(label)
                if (isRequired) {
                    Text(
                        " *",
                        color = BusinessColors.Primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                icon,
                contentDescription = null,
                tint = BusinessColors.Accent,
                modifier = Modifier.size(20.dp)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BusinessColors.Primary,
            unfocusedBorderColor = BusinessColors.Border,
            focusedLeadingIconColor = BusinessColors.Primary,
            unfocusedLeadingIconColor = BusinessColors.Accent,
            focusedLabelColor = BusinessColors.Primary
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun NoResultsCard(searchQuery: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BusinessColors.CardBackground
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = null,
                tint = BusinessColors.Accent.copy(alpha = 0.6f),
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No results found",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = BusinessColors.OnSurface,
                textAlign = TextAlign.Center
            )

            Text(
                text = "We couldn't find any businesses matching \"$searchQuery\"",
                fontSize = 14.sp,
                color = BusinessColors.OnSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Try searching with different keywords or check your spelling",
                fontSize = 12.sp,
                color = BusinessColors.OnSurface.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )
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
                isSelected = false,
                onClick = { navController.navigate("HOMESCREEN") }
            )
            BottomNavItem(
                icon = Icons.Default.Business,
                label = "My Business",
                isSelected = true,
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