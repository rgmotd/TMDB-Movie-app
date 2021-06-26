package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.Constants.GENRE_MAP
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.ui.adapters.CastAdapter
import com.example.movieapp.ui.adapters.GenreAdapter
import com.example.movieapp.ui.viewmodels.MovieDetailsViewModel
import com.example.movieapp.ui.viewmodels.CastViewModelAssistedFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var castAdapter: CastAdapter
    lateinit var genreAdapter: GenreAdapter
    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var castFactory: CastViewModelAssistedFactory

    val movieDetailsViewModel: MovieDetailsViewModel by viewModels { MovieDetailsViewModel.Factory(castFactory, args.id) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()


        movieDetailsViewModel.movieCast.observe(viewLifecycleOwner) {
            castAdapter.items = it.cast
        }

        movieDetailsViewModel.movie.observe(viewLifecycleOwner) { movie ->
            Log.d("MovieDetailsFragment", "onViewCreated: $movie")
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/original" + movie.backdrop_path)
                .placeholder(R.color.colorPrimaryVariant)
                .into(binding.ivHeader)
            binding.apply {
                tvRating.text = movie.vote_average.toString()
                tvTitle.text = movie.title
                tvStoryText.text = movie.overview
            }

            binding.ivSave.setImageResource(if (movie.is_saved) R.drawable.saved else R.drawable.not_saved)
            binding.ivSave.setOnClickListener {
                binding.ivSave.setImageResource(if (movie.is_saved) R.drawable.not_saved else R.drawable.saved)
                movie.is_saved = !movie.is_saved
                movieDetailsViewModel.insert(movie)
            }

            genreAdapter.items = getGenres(movie.genre_ids)
        }
    }

    private fun setupAdapters() {
        genreAdapter = GenreAdapter()
        castAdapter = CastAdapter()
        binding.rvGenres.adapter = genreAdapter
        binding.rvCast.adapter = castAdapter
        binding.rvGenres.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCast.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getGenres(list: List<Int>): List<String> {
        val genres = mutableListOf<String>()
        list.forEach {
            genres.add(GENRE_MAP[it]!!)
        }
        return genres
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}