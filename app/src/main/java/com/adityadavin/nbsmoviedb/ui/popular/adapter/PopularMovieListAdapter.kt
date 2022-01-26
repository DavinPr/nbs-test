package com.adityadavin.nbsmoviedb.ui.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityadavin.nbsmoviedb.R
import com.adityadavin.nbsmoviedb.core.domain.model.Movie
import com.adityadavin.nbsmoviedb.databinding.ItemPopularMovieBinding
import com.bumptech.glide.Glide

class PopularMovieListAdapter :
    RecyclerView.Adapter<PopularMovieListAdapter.PopularItemViewHolder>() {

    private val itemList = ArrayList<Movie>()
    var onClick: ((Int) -> Unit)? = null

    fun setList(newList: List<Movie>?) {
        if (newList == null) return
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMovieListAdapter.PopularItemViewHolder =
        PopularItemViewHolder(
            ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(
        holder: PopularMovieListAdapter.PopularItemViewHolder,
        position: Int
    ) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size

    inner class PopularItemViewHolder(private val binding: ItemPopularMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.popularMovieTitle.text = movie.title
            binding.popularMovieRating.text =
                String.format(itemView.resources.getString(R.string.rating), movie.voteAverage)
            binding.popularMovieOverview.text = movie.overview
            Glide.with(itemView.context)
                .load(movie.posterPath)
                .into(binding.popularMoviePoster)
        }

        init {
            itemView.setOnClickListener {
                onClick?.invoke(itemList[bindingAdapterPosition].id)
            }
        }
    }

}