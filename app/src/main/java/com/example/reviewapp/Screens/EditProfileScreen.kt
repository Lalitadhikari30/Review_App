package com.example.reviewapp.Screens

import android.R.attr.name
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.navigation.compose.rememberNavController
import com.example.reviewapp.AuthViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


//@Preview
@Composable
fun EditProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val firebaseAuth = FirebaseAuth.getInstance()
    var user by remember { mutableStateOf(firebaseAuth.currentUser) }
    val context = LocalContext.current

//    var name by remember { mutableStateOf(user?.displayName ?: "")  }
    var name by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser?.displayName ?: "") }

    var email by remember { mutableStateOf(user?.email ?: "") }
    var phone by remember { mutableStateOf("7678361428") }
    var alternatePhone by remember { mutableStateOf("") }
    var hintName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Gender") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var userDeleted by remember { mutableStateOf(false) }
    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    val firestore = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        user?.uid?.let { uid ->
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        name = document.getString("name") ?: ""
                        gender = document.getString("gender") ?: ""
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        // Header
        EditProfileHeader()

        // Profile Picture Section
        ProfilePictureSection(
            profileImageUri = profileImageUri,
            onEditClick = {
                imagePickerLauncher.launch("image/*")
            }
        )

        // Form Fields
        FormSection(
            name = name,
            email = email,
            phone = phone,
            alternatePhone = alternatePhone,
            hintName = hintName,
            selectedGender = gender,
            onNameChange = { name = it },
            onEmailChange = { email = it },
            onPhoneChange = { phone = it },
            onAlternatePhoneChange = { alternatePhone = it },
            onHintNameChange = { hintName = it },
            onGenderChange = { gender = it }
        )

        // Save Button
        SaveButton( name = name,
            selectedGender = gender,
            onSuccess = {
                Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
            },
            onError = { errMsg ->
                Toast.makeText(context, "Failed: $errMsg", Toast.LENGTH_SHORT).show()
            })

            // Delete Account
//
        DeleteAccountButton(navController = navController, user = user, onUserDeleted = {
            userDeleted = true
        }
        )


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
fun ProfilePictureSection(
    profileImageUri: Uri?,
    onEditClick: () -> Unit
) {
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
                        brush = if (profileImageUri == null) {
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFECACA), // red-200
                                    Color(0xFFFCA5A5)  // red-300
                                )
                            )
                        } else {
                            Brush.radialGradient(
                                colors = listOf(Color.Transparent, Color.Transparent)
                            )
                        },
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (profileImageUri != null) {
                    // Display selected image
                    AsyncImage(
                        model = profileImageUri,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Default avatar
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
            }

            // Edit Icon - Now clickable
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = Color(0xFF374151), // gray-700
                        shape = CircleShape
                    )
                    .align(Alignment.BottomEnd)
                    .clickable { onEditClick() }, // Added clickable functionality
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

            Column {
                Text(
                    text = "Email Address",
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
                        value = email,
                        onValueChange = {}, // No-op: disables editing
                        modifier = Modifier
                            .weight(1f),
                        enabled = false, // disables input & keyboard
                        readOnly = true, // avoids triggering focus
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            disabledBorderColor = Color.Gray,
                            disabledLabelColor = Color.Gray,
                            disabledTrailingIconColor = Color.Gray
                        )
                    )


                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
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
    var showDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Gender",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF374151),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true },
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

        // Gender Selection Dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = "Select Gender")
                },
                text = {
                    Column {
                        listOf("Male", "Female", "Others").forEach { gender ->
                            Text(
                                text = gender,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onGenderChange(gender)
                                        showDialog = false
                                    }
                                    .padding(12.dp),
                                color = if (gender == selectedGender) Color.Red else Color.Black
                            )
                        }
                    }
                },
                confirmButton = {}
            )
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

@Composable
fun SaveButton(name: String,                    // the updated name from EditText field
               onSuccess: () -> Unit,           // callback after success
               selectedGender: String,
               onError: (String) -> Unit)        // callback on error
     {
         val context = LocalContext.current
         val user = FirebaseAuth.getInstance().currentUser
         val firestore = FirebaseFirestore.getInstance("users")

         Button(
        onClick = {
            val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // ✅ Now also update gender in Firestore
                        val userId = user.uid
                        val userMap = mapOf(
                            "name" to name,
                            "gender" to selectedGender
                        )

                        FirebaseFirestore.getInstance().collection("users")
                            .document(userId)
                            .set(
                                mapOf(
                                    "name" to name,
                                    "gender" to selectedGender
                                ),
                                SetOptions.merge()
                            )

                            .addOnSuccessListener {
                                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                                onSuccess()
                            }
                            .addOnFailureListener {
                                onError(it.message ?: "Failed to update Firestore")
                            }
                    } else {
                        onError(task.exception?.message ?: "Failed to update profile")
                    }
                }
                  },
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
fun DeleteAccountButton(navController: NavController,
                        user: FirebaseUser?,
                        onUserDeleted: () -> Unit) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var userDeleted by remember { mutableStateOf(false) }

    if (userDeleted) {
        LaunchedEffect(true) {
            Toast.makeText(context, "Account deleted successfully", Toast.LENGTH_SHORT).show()
            navController.navigate("LOGIN")
            }
        }
//    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 32.dp)
    ) {
        TextButton(
            onClick = { showDialog = true }
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
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        user?.delete()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    onUserDeleted() // ✅ Triggers user = null in parent
                                    userDeleted = true
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Error: ${task.exception?.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
