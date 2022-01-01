package com.example.faceithelper.api.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    @Json(name = "player_id") val playerId: String,
    val nickname: String,
    val avatar: String,
    val country: String,
    val games: List<Game>
) : Parcelable