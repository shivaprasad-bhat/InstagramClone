package me.svbneelmane.instagramclone

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import me.svbneelmane.instagramclone.data.Event
import me.svbneelmane.instagramclone.data.UserData
import java.lang.Exception
import javax.inject.Inject

const val USERS = "users"

@HiltViewModel
class InstaViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage
) : ViewModel() {

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val popupNotification = mutableStateOf<Event<String>?>(null)

    init {
//        auth.signOut()
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        currentUser?.uid?.let { uid ->
            getUserData(uid)
        }
    }

    fun onSignup(userName: String, email: String, password: String) {
        inProgress.value = true

        db.collection(USERS).whereEqualTo("userName", userName).get()
            .addOnSuccessListener { document ->
                if (document.size() > 0) {
                    handleException(customMessage = "User Name Already Exists")
                    inProgress.value = false
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                signedIn.value = true
                                createOrUpdateProfile(username = userName)
                            } else {
                                handleException(
                                    exception = task.exception,
                                    customMessage = "Signup failed"
                                )
                            }
                            inProgress.value = false
                        }
                }
            }
            .addOnFailureListener {

            }
    }

    private fun createOrUpdateProfile(
        username: String? = null,
        name: String? = null,
        bio: String? = null,
        imageURL: String? = null
    ) {
        val userId = auth.currentUser?.uid
        val userData = UserData(
            userId = userId,
            name = name ?: userData.value?.name,
            userName = username ?: userData.value?.userName,
            bio = bio ?: userData.value?.bio,
            imageURL = imageURL ?: userData.value?.imageURL,
            following = userData.value?.following
        )

        userId?.let { id ->
            inProgress.value = true
            db.collection(USERS).document(id).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        doc.reference.update(userData.toMap())
                            .addOnSuccessListener {
                                this.userData.value = userData
                                inProgress.value = false
                            }
                            .addOnFailureListener { e ->
                                handleException(e, customMessage = "Can't update user")
                                inProgress.value = false
                            }
                    } else {
                        db.collection(USERS).document(userId).set(userData.toMap())
                        getUserData(userId = userId)
                        inProgress.value = false
                    }
                }
                .addOnFailureListener { e ->
                    handleException(exception = e, "Exception from firebase")
                    inProgress.value = false
                }
        }
    }

    private fun getUserData(userId: String) {
        inProgress.value = true
        db.collection(USERS).document(userId).get()
            .addOnSuccessListener { doc ->
                val user = doc.toObject<UserData>()
                userData.value = user
                inProgress.value = false
            }
            .addOnFailureListener { e ->
                handleException(e, "Can't retrieve user data")
                inProgress.value = false
            }
    }


    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val errorMessage = exception?.localizedMessage ?: ""
        val message =
            if (customMessage.isEmpty()) errorMessage else "$customMessage : $errorMessage"
        popupNotification.value = Event(message)
    }
}