package com.mura.android.lyrics.ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mura.android.lyrics.lyric.ui.lyric.LyricViewModel
import com.mura.android.lyrics.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    val lyricViewModel: LyricViewModel by activityViewModels()

}