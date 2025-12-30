package projects_asset

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    var message by mutableStateOf("")
        private set
    var loading by mutableStateOf(false)
        private set
    fun login(username: String, password: String){
        viewModelScope.launch {
            try{
                loading = true
                val response = RetrofitClient.api.login(LoginRequest(identifier = username, password = password))
                if(response.isSuccessful) {
                    val loginBody = response.body()
                    if(loginBody?.status == "success" && loginBody.user != null){
                        message = "Đăng nhập thành công"
                    }
                    else {
                        message = "Sai tài khoản hoặc mật khẩu"
                    }
                } else {
                    message = "Sai tài khoản hoặc mật khẩu, lỗi 401, 400, 500"
                }
            } catch (e: Exception){
                message = "Không thể kết nối server"
            }
            finally {
                loading = false
            }
        }
    }
}