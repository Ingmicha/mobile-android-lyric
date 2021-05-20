package com.mura.android.lyrics.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.mura.android.lyrics.R
import com.mura.android.lyrics.databinding.FragmentLyricBinding
import com.mura.android.lyrics.lyric.ui.lyric.InsertLyricWorkManager.Companion.INSERT_LYRIC_WORK_TAG
import com.mura.android.lyrics.ui.base.BaseFragment
import com.mura.android.lyrics.utils.extentions.observe
import com.mura.android.lyrics.utils.extentions.toast
import kotlinx.coroutines.launch


class LyricFragment : BaseFragment() {

    lateinit var binding: FragmentLyricBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lyric, container, false)
        binding.lifecycleOwner = this
        binding.lyricViewModel = lyricViewModel
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
                lifecycleScope.launch {
                    lyricViewModel.getLyricByArtisTitle(artist, title)
                }
            } else {
                Snackbar.make(
                    requireView(),
                    "Debes ingresar un artista y un titulo",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun setObservers() {

        observe(lyricViewModel.isLoading){
            it?: return@observe

        }

        observe(
            WorkManager.getInstance(requireContext())
                .getWorkInfosForUniqueWorkLiveData(INSERT_LYRIC_WORK_TAG)
        ) {
            it ?: return@observe
            when (it[0].state) {
                WorkInfo.State.RUNNING -> {
                    toast("Guardando Resultado")
                }
                WorkInfo.State.SUCCEEDED -> {
                    toast("Resultado Guardado")
                }
                WorkInfo.State.FAILED -> {
                    toast("Error al Guardar")
                }
                else -> {
                    toast("Error inserperado")
                }
            }
        }

    }
}