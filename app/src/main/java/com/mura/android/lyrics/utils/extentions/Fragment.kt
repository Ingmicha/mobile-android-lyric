package com.mura.android.lyrics.utils.extentions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String) =
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
