package com.example.faceithelper.api

import com.example.faceithelper.api.models.GameDetails
import com.example.faceithelper.api.models.GamesAdapter
import com.example.faceithelper.api.models.Player
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://open.faceit.com/data/v4/"
private const val API_KEY: String = "5e83fb4e-d459-4672-b86b-984befb3e6db"

private val moshi = Moshi.Builder()
    .add(GamesAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()

interface FaceitApiService {
    @GET("players")
    suspend fun getPlayerInfo(@Query("nickname") nickname: String): Player

    @GET("games/{game_id}")
    suspend fun getGameInfo(@Path("game_id") gameId: String): GameDetails
}

val faceitApi: FaceitApiService by lazy {
    val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(AddAuthInterceptor())
        .build()

    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(okhttpClient)
        .build()
        .create(FaceitApiService::class.java)
}

private class AddAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request
            .newBuilder()
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        return chain.proceed(newRequest)
    }
}