package com.adityadavin.nbsmoviedb.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityadavin.nbsmoviedb.R
import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.DetailMovie
import com.adityadavin.nbsmoviedb.core.utils.MOVIE_ID_KEY
import com.adityadavin.nbsmoviedb.databinding.ActivityDetailBinding
import com.adityadavin.nbsmoviedb.ui.detail.adapter.DetailCastListAdapter
import com.adityadavin.nbsmoviedb.utils.HorizontalSpaceDecoration
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private val viewModel : DetailViewModel by viewModel()
    private lateinit var castAdapter : DetailCastListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(MOVIE_ID_KEY, 0)

        castAdapter = DetailCastListAdapter()

        viewModel.getDetail(id).observe(this){ resource ->
            when(resource) {
                is Resource.Loading -> {
                    Log.d("Detail", resource.message.toString())
                }
                is Resource.Success -> {
                    if (resource.data != null){
                        init(resource.data!!)
                    }
                    Log.d("Detail", resource.data.toString())
                }
                is Resource.Error -> {
                    Log.d("Detail", resource.message.toString())
                }
            }
        }

    }

    private fun init(detail : DetailMovie){
        binding.detailTitle.text = detail.title
        binding.detailRuntime.text = detail.runtime
        binding.detailOverview.text = detail.overview
        binding.detailGenres.text = detail.genres

        castAdapter.setList(detail.cast)

        Glide.with(this)
            .load(detail.backdropPath)
            .into(binding.detailPoster)

        binding.detailRvCast.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
            adapter = castAdapter
            addItemDecoration(
                HorizontalSpaceDecoration(resources.getDimensionPixelSize(R.dimen.list_margin))
            )
        }
    }
}