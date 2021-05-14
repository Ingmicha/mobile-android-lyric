package com.mura.android.lyrics.utils.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

fun Fragment.checkSelfPermissionCompat(permission: String) =
    ActivityCompat.checkSelfPermission(requireContext(), permission)

fun Fragment.shouldShowRequestPermissionRationaleCompat(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)

fun Fragment.requestPermissionsCompat(
    permissionsArray: Array<String>,
    requestCode: Int
) {
    ActivityCompat.requestPermissions(requireActivity(), permissionsArray, requestCode)
}