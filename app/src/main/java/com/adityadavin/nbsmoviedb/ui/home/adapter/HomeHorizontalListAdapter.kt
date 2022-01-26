package com.adityadavin.nbsmoviedb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityadavin.nbsmoviedb.core.domain.model.Movie
import com.adityadavin.nbsmoviedb.databinding.ItemHomeMovieBinding
import com.bumptech.glide.Glide

class HomeHorizontalListAdapter :
    RecyclerView.Adapter<HomeHorizontalListAdapter.HomeHorizontalItemViewHolder>() {

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
    ): HomeHorizontalItemViewHolder =
        HomeHorizontalItemViewHolder(
            ItemHomeMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: HomeHorizontalItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size

    inner class HomeHorizontalItemViewHolder(private val binding: ItemHomeMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(itemView.context)
                .load(movie.posterPath)
                .into(binding.homeMoviePoster)
        }

        init {
            itemView.setOnClickListener {
                onClick?.invoke(itemList[bindingAdapterPosition].id)
            }
        }
    }
}