package com.example.movieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.databinding.FragmentUpcomingBinding
import com.example.movieapp.ui.adapters.CastAdapter
import com.example.movieapp.ui.adapters.GenreAdapter
import com.example.movieapp.ui.adapters.MovieAdapter
import com.example.movieapp.ui.other.GridSpacingItemDecoration
import com.example.movieapp.ui.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter

    val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()

        viewModel.getUpcomingMovies().observe(viewLifecycleOwner) { response ->
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, response)
        }
    }

    private fun setupAdapters() {
        movieAdapter = MovieAdapter(false)
        movieAdapter.onItemClickCallback = {
            findNavController().navigate(UpcomingFragmentDirections.actionUpcomingFragmentToMovieDetailsFragment(it))
        }
        binding.rvMoviesUpcoming.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}