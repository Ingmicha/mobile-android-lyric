package com.mura.android.lyrics.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mura.android.lyrics.R
import com.mura.android.lyrics.databinding.FragmentLyricSaveBinding
import com.mura.android.lyrics.ui.base.BaseFragment

class LyricFindFragment : BaseFragment() {

    lateinit var binding: FragmentLyricSaveBinding

    private val args: LyricFindFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lyric_save, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLyricSaveBinding.bind(view)

        binding.artistText.text = args.artist
        binding.titleText.text = args.title
        binding.resultTextView.text = args.lyric

    }

}