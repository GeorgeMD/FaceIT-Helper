package com.example.faceithelper.api.models

import android.os.Parcelable
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

@Parcelize
data class Game(
    val region: String,
    @Json(name = "faceit_elo") val faceitElo: Int,
    @Json(name = "game_player_id") val gamePlayerId: String,
    @Json(name = "game_player_name") val gamePlayerName: String,
    @Json(name = "game_profile_id") val gameProfileId: String,
    @Json(name = "skill_level") val skillLevel: Int,
    @Json(name = "skill_level_label") val skillLevelLabel: String
) : Parcelable {
    @IgnoredOnParcel
    var gameName: String = ""
}

@Parcelize
data class GameDetails(
    @Json(name = "short_label") val shortLabel: String,
    val assets: GameAssets
) : Parcelable

@Parcelize
data class GameAssets(
    val cover: String,
    @Json(name = "flag_img_l") val flagImgLarge: String
) : Parcelable

class GamesAdapter {
    @FromJson
    fun fromJson(value: Map<String, Game>): List<Game> {
        val games = ArrayList<Game>()

        for (entry in value.entries) {
            val gameName = entry.key
            val game = entry.value
            game.gameName = gameName
            games.add(game)
        }

        return games
    }
}