package com.borja.pruebanewyorktimes.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.borja.pruebanewyorktimes.data.remote.dto.Result
import com.borja.pruebanewyorktimes.databinding.ItemNewsBinding
import com.bumptech.glide.Glide
import java.lang.IndexOutOfBoundsException


class NewsAdapter (private val listener: OnClickItem) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    lateinit var list: List<Result>

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = list[position].id

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = list[position]
        holder.binding.result = result
        try {
            Glide.with(holder.binding.root.context)
                .load(result.media[0].mediaMetadata[0].url)
                .into(holder.binding.imageView)
        }catch (e: IndexOutOfBoundsException) {}

        holder.binding.root.setOnClickListener {
            listener.onClick(result.url)
        }
    }

    fun updateList(list: List<Result>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

}