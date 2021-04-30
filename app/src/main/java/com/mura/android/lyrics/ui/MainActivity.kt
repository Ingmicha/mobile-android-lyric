package com.mura.android.lyrics.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mura.android.lyrics.R
import com.mura.android.lyrics.data.viewModel.MainViewModel
import com.mura.android.lyrics.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    fun showLinearProgressBar(show: Boolean) {
        binding.linearProgressIndicator.visibility = if (show) {
            View.VISIBLE
        } else
            View.GONE
    }

}