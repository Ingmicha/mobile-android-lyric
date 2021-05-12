package com.mura.android.lyrics.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.mura.android.lyrics.R
import com.mura.android.lyrics.data.model.Lyric
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lyric_save, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.item = Lyric(artist = args.artist,title = args.title,lyric = args.lyric)

    }

}