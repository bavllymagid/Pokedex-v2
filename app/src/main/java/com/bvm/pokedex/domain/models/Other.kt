package com.bvm.pokedex.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Other(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
):Parcelable