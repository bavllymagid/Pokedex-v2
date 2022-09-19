package com.bvm.pokedex.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OfficialArtwork(
    val front_default: String
):Parcelable