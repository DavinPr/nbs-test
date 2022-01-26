package com.adityadavin.nbsmoviedb.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityadavin.nbsmoviedb.R
import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.databinding.FragmentFavoriteBinding
import com.adityadavin.nbsmoviedb.ui.favorite.adapter.FavoriteListAdapter
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

        viewModel.favoriteMovie.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    favoriteAdapter.setList(it.data)
                }
                is Resource.Error -> {}
            }
        }

        binding.favoriteRvMovie.apply {
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
            adapter = favoriteAdapter
            addItemDecoration(VerticalSpaceDecoration(resources.getDimensionPixelSize(R.dimen.list_margin)))
        }
    }

}