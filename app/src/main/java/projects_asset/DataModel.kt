package projects_asset

data class LoginRequest(
    val identifier: String,
    val password: String
)
data class LoginResponse(
    val status: String,
    val user: User?
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