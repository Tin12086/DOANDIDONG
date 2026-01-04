package projects_Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import projects_asset.Iphone
import projects_asset.IphoneViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MenuScreen(viewModel: IphoneViewModel = viewModel(), navController: NavController) {
    val iphones = viewModel.iphones
    val hotDeals = iphones.filter { it.isHot }

    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = { HomeBottomBar(navController) }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Mục 1: Search Bar (chiếm 2 cột)
            item(span = { GridItemSpan(2) }) {
                Column {
                    SearchBar()
                    Spacer(Modifier.height(12.dp))
                }
            }

            // Mục 2: Hàng khuyến mãi (chiếm 2 cột)
            item(span = { GridItemSpan(2) }) {
                Column {
                    SectionTitle("Hàng khuyến mãi")
                    HotDealRow(hotDeals)
                    Spacer(Modifier.height(16.dp))
                    SectionTitle("Gợi ý cho bạn")
                }
            }

            // Mục 3: Danh sách sản phẩm (tự động chia 2 cột)
            items(iphones) { item ->
                IphoneCard(item)
            }
        }
    }
}

@Composable
fun IphoneCard(item: Iphone) {
    val locale = Locale.Builder().setLanguage("vi").setRegion("VN").build()

    // item.price đã là Int → convert sang Long để format
    val priceFormatted = NumberFormat.getInstance(locale).format(item.price.toLong())
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            AsyncImage(
                // Đảm bảo URL chính xác
                model = item.image,
                contentDescription = item.name,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(item.name, maxLines = 2, minLines = 2, fontWeight = FontWeight.Medium)
                Text(
                    "$priceFormatted đ",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HotDealItem(item: Iphone) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .padding(end = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = "",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Text(item.name, maxLines = 1, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HotDealRow(iphones: List<Iphone>) {
    LazyRow {
        items(iphones) { item ->
            HotDealItem(item)
        }
    }
}

// --- Các component nhỏ khác giữ nguyên ---
@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "E-Commerce", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Row {
            IconButton(onClick = {}) { Icon(Icons.Default.ShoppingCart, contentDescription = "", tint = Color.Gray) }
            IconButton(onClick = {}) { Icon(Icons.Default.Favorite, contentDescription = "", tint = Color.Red) }
        }
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Tìm kiếm sản phẩm") },
        trailingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50)
    )
}

@Composable
fun SectionTitle(title: String) {
    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun HomeBottomBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, "") }, label = { Text("Trang chủ") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Favorite, "") }, label = { Text("Yêu thích") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.ShoppingCart, "") }, label = { Text("Giỏ hàng") })
        NavigationBarItem(selected = false, onClick = {
            navController.navigate("update_profile"){
                launchSingleTop = true
            }
        }, icon = { Icon(Icons.Default.Person, "") }, label = { Text("Tài khoản") })
    }
}
