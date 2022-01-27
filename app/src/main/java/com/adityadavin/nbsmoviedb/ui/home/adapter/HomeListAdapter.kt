package com.adityadavin.nbsmoviedb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityadavin.nbsmoviedb.R
import com.adityadavin.nbsmoviedb.core.domain.model.CategoryMovie
import com.adityadavin.nbsmoviedb.core.utils.BANNER_TYPE
import com.adityadavin.nbsmoviedb.core.utils.HORIZONTAL_LIST_TYPE
import com.adityadavin.nbsmoviedb.databinding.ItemHomeCategoryBannerBinding
import com.adityadavin.nbsmoviedb.databinding.ItemHomeCategoryHorizontalBinding

class HomeListAdapter(private val onClick : ((Int) -> Unit)) :
    RecyclerView.Adapter<HomeViewHolder>() {

    private val itemList = ArrayList<CategoryMovie>()

    fun setList(newList: List<CategoryMovie>?) {
        if (newList == null) {
            itemList.addAll(listOf())
        } else {
            itemList.clear()
            itemList.addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return when (viewType) {
            R.layout.item_home_banner -> HomeViewHolder.HomeBannerListViewHolder(
                ItemHomeCategoryBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onClick
            )
            R.layout.item_home_category_horizontal -> HomeViewHolder.HomeHorizontalListViewHolder(
                ItemHomeCategoryHorizontalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onClick
            )
            else -> throw IllegalArgumentException("Invalid Viewtype Provided")
        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = itemList[position]
        when (holder) {
            is HomeViewHolder.HomeBannerListViewHolder -> {
                holder.bind(item)
            }
            is HomeViewHolder.HomeHorizontalListViewHolder -> {
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position].categoryType) {
            BANNER_TYPE -> R.layout.item_home_banner
            HORIZONTAL_LIST_TYPE -> R.layout.item_home_category_horizontal
            else -> throw IllegalArgumentException("Invalid Viewtype Provided")
        }
    }

}