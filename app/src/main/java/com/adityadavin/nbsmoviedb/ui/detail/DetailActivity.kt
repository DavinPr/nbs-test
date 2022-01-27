package com.adityadavin.nbsmoviedb.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityadavin.nbsmoviedb.R
import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.DetailMovie
import com.adityadavin.nbsmoviedb.core.utils.MOVIE_ID_KEY
import com.adityadavin.nbsmoviedb.core.utils.fullScreen
import com.adityadavin.nbsmoviedb.core.utils.setMargin
import com.adityadavin.nbsmoviedb.core.utils.toPx
import com.adityadavin.nbsmoviedb.databinding.ActivityDetailBinding
import com.adityadavin.nbsmoviedb.ui.detail.adapter.DetailCastListAdapter
import com.adityadavin.nbsmoviedb.utils.Event
import com.adityadavin.nbsmoviedb.utils.HorizontalSpaceDecoration
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var castAdapter: DetailCastListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fullScreen()
        if (Build.VERSION.SDK_INT >= 30) {
            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->

                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

                view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply {
                    leftMargin = insets.left
                    bottomMargin = insets.bottom
                    rightMargin = insets.right
                }

                binding.btnBack.setMargin(
                    marginRight = 16.toPx.toInt(),
                    marginTop = (insets.top + 16.toPx.toInt()),
                    marginLeft = 16.toPx.toInt(),
                    marginBottom = 16.toPx.toInt()
                )
                WindowInsetsCompat.CONSUMED
            }
        }

        val id = intent.getIntExtra(MOVIE_ID_KEY, 0)

        castAdapter = DetailCastListAdapter()

        binding.btnBack.setOnClickListener { finish() }

        viewModel.checkFavorite(id)

        viewModel.requestDetail(id)
        viewModel.detail.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("Detail", resource.message.toString())
                }
                is Resource.Success -> {
                    if (resource.data != null) {
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

    private fun init(detail: DetailMovie) {
        binding.detailTitle.text = detail.title
        binding.detailRuntime.text = detail.runtime
        binding.detailOverview.text = detail.overview
        binding.detailGenres.text = detail.genres

        castAdapter.setList(detail.cast)

        Glide.with(this)
            .load(detail.backdropPath)
            .into(binding.detailPoster)

        binding.detailRvCast.apply {
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
            adapter = castAdapter
            addItemDecoration(
                HorizontalSpaceDecoration(resources.getDimensionPixelSize(R.dimen.list_margin))
            )
        }

        binding.detailBtnTrailer.setOnClickListener {
            val url = "https://www.youtube.com/results?search_query=" + detail.title + " trailer"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        viewModel.isFavorite.observe(this) { state ->
            if (!state) {
                binding.detailBtnFavorite.apply {
                    text = resources.getString(R.string.add_to_favorite)
                    icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_outline_add)
                }
            } else {
                binding.detailBtnFavorite.apply {
                    text = resources.getString(R.string.remove_from_favorite)
                    icon =
                        ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_outline_delete)
                }
            }
        }

        binding.detailBtnFavorite.setOnClickListener {
            val isFavorite = viewModel.isFavorite.value
            if (isFavorite != null) {
                if (isFavorite) {
                    viewModel.deleteFavorite(detail)
                } else {
                    viewModel.insertFavorite(detail)
                }
            }
        }

        viewModel.event.observe(this) {
            if (it is Event.ShowToast) {
                it.show(this)
                viewModel.clearEvent
            }
        }
    }
}