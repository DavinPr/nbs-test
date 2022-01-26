package com.adityadavin.nbsmoviedb.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.adityadavin.nbsmoviedb.R
import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.CategoryMovie
import com.adityadavin.nbsmoviedb.databinding.ItemHomeCategoryBannerBinding
import com.adityadavin.nbsmoviedb.databinding.ItemHomeCategoryHorizontalBinding
import com.adityadavin.nbsmoviedb.utils.HorizontalSpaceDecoration

sealed class HomeViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class HomeBannerListViewHolder(
        private val binding: ItemHomeCategoryBannerBinding,
        private val onClick: ((Int) -> Unit)
    ) :
        HomeViewHolder(binding) {
        fun bind(category: CategoryMovie) {
            val bannerListAdapter = HomeBannerListAdapter()
            when (category.categoryItem) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    bannerListAdapter.setList(category.categoryItem.data)
                }
                is Resource.Error -> {
                    bannerListAdapter.setList(category.categoryItem.data)
                }
            }
            binding.homeBanner.adapter = bannerListAdapter
            binding.homeBannerIndicator.setViewPager2(binding.homeBanner)
            bannerListAdapter.onClick = onClick
        }
    }

    class HomeHorizontalListViewHolder(
        private val binding: ItemHomeCategoryHorizontalBinding,
        private val onClick: ((Int) -> Unit)
    ) :
        HomeViewHolder(binding) {
        fun bind(category: CategoryMovie) {
            binding.homeTvCategory.text = category.categoryTitle
            val movieListAdapter = HomeHorizontalListAdapter()
            when (category.categoryItem) {
                is Resource.Loading -> {
                    binding.homeRvCategory.visibility = View.INVISIBLE
                    binding.homeMessageCategory.visibility = View.GONE
                    binding.homeLoadingCategory.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.homeRvCategory.visibility = View.VISIBLE
                    binding.homeLoadingCategory.visibility = View.GONE
                    binding.homeMessageCategory.visibility = View.GONE
                    movieListAdapter.setList(category.categoryItem.data)
                }
                is Resource.Error -> {
                    binding.homeLoadingCategory.visibility = View.GONE
                    if (category.categoryItem.data.isNullOrEmpty()) {
                        binding.homeMessageCategory.visibility = View.VISIBLE
                    } else {
                        binding.homeRvCategory.visibility = View.VISIBLE
                        movieListAdapter.setList(category.categoryItem.data)
                    }
                }
            }
            binding.homeRvCategory.apply {
                layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                hasFixedSize()
                adapter = movieListAdapter
                addItemDecoration(
                    HorizontalSpaceDecoration(itemView.resources.getDimensionPixelSize(R.dimen.list_margin))
                )
            }
            movieListAdapter.onClick = onClick
        }
    }
}