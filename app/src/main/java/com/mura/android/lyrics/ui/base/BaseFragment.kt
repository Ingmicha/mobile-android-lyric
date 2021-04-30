package com.mura.android.lyrics.ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mura.android.lyrics.data.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    val mainViewModel: MainViewModel by viewModels()

}