package com.adityadavin.nbsmoviedb.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adityadavin.nbsmoviedb.databinding.FragmentMoreBinding
import com.bumptech.glide.Glide


class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load("https://avatars.githubusercontent.com/u/56529159?s=400&u=d220528140f3e825d0acbdc12a25bbab3f7ca461&v=4")
            .into(binding.moreProfileImg)
    }

}