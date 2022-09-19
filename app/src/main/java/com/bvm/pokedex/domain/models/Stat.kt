package com.bvm.pokedex.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatX
):Parcelable