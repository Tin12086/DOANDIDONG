package projects_asset

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel : ViewModel() {
    var user by mutableStateOf<LoginResponse?>(null)
    var identifier by mutableStateOf("")
    var password by mutableStateOf("")
    var message by mutableStateOf("")
    var loading by mutableStateOf(false)

    fun login(onSuccess: (LoginResponse) -> Unit) {
        // Reset message
        message = ""

        // Validate input
        if (identifier.isBlank() || password.isBlank()) {
            message = "Vui lòng nhập tài khoản và mật khẩu"
            return
        }

        viewModelScope.launch {
            try {
                loading = true

                val request = LoginRequest(identifier.trim(), password)
                val response = RetrofitClient.userApi.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val userData = response.body()!!

                    // Kiểm tra theo status từ server
                    if (userData.status == "success" || userData.status == "Success") {
                        if (userData.user != null) {
                            // Lưu session
                            UserSession.login(
                                username = userData.user.username,
                                phoneNumber = userData.user.phoneNumber ?: "",
                                email = userData.user.email ?: "",
                                fullName = userData.user.full_name ?: ""
                            )
                            user = userData
                            onSuccess(userData)
                        } else {
                            message = "Tài khoản không có thông tin"
                        }
                    } else {
                        // Server trả về lỗi
                        message = userData.message.ifEmpty { "Tài khoản hoặc mật khẩu không đúng" }
                    }
                } else {
                    // Lỗi HTTP
                    val errorBody = response.errorBody()?.string()
                    message = if (!errorBody.isNullOrEmpty()) {
                        try {
                            JSONObject(errorBody).getString("message")
                        } catch (e: Exception) {
                            "Tài khoản hoặc mật khẩu không đúng"
                        }
                    } else {
                        "Tài khoản hoặc mật khẩu không đúng"
                    }
                }
            } catch (e: Exception) {
                message = "Không thể kết nối đến máy chủ"
                e.printStackTrace()
            } finally {
                loading = false
            }
        }
    }

    // Reset form
    fun reset() {
        identifier = ""
        password = ""
        message = ""
        loading = false
    }
}