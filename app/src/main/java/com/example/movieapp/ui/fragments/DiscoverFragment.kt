package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.movieapp.databinding.FragmentDiscoverBinding
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.ui.adapters.MovieAdapter
import com.example.movieapp.ui.adapters.SliderAdapter
import com.example.movieapp.ui.other.GridSpacingItemDecoration
import com.example.movieapp.ui.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.abs

@AndroidEntryPoint
class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    lateinit var viewPagerMovieAdapter: SliderAdapter

    val viewModel: MovieViewModel by viewModels()

    val TAG = "DiscoverFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()

        viewModel.getPopularMovies().observe(viewLifecycleOwner) { response ->
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, response)
        }

        viewModel.topRatedMovies.observe(viewLifecycleOwner) { response ->
            viewPagerMovieAdapter.items = response.results
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAdapters() {
        // Setup recyclerview
        movieAdapter = MovieAdapter(true)
        movieAdapter.onItemClickCallback = {
            findNavController().navigate(DiscoverFragmentDirections.actionDiscoverFragmentToMovieDetailsFragment(it))
        }
        binding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            addItemDecoration(GridSpacingItemDecoration(3, 104, false))
        }


        // Setup viewpager
        viewPagerMovieAdapter = SliderAdapter()
        binding.vpSlider.apply {
            adapter = viewPagerMovieAdapter

            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(30))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.25f
            }

            setPageTransformer(compositePageTransformer)
        }
    }
}