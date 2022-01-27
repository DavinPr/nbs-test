package com.adityadavin.nbsmoviedb.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityadavin.nbsmoviedb.core.domain.model.Casts
import com.adityadavin.nbsmoviedb.databinding.ItemDetailCastBinding
import com.bumptech.glide.Glide

class DetailCastListAdapter :
    RecyclerView.Adapter<DetailCastListAdapter.DetailCastItemViewHolder>() {

    private val itemList = ArrayList<Casts>()
    var onClick: ((Int) -> Unit)? = null

    fun setList(newList: List<Casts>?) {
        if (newList == null) {
            itemList.addAll(listOf())
        } else {
            itemList.clear()
            itemList.addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailCastItemViewHolder =
        DetailCastItemViewHolder(
            ItemDetailCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DetailCastItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size

    inner class DetailCastItemViewHolder(private val binding: ItemDetailCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Casts) {
            binding.castName.text = movie.name
            Glide.with(itemView.context)
                .load(movie.profilePath)
                .into(binding.castProfile)
        }

        init {
            itemView.setOnClickListener {
                onClick?.invoke(itemList[bindingAdapterPosition].id)
            }
        }
    }
}