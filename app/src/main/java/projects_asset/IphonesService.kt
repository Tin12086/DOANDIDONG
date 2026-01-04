package projects_asset

import retrofit2.http.GET
import retrofit2.http.Path

interface IphonesService {
    @GET("iphones")
    suspend fun getIphones(): List<Iphone>

    @GET("iphones/{id}")
    suspend fun getIphoneDetail(
        @Path("id") id: Int
    ): Iphone
}