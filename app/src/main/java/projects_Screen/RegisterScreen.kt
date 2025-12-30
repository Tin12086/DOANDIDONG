package projects_Screen

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import projects_asset.RegisterViewModel

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
){
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var full_name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    LaunchedEffect(viewModel.message) {
        if (viewModel.message.isNotEmpty()) {
            snackbarHostState.showSnackbar(viewModel.message)
        }
        else if (viewModel.message.contains("Đăng ký thành công")) {
            delay(1500) // Chờ 1.5 giây để người dùng đọc thông báo
            onNavigateToLogin()
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        paddingValues ->
        Column(
            modifier =  Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()).padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "TẠO TÀI KHOẢN MỚI",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                value = full_name,
                onValueChange = {full_name = it},
                label = { Text("Họ và tên:")},
                leadingIcon = {Icon(Icons.Filled.Person, contentDescription = "")},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = {Text("Email:")},
                leadingIcon = {Icon(Icons.Default.Email, contentDescription = "")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Số điện thoại") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = {username = it},
                label = {Text("Tài khoản")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {Icon(Icons.Filled.VerifiedUser, contentDescription = "")}
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Mật khẩu
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(image, null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Xác nhận mật khẩu
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Xác nhận mật khẩu") },
                leadingIcon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    val image = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(image, null)
                    }
                },
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if(password != confirmPassword) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Mật khẩu không trùng, không hợp lệ",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    else if(full_name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Chưa điền đầy đủ thông tin",
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Email không tồn tại",
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else if(phoneNumber.length != 10 || !phoneNumber.all { it.isDigit() }){
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Số điện thoại không hợp lệ",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    else if(password.length < 6){
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Mật khẩu phải dài hơn 6 ký tự",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    else{
                        viewModel.register(
                            username = username,
                            password = password,
                            phoneNumber = phoneNumber,
                            email = email,
                            full_name = full_name,
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(54.dp),
            ) {
                Text("ĐĂNG KÝ", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            }
        }
    }
}