package projects_Screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import projects_asset.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToHomePage: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Hiển thị thông báo từ ViewModel và xử lý chuyển trang
    LaunchedEffect(viewModel.message) {
        if (viewModel.message.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = viewModel.message,
                duration = SnackbarDuration.Short
            )
            // Nếu đăng nhập thành công, chuyển trang
            if (viewModel.user != null) {
                delay(1000)
                onNavigateToHomePage()
            }
        }
    }

    // Backup: tự động chuyển trang khi user được set
    LaunchedEffect(viewModel.user) {
        if (viewModel.user != null) {
            delay(1000)
            onNavigateToHomePage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Chào Mừng Trở Lại",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Username/Email field - sử dụng trực tiếp từ ViewModel
            OutlinedTextField(
                value = viewModel.identifier,
                onValueChange = { viewModel.identifier = it },
                label = { Text("Tài khoản hoặc Email") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Tài khoản")
                },
                isError = viewModel.message.isNotBlank() && viewModel.identifier.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password field - sử dụng trực tiếp từ ViewModel
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = { Text("Mật khẩu") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Mật khẩu")
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                isError = viewModel.message.isNotBlank() && viewModel.password.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Hiển thị mật khẩu"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Login Button với loading state
            Button(
                onClick = {
                    if (viewModel.identifier.isBlank() || viewModel.password.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Vui lòng nhập đầy đủ thông tin",
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else {
                        viewModel.login(onSuccess = { loginResponse ->
                            // Thành công - sẽ tự động chuyển trang trong LaunchedEffect
                        })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !viewModel.loading
            ) {
                if (viewModel.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text("ĐĂNG NHẬP", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(14.dp))

            // Link đến trang đăng ký
            TextButton(onClick = { onNavigateToRegister() }) {
                Text(
                    "Chưa có tài khoản? Đăng ký ngay",
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }

            Spacer(Modifier.height(18.dp))

            // Social login
            SocialLoginRow()
        }
    }
}

@Composable
fun SocialIcon(
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(50.dp)
            .border(1.dp, Color.LightGray, CircleShape)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun SocialLoginRow() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dòng kẻ ngang kèm chữ "Hoặc"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Color.LightGray)
            Text(
                text = " Hoặc ",
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.Gray,
                fontSize = 14.sp
            )
            HorizontalDivider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Color.LightGray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Row chứa 2 Icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nút Facebook
            SocialIcon(
                icon = Icons.Default.Facebook,
                tint = Color(0xFF1877F2),
                onClick = {}
            )

            Spacer(modifier = Modifier.width(24.dp))

            // Nút Email/Google
            SocialIcon(
                icon = Icons.Default.Email,
                tint = Color(0xFFDB4437),
                onClick = {}
            )
        }
    }
}