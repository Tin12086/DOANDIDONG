package projects_asset

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterViewModel : ViewModel() {
    var message by mutableStateOf("")
        private set
    var loading by mutableStateOf(false)
        private set

    fun register(
        username: String,
        password: String,
        phoneNumber: String,
        email: String,
        full_name: String
    ){
        viewModelScope.launch {
            try {
                loading = true
                val response = RetrofitClient.api.register(RegisterRequest(
                    username = username, password = password, phoneNumber = phoneNumber,
                    email = email, full_name = full_name
                ))
                if(response.isSuccessful){
                    message = response.body()?.message ?: "Đăng ký thành công"
                }else {
                    val errorJson = response.errorBody()?.string()
                    val errorMessage = try {
                        JSONObject(errorJson.orEmpty()).getString("message")
                    } catch (e: Exception) {
                        "Đăng ký thất bại"
                    }
                    message = errorMessage
                }
            } catch (e: Exception){
                message = "Không thể kết nối với Server"
            } finally {
                loading = false
            }
        }
    }
}