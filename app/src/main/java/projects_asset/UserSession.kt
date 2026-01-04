package projects_asset

object UserSession {
    var username: String? = null
        private set
    var phoneNumber: String? = null
        private set
    var email: String? = null
        private set
    var fullName: String? = null
        private set

    fun login(
        username: String,
        phoneNumber: String,
        email: String,
        fullName: String
    ) {
        this.username = username
        this.phoneNumber = phoneNumber
        this.email = email
        this.fullName = fullName
    }

    fun logout() {
        username = null
        phoneNumber = null
        email = null
        fullName = null
    }

    val isLoggedIn: Boolean
        get() = !username.isNullOrEmpty()
}