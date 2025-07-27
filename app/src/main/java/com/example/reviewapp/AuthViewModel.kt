package com.example.reviewapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.livedata.observeAsState
import com.google.firebase.auth.UserProfileChangeRequest
import kotlin.text.isEmpty

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _displayName = MutableLiveData<String>()
    val displayName: LiveData<String> = _displayName


    init {
        checkAuthStatus()
    }

fun checkAuthStatus() {
    val currentUser = auth.currentUser
    if (currentUser == null) {
        _authState.value = AuthState.Unauthenticated
    } else {
        _displayName.value = currentUser.displayName ?: ""
        _authState.value = AuthState.Authenticated
    }
}

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and Password can't be empty")
            onResult(false)
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    onResult(true)
                    _displayName.value = auth.currentUser?.displayName ?: ""

                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                    onResult(false)
                }
            }
    }
    fun signup(username: String, email: String, password: String) {
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    auth.currentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                _authState.value = AuthState.Authenticated
                                _displayName.value = auth.currentUser?.displayName ?: ""
                            } else {
                                _authState.value = AuthState.Error("Failed to update profile")
                            }
                        }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }



    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun sendPasswordResetEmail(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.message ?: "Error sending reset email.")
                }
            }
    }
}

sealed class AuthState {
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
