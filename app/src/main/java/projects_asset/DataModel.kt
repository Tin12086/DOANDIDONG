package projects_asset

data class LoginRequest(
    val identifier: String,
    val password: String
)
data class LoginResponse(
    val status: String,
    val user: User?,
    val message: String
)

data class User(
    val username: String,
    val phoneNumber: String?,
    val email: String?,
    val full_name: String?,
)
data class RegisterResponse(
    val status: String,
    val message: String,
    val user: User?
)
data class RegisterRequest(
    val username: String,
    val password: String,
    val phoneNumber: String,
    val email: String?,
    val full_name: String?
)
data class Iphone(
    val id: Int,
    val name: String,
    val price: Int,
    val image: String,
    val isHot: Boolean
)

data class UpdateProfileRequest(
    val full_name: String,
    val email: String,
    val phoneNumber: String,
    val password: String? = null
)
data class UpdateProfileResponse(
    val success: Boolean,
    val message: String
)
