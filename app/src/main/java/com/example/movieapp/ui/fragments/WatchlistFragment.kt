package com.example.movieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.FragmentUpcomingBinding
import com.example.movieapp.databinding.FragmentWatchlistBinding
import com.example.movieapp.ui.adapters.MovieAdapter
import com.example.movieapp.ui.adapters.SavedAdapter
import com.example.movieapp.ui.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistFragment : Fragment() {
    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: SavedAdapter

    val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()

        viewModel.getSavedMovies().observe(viewLifecycleOwner) { response ->
            movieAdapter.items = response
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAdapters() {
        movieAdapter = SavedAdapter()
        movieAdapter.onItemClickCallback = {
            findNavController().navigate(WatchlistFragmentDirections.actionWatchlistFragmentToMovieDetailsFragment(it.id))
        }
        binding.rvSavedMovies.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }
}