package com.example.movieapp.ui.fragments

import android.os.Bundle
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
import com.example.movieapp.ui.viewmodels.CastViewModel
import com.example.movieapp.ui.viewmodels.CastViewModelAssistedFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var castAdapter: CastAdapter
    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var castFactory: CastViewModelAssistedFactory

    val viewModel: CastViewModel by viewModels { CastViewModel.Factory(castFactory, args.movie.id) }

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

        viewModel.movieCast.observe(viewLifecycleOwner) {
            castAdapter.items = it.cast
        }


        Glide.with(binding.root).load("https://image.tmdb.org/t/p/original" + args.movie.backdrop_path)
            .placeholder(R.color.colorPrimaryVariant)
            .into(binding.ivHeader)
        binding.apply {
            tvRating.text = args.movie.vote_average.toString()
            tvTitle.text = args.movie.title
            tvStoryText.text = args.movie.overview
        }
    }

    private fun setupAdapters() {
        val genreAdapter = GenreAdapter()
        castAdapter = CastAdapter()
        genreAdapter.items = getGenres(args.movie.genre_ids)
        binding.rvGenres.adapter = genreAdapter
        binding.rvCast.adapter = castAdapter
        binding.rvGenres.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCast.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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