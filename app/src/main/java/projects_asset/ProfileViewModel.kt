package projects_asset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileViewModel: ViewModel() {

    var full_name by mutableStateOf("")
    var email by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var username by mutableStateOf("")
    var newPassword by mutableStateOf("")  // chỉ còn mật khẩu mới
    // Xóa: var oldPassword by mutableStateOf("")

    var loading by mutableStateOf(false)
    var message by mutableStateOf("")

    var updateSuccess by mutableStateOf(false)
        private set

    // Khởi tạo: Load dữ liệu từ UserSession
    init {
        loadUserData()
    }

    private fun loadUserData() {
        full_name = UserSession.fullName ?: ""
        email = UserSession.email ?: ""
        phoneNumber = UserSession.phoneNumber ?: ""
        username = UserSession.username ?: ""
    }

    fun updateProfile() {
        viewModelScope.launch {
            try {
                // Validate input
                if (full_name.isBlank() || email.isBlank() || phoneNumber.isBlank()) {
                    message = "Vui lòng điền đầy đủ thông tin"
                    return@launch
                }

                loading = true
                message = ""
                updateSuccess = false

                // Tạo request gửi lên server (KHÔNG có oldPassword)
                val request = UpdateProfileRequest(
                    phoneNumber = phoneNumber,
                    full_name = full_name,
                    email = email,
                    password = if (newPassword.isNotBlank()) newPassword else null
                )

                val response = RetrofitClient.userApi.updateProfile(request)
                updateSuccess = response.success
                message = response.message

                // Nếu cập nhật thành công, cập nhật lại UserSession
                if (updateSuccess) {
                    UserSession.login(
                        username = UserSession.username ?: "",
                        phoneNumber = phoneNumber,
                        email = email,
                        fullName = full_name
                    )
                }

            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> message = "Dữ liệu không hợp lệ"
                    401 -> message = "Không có quyền thực hiện"
                    409 -> message = "Email hoặc số điện thoại đã tồn tại"
                    else -> message = "Cập nhật thất bại: ${e.code()}"
                }
            } catch (e: Exception) {
                message = "Lỗi kết nối: ${e.message}"
                e.printStackTrace()
            } finally {
                loading = false
                // Reset password field
                newPassword = ""
            }
        }
    }

    fun clearState() {
        newPassword = ""
        loading = false
        message = ""
        updateSuccess = false
    }

    // Thêm hàm refresh để load lại dữ liệu
    fun refreshUserData() {
        loadUserData()
    }
}