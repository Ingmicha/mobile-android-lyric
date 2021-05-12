package com.mura.android.lyrics.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mura.android.lyrics.R
import com.mura.android.lyrics.databinding.FragmentLyricListBinding
import com.mura.android.lyrics.ui.adapter.LyricAdapter
import com.mura.android.lyrics.ui.base.BaseFragment
import com.mura.android.lyrics.utils.Resource
import kotlinx.coroutines.launch

class LyricListFragment : BaseFragment() {

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

        //** Set the adapter of the RecyclerView
        setAdapter()

        //** Set the Layout Manager of the RecyclerView
        setRVLayoutManager()

        //** Set Observers
        setObservers()

        //**Set Listeners
        setListeners()

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

            findNavController().navigate(R.id.action_lyricListFragment_to_lyricFragment)
        }

    }

    private fun setObservers() {

        mainViewModel.responseDatabase.observe(viewLifecycleOwner, Observer {
            //(activity as MainActivity).showLinearProgressBar(false) //  View.GONE
            if (it.status == Resource.Status.SUCCESS)
                if ((it.data as List<*>).isNotEmpty()) {
                    adapter.updateData(it.data)
                    binding.recyclerViewLyricList.visibility = View.VISIBLE
                } else {
                    binding.emptyListTextView.visibility = View.VISIBLE
                    binding.emptyImageView.visibility = View.VISIBLE
                }
        })
    }
}