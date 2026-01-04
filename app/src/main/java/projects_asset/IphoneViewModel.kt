package projects_asset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class IphoneViewModel : ViewModel() {
    var iphones by mutableStateOf<List<Iphone>>(emptyList())
        private set
    init {
        loadIphones()
    }
    private fun loadIphones(){
        viewModelScope.launch {
            iphones = RetrofitClient.iphoneApi.getIphones()
        }
    }
}