package com.adityadavin.nbsmoviedb.ui.popular

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.adityadavin.nbsmoviedb.R
import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.utils.MOVIE_ID_KEY
import com.adityadavin.nbsmoviedb.databinding.FragmentPopularBinding
import com.adityadavin.nbsmoviedb.ui.detail.DetailActivity
import com.adityadavin.nbsmoviedb.ui.popular.adapter.PopularMovieListAdapter
import com.adityadavin.nbsmoviedb.utils.GridSpaceDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PopularViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.requestMoviesPopular()

        val popularListAdapter = PopularMovieListAdapter()
        popularListAdapter.onClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, it)
            startActivity(intent)
        }

        viewModel.moviesPopular.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    popularListAdapter.setList(it.data)
                }
                is Resource.Error -> {
                    if (it.data.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "Data not found", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        popularListAdapter.setList(it.data)
                    }
                }
            }
        }

        binding.popularRvMovie.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            hasFixedSize()
            adapter = popularListAdapter
            addItemDecoration(
                GridSpaceDecoration(
                    resources.getDimensionPixelSize(R.dimen.grid_margin),
                    2
                )
            )
        }


        binding.popularSearchMovie.apply {
            val searchIcon = this.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
            searchIcon.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.white),
                PorterDuff.Mode.SRC_IN
            )
            setOnCloseListener {
                binding.popularSearchMessage.apply {
                    visibility = View.GONE
                    text = null
                }
                viewModel.requestAllMoviesFromLocal()
                true
            }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.popularSearchMessage.apply {
                        visibility = View.VISIBLE
                        text = resources.getString(R.string.search_message, query)
                    }
                    if (!query.isNullOrEmpty()) {
                        viewModel.searchMoviesPopular(query)
                    }
                    Log.d("Search", query.toString())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }

    }
}