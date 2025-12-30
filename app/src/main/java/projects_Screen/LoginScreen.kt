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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onNavigateToHomePage: () -> Unit, onNavigateToRegister: () -> Unit) {
    // Khai báo biến lưu trạng thái nhập liệu
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize().padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Chào Mừng Trở Lại",
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Ô nhập tên đăng nhập hoặc Email
            OutlinedTextField(
                value = username,
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "")
                },
                onValueChange = { username = it },
                label = { Text("Tên đăng nhập") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ô nhập mật khẩu
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "")
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(image, null)
                    }
                },
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Nút Đăng nhập
            Button(
                onClick = {
                    if(username.isEmpty() || password.isEmpty()){
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Vui lòng nhập đầy đủ thông tin",
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else{
                        onNavigateToHomePage()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("ĐĂNG NHẬP")
            }
            Spacer(Modifier.height(14.dp))
            TextButton(
                onClick = {
                    onNavigateToRegister()
                },

            ) {
                Text("Chưa có tài khoản, đăng ký ngay", style = TextStyle(textDecoration = TextDecoration.Underline))
            }
            Spacer(Modifier.height(18.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                SocialLoginRow()
            }
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
            .border(1.dp, Color.LightGray, CircleShape) // Tạo viền tròn nhẹ
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
fun SocialLoginRow(
) {
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
            // Nút Facebook (Màu xanh chuẩn)
            SocialIcon(
                icon = Icons.Default.Facebook, // Lưu ý: Cần import thư mục Icons mở rộng hoặc dùng painterResource nếu có file svg
                tint = Color(0xFF1877F2),
                onClick = {}
            )

            Spacer(modifier = Modifier.width(24.dp))

            // Nút Email/Google (Màu đỏ cam)
            SocialIcon(
                icon = Icons.Default.Email,
                tint = Color(0xFFDB4437), // Màu đỏ Google
                onClick = {}
            )
        }
    }
}