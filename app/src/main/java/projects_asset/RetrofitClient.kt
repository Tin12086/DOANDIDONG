package projects_asset

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8000/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val iphoneApi: IphonesService by lazy {
        retrofit.create(IphonesService::class.java)
    }

    val userApi: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}
