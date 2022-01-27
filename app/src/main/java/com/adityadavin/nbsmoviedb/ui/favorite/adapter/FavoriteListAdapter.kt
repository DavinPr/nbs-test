package com.adityadavin.nbsmoviedb.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityadavin.nbsmoviedb.core.domain.model.FavoriteMovie
import com.adityadavin.nbsmoviedb.databinding.ItemFavoriteMovieBinding
import com.bumptech.glide.Glide

class FavoriteListAdapter : RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {

    private val list = ArrayList<FavoriteMovie>()
    fun setList(newList: List<FavoriteMovie>?) {
        if (newList == null) {
            list.addAll(listOf())
        } else {
            list.clear()
            list.addAll(newList)
        }
        notifyDataSetChanged()
    }

    var onClick: ((Int) -> Unit)? = null

    var onDelete: ((FavoriteMovie) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteListAdapter.FavoriteViewHolder = FavoriteViewHolder(
        ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FavoriteListAdapter.FavoriteViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    inner class FavoriteViewHolder(private val binding: ItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: FavoriteMovie) {
            Glide.with(itemView.context)
                .load(movie.backdropPath)
                .into(binding.favoritePoster)
            binding.favoriteTitle.text = movie.title
            binding.favoriteDate.text = movie.releaseDate
            binding.favoriteGenres.text = movie.genres
        }

        init {
            itemView.setOnClickListener {
                onClick?.invoke(list[bindingAdapterPosition].movieId)
            }
            binding.favoriteBtnDelete.setOnClickListener {
                onDelete?.invoke(list[bindingAdapterPosition])
            }
        }
    }
}