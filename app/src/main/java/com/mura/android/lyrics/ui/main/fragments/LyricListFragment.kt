package com.mura.android.lyrics.ui.main.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.mura.android.lyrics.R
import com.mura.android.lyrics.databinding.FragmentLyricListBinding
import com.mura.android.lyrics.ui.base.BaseFragment
import com.mura.android.lyrics.ui.main.adapter.LyricAdapter
import com.mura.android.lyrics.ui.viewModel.MainViewModel
import com.mura.android.lyrics.utils.extentions.checkSelfPermissionCompat
import com.mura.android.lyrics.utils.extentions.isGPSEnabled
import com.mura.android.lyrics.utils.extentions.requestPermissionsCompat

class LyricListFragment : BaseFragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: FragmentLyricListBinding

    private lateinit var adapter: LyricAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lyric_list, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.stopTrackLocation()

        //** Set the adapter of the RecyclerView
        setAdapter()

        //** Set the Layout Manager of the RecyclerView
        setRVLayoutManager()

        //** Set Observers
        setObservers()

        //**Set Listeners
        setListeners()

        //CheckPermission
        checkPermission()
    }

    private fun setAdapter() {
        adapter = LyricAdapter(this)
        adapter.notifyDataSetChanged()
        binding.recyclerViewLyricList.adapter = adapter
    }

    private fun setRVLayoutManager() {
        val mLayoutManager = LinearLayoutManager(binding.recyclerViewLyricList.context)
        binding.recyclerViewLyricList.layoutManager = mLayoutManager
        binding.recyclerViewLyricList.setHasFixedSize(true)
    }

    private fun setListeners() {

        binding.floatingActionButton.setOnClickListener {
            mainViewModel.stopTrackLocation()
            //findNavController().navigate(R.id.action_lyricListFragment_to_lyricFragment)
        }

    }

    private fun setObservers() {

        WorkManager.getInstance(requireContext())
            .getWorkInfosByTagLiveData(MainViewModel.LOCATION_WORK_TAG).observe(viewLifecycleOwner,
            Observer {
                it ?: return@Observer
                if (it.isEmpty()) return@Observer
                Log.d("WORK-MANAGER", it[0].state.toString())
            })

//        mainViewModel.responseDatabase.observe(viewLifecycleOwner, Observer {
//            //(activity as MainActivity).showLinearProgressBar(false) //  View.GONE
//            if (it.status == Resource.Status.SUCCESS)
//                if ((it.data as List<*>).isNotEmpty()) {
//                    adapter.updateData(it.data)
//                    binding.recyclerViewLyricList.visibility = View.VISIBLE
//                } else {
//                    binding.emptyListTextView.visibility = View.VISIBLE
//                    binding.emptyImageView.visibility = View.VISIBLE
//                }
//        })
    }


    private fun getFromLocation() =
        if (requireActivity().isGPSEnabled()) mainViewModel.trackLocation() else mainViewModel.locationSetup()


    private fun checkPermission() {
        if (checkSelfPermissionCompat(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already available, start camera preview
            getFromLocation()
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            requestPermissionsCompat(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_LOCATION
            )

        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            requestPermissionsCompat(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_LOCATION
            )
        }
    }

    companion object {
        const val PERMISSION_REQUEST_LOCATION = 0
    }
}