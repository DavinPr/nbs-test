package com.adityadavin.nbsmoviedb.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(

	@field:SerializedName("page")
	val page: Int = 0,

	@field:SerializedName("total_pages")
	val totalPages: Int = 0,

	@field:SerializedName("results")
	val results: List<MovieResultResponse> = listOf(),

	@field:SerializedName("total_results")
	val totalResults: Int = 0
)

data class MovieResultResponse(

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("video")
	val video: Boolean = false,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int> = listOf(),

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double = 0.0,

	@field:SerializedName("vote_average")
	val voteAverage: Double = 0.0,

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("adult")
	val adult: Boolean = false,

	@field:SerializedName("vote_count")
	val voteCount: Int = 0
)
