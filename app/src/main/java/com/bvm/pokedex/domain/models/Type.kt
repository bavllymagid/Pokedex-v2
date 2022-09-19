package com.bvm.pokedex.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Type(
    val slot: Int,
    val type: TypeX
):Parcelable