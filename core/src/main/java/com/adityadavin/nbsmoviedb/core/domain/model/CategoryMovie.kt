package com.adityadavin.nbsmoviedb.core.domain.model

import com.adityadavin.nbsmoviedb.core.data.Resource

data class CategoryMovie(
    val categoryType : Int,
    val categoryTitle: String?,
    val categoryItem: Resource<List<Movie>>
)
