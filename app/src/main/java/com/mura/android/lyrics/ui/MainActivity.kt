package com.mura.android.lyrics.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.mura.android.lyrics.R
import com.mura.android.lyrics.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }

    fun showLinearProgressBar(show: Boolean) {
        binding.linearProgressIndicator.visibility = if (show) {
            View.VISIBLE
        } else
            View.GONE
    }

}