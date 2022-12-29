package com.apexon.mockapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apexon.mockapi.databinding.RowItemNewsBinding
import com.apexon.mockapi.remote.responses.NewsResponse

class NewsAdapter(val callback: (String?) -> Unit) : RecyclerView.Adapter<NewsAdapter.VH>(){

    var list: List<NewsResponse.Articles>? = null
    set(value) {
        field = value
        notifyItemRangeChanged(0,(value?.size ?: 0))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RowItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list?.get(position))
    }

    override fun getItemCount() = list?.size ?: 0

    inner class VH(private val binding: RowItemNewsBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.lLay.setOnClickListener {
                val result = binding.root.tag as NewsResponse.Articles?
                callback.invoke(result?.author)
            }
        }

        fun bind(articles: NewsResponse.Articles?) {
            binding.root.tag  = articles
            binding.model = articles
            binding.executePendingBindings()
        }
    }
}