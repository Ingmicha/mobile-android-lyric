package com.mura.android.lyrics.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mura.android.lyrics.data.model.Lyric
import com.mura.android.lyrics.databinding.ItemLyricBinding
import com.mura.android.lyrics.ui.fragments.LyricListFragment
import com.mura.android.lyrics.ui.fragments.LyricListFragmentDirections


class LyricAdapter(private val lyricListFragment: LyricListFragment) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var lyrics: List<Lyric>

    fun updateData(lyrics: List<Lyric>) {
        this.lyrics = lyrics
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        return if (this::lyrics.isInitialized)
            lyrics.size
        else
            0
    }

    class ItemViewHolder(var viewBinding: ItemLyricBinding) :
        RecyclerView.ViewHolder(viewBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemLyricBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        itemViewHolder.viewBinding.item = lyrics[position]
        itemViewHolder.viewBinding.executePendingBindings()
        itemViewHolder.viewBinding.itemCardView.setOnClickListener {

            val action = LyricListFragmentDirections.actionLyricListFragmentToLyricFindFragment(
                lyrics[position].artist,
                lyrics[position].title,
                lyrics[position].lyric
            )

            lyricListFragment.findNavController()
                .navigate(action)
        }
    }

}