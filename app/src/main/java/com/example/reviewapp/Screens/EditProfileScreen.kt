package com.example.reviewapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Preview
@Composable
fun EditProfileScreen() {
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("Jonathan Smith") }
    var email by remember { mutableStateOf("jonathan.smith@gmail.com") }
    var phone by remember { mutableStateOf("7678361428") }
    var alternatePhone by remember { mutableStateOf("") }
    var hintName by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Man") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        // Header
        EditProfileHeader()

        // Profile Picture Section
        ProfilePictureSection()

        // Form Fields
        FormSection(
            name = name,
            email = email,
            phone = phone,
            alternatePhone = alternatePhone,
            hintName = hintName,
            selectedGender = selectedGender,
            onNameChange = { name = it },
            onEmailChange = { email = it },
            onPhoneChange = { phone = it },
            onAlternatePhoneChange = { alternatePhone = it },
            onHintNameChange = { hintName = it },
            onGenderChange = { selectedGender = it }
        )

        // Save Button
        SaveButton()

        // Delete Account
        DeleteAccountSection()

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileHeader() {
    TopAppBar(
        title = {
            Text(
                text = "Personal info",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
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
fun ProfilePictureSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(96.dp)
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
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFED7AA), // orange-200
                                    Color(0xFFFECACA)  // red-200
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        tint = Color(0xFFDC2626), // red-600
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            // Edit Icon
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = Color(0xFF374151), // gray-700
                        shape = CircleShape
                    )
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Photo",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun FormSection(
    name: String,
    email: String,
    phone: String,
    alternatePhone: String,
    hintName: String,
    selectedGender: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAlternatePhoneChange: (String) -> Unit,
    onHintNameChange: (String) -> Unit,
    onGenderChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        // Name Field
        FormField(
            label = "Name",
            value = name,
            onValueChange = onNameChange,
            showArrow = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Gender Field
        GenderField(
            selectedGender = selectedGender,
            onGenderChange = onGenderChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Phone Field
        PhoneField(
            phone = phone,
            onPhoneChange = onPhoneChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        FormField(
            label = "Email",
            value = email,
            onValueChange = onEmailChange,
            keyboardType = KeyboardType.Email,
            showVerified = true,
            showArrow = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Alternate mobile number section
        Text(
            text = "Alternate mobile number details",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF6B7280),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Alternate Phone Field
        AlternatePhoneField(
            alternatePhone = alternatePhone,
            onAlternatePhoneChange = onAlternatePhoneChange
        )

        Text(
            text = "This will help recover your account if needed",
            fontSize = 12.sp,
            color = Color(0xFF9CA3AF),
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        // Hint Name Field
        FormField(
            label = "Hint name",
            value = hintName,
            onValueChange = onHintNameChange,
            placeholder = "Add a name that helps you identify alternate number"
        )

        Text(
            text = "Add a name that helps you identify alternate number",
            fontSize = 12.sp,
            color = Color(0xFF9CA3AF),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    showVerified: Boolean = false,
    showArrow: Boolean = false,
    placeholder: String = ""
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF374151),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFEF4444),
                unfocusedBorderColor = Color(0xFFD1D5DB),
                focusedTextColor = Color(0xFF1F2937),
                unfocusedTextColor = Color(0xFF1F2937)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showVerified) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    if (showArrow) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Arrow",
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            },
            placeholder = {
                if (placeholder.isNotEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color(0xFF9CA3AF),
                        fontSize = 14.sp
                    )
                }
            }
        )
    }
}

@Composable
fun GenderField(
    selectedGender: String,
    onGenderChange: (String) -> Unit
) {
    Column {
        Text(
            text = "Gender",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF374151),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(4.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD1D5DB))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedGender,
                    fontSize = 16.sp,
                    color = Color(0xFF1F2937)
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Select Gender",
                    tint = Color(0xFF9CA3AF),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneField(
    phone: String,
    onPhoneChange: (String) -> Unit
) {
    Column {
        Text(
            text = "Phone number",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF374151),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = phone,
                onValueChange = onPhoneChange,
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFEF4444),
                    unfocusedBorderColor = Color(0xFFD1D5DB),
                    focusedTextColor = Color(0xFF1F2937),
                    unfocusedTextColor = Color(0xFF1F2937)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = {
                    Text(
                        text = "+91",
                        fontSize = 16.sp,
                        color = Color(0xFF1F2937)
                    )
                },
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Arrow",
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(56.dp)
            ) {
                Text(
                    text = "CHANGE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlternatePhoneField(
    alternatePhone: String,
    onAlternatePhoneChange: (String) -> Unit
) {
    OutlinedTextField(
        value = alternatePhone,
        onValueChange = onAlternatePhoneChange,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFEF4444),
            unfocusedBorderColor = Color(0xFFD1D5DB),
            focusedTextColor = Color(0xFF1F2937),
            unfocusedTextColor = Color(0xFF1F2937)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        leadingIcon = {
            Text(
                text = "+91",
                fontSize = 16.sp,
                color = Color(0xFF1F2937)
            )
        },
        placeholder = {
            Text(
                text = "Mobile Number",
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp
            )
        }
    )
}

@Composable
fun SaveButton() {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 32.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEF4444)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "SAVE DETAILS",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun DeleteAccountSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 32.dp)
    ) {
        TextButton(
            onClick = { }
        ) {
            Text(
                text = "DELETE ACCOUNT",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center
            )
        }
    }
}

