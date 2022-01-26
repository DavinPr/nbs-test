package com.adityadavin.nbsmoviedb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityadavin.nbsmoviedb.R
import com.adityadavin.nbsmoviedb.core.utils.MOVIE_ID_KEY
import com.adityadavin.nbsmoviedb.databinding.FragmentHomeBinding
import com.adityadavin.nbsmoviedb.ui.detail.DetailActivity
import com.adityadavin.nbsmoviedb.ui.home.adapter.HomeListAdapter
import com.adityadavin.nbsmoviedb.utils.VerticalSpaceDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryListAdapter = HomeListAdapter {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, it)
            startActivity(intent)
        }

        viewModel.requestMoviesByCategory()
        viewModel.moviesByCategory.observe(viewLifecycleOwner) { list ->
            categoryListAdapter.setList(list)
        }

        binding.homeRvCategory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
            adapter = categoryListAdapter
            addItemDecoration(VerticalSpaceDecoration(resources.getDimensionPixelSize(R.dimen.list_margin)))
        }
    }
}