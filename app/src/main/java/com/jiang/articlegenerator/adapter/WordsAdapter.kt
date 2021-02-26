package com.jiang.articlegenerator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jiang.articlegenerator.databinding.ItemWordsBinding
import com.jiang.articlegenerator.model.CrapWords

/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      WordsAdapter
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/26 13:31
 */
class WordsAdapter(private val list: List<CrapWords>) :
    RecyclerView.Adapter<WordsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemWordsBinding) : RecyclerView.ViewHolder(binding.root)

    var onClick: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemWordsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvWord.text = item.content
            root.setOnClickListener { onClick?.invoke(position) }
        }
    }

    override fun getItemCount(): Int = list.size

}