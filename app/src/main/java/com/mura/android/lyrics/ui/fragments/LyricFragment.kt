package com.mura.android.lyrics.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.mura.android.lyrics.R
import com.mura.android.lyrics.databinding.FragmentLyricBinding
import com.mura.android.lyrics.ui.MainActivity
import com.mura.android.lyrics.ui.base.BaseFragment
import kotlinx.coroutines.launch


class LyricFragment : BaseFragment() {

    lateinit var binding: FragmentLyricBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lyric, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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