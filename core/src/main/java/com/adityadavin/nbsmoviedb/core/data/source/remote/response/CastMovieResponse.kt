package com.adityadavin.nbsmoviedb.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CastMovieResponse(

    @field:SerializedName("cast")
    val cast: List<CastItemResponse> = listOf(),

    @field:SerializedName("id")
    val id: Int = 0,
)

data class CastItemResponse(

    @field:SerializedName("cast_id")
    val castId: Int = 0,

    @field:SerializedName("character")
    val character: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("profile_path")
    val profilePath: String? = null,

    @field:SerializedName("id")
    val id: Int = 0,
)
