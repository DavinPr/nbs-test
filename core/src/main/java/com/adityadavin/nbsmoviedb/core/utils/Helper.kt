package com.adityadavin.nbsmoviedb.core.utils

import com.adityadavin.nbsmoviedb.core.domain.model.Genres

fun Int.runtimeFormat() : String{
    return if (this > 60) {
        val hours = this / 60
        val minutes = this % 60
        "${hours}h ${minutes}m"
    } else {
        "${this}m"
    }
}

fun List<Genres>.genreFormat() : String{
    return this.joinToString()
}