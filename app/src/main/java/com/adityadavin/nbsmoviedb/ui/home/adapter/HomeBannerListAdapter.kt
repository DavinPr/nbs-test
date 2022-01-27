package com.adityadavin.nbsmoviedb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityadavin.nbsmoviedb.core.domain.model.Movie
import com.adityadavin.nbsmoviedb.databinding.ItemHomeBannerBinding
import com.bumptech.glide.Glide

class HomeBannerListAdapter :
    RecyclerView.Adapter<HomeBannerListAdapter.HomeBannerItemViewHolder>() {

    private val itemList = ArrayList<Movie>()
    var onClick: ((Int) -> Unit)? = null

    fun setList(newList: List<Movie>?) {
        if (newList == null) {
            itemList.addAll(listOf())
        } else {
            itemList.clear()
            itemList.addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerItemViewHolder =
        HomeBannerItemViewHolder(
            ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: HomeBannerItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size

    inner class HomeBannerItemViewHolder(private val binding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(itemView.context)
                .load(movie.backdropPath)
                .into(binding.homeImgBanner)
        }

        init {
            itemView.setOnClickListener {
                onClick?.invoke(itemList[bindingAdapterPosition].id)
            }
        }
    }

}