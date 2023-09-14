package me.svbneelmane.instagramclone.data

data class UserData(
    var userId: String? = null,
    var name: String? = null,
    var userName: String? = null,
    var imageURL: String? = null,
    var bio: String? = null,
    var following: List<String>? = null,
) {
    fun toMap() = mapOf(
        "name" to name,
        "userId" to userId,
        "userName" to userName,
        "imageURL" to imageURL,
        "bio" to bio,
        "following" to following
    )
}
