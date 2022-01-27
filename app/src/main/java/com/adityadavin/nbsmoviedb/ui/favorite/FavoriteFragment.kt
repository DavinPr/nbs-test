package com.adityadavin.nbsmoviedb.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityadavin.nbsmoviedb.R
import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.utils.MOVIE_ID_KEY
import com.adityadavin.nbsmoviedb.databinding.FragmentFavoriteBinding
import com.adityadavin.nbsmoviedb.ui.detail.DetailActivity
import com.adityadavin.nbsmoviedb.ui.favorite.adapter.FavoriteListAdapter
import com.adityadavin.nbsmoviedb.utils.Event
import com.adityadavin.nbsmoviedb.utils.VerticalSpaceDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteAdapter = FavoriteListAdapter()

        viewModel.requestMoviesFavorite()
        viewModel.favoriteMovie.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (it.data == null) {
                        Log.d("Favorite data", "null")
                    } else {
                        Log.d("Favorite data", "not null")
                    }
                    favoriteAdapter.setList(it.data)
                }
                is Resource.Error -> {
                    Log.d("Favorite data", it.message.toString())
                }
            }
        }

        viewModel.event.observe(viewLifecycleOwner) {
            if (it is Event.ShowToast) {
                it.show(requireContext())
                viewModel.clearEvent
            }
        }

        favoriteAdapter.onClick = {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, it)
            startActivity(intent)
        }

        favoriteAdapter.onDelete = {
            viewModel.deleteFavorite(it)
        }

        binding.favoriteRvMovie.apply {
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
            adapter = favoriteAdapter
            addItemDecoration(VerticalSpaceDecoration(resources.getDimensionPixelSize(R.dimen.list_margin)))
        }

        binding.favoriteSearchMovie.apply {
            setOnCloseListener {
                binding.favoriteSearchMessage.apply {
                    visibility = View.GONE
                    text = null
                }
                viewModel.requestMoviesFavorite()
                true
            }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.favoriteSearchMessage.apply {
                        visibility = View.VISIBLE
                        text = resources.getString(R.string.search_message, query)
                    }
                    if (!query.isNullOrEmpty()) {
                        viewModel.searchMoviesFavorite(query)
                    }
                    Log.d("Search", query.toString())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }
    }

}