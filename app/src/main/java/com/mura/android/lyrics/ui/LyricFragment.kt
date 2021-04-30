package com.mura.android.lyrics.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mura.android.lyrics.R
import com.mura.android.lyrics.data.viewModel.MainViewModel
import com.mura.android.lyrics.databinding.FragmentLyricBinding
import com.mura.android.lyrics.databinding.FragmentLyricListBinding
import com.mura.android.lyrics.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


class LyricFragment : BaseFragment() {

    lateinit var binding: FragmentLyricBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lyric, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLyricBinding.bind(view)

        setOnClickListeners()

        setObservers()

    }

    private fun setOnClickListeners() {
        binding.searchButton.setOnClickListener {
            val artist = binding.artistEditText.text.toString()
            val title = binding.tittleEditText.text.toString()

            if (artist.trim().isNotEmpty() && title.trim().isNotEmpty()) {
                (activity as MainActivity).showLinearProgressBar(true) //  View.GONE
                lifecycleScope.launch {
                    mainViewModel.onSearchByArtistAndTitle(artist, title)
                }
            } else {
                Snackbar.make(requireView(), "Debes ingresar un artista y un titulo", Snackbar.LENGTH_SHORT)
                    .show()
            }


        }
    }

    private fun setObservers() {

        mainViewModel.lyric.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).showLinearProgressBar(false) //  View.GONE
            binding.resultTextView.text = it
        })

        mainViewModel.error.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).showLinearProgressBar(false) //  View.GONE

            Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT)
                .show()
        })
    }

}