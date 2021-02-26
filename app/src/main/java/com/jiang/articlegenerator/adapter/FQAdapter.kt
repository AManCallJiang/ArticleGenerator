package com.jiang.articlegenerator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jiang.articlegenerator.databinding.ItemFqBinding
import com.jiang.articlegenerator.model.FamousQuotes


class FQAdapter(private val list: List<FamousQuotes>) :
    RecyclerView.Adapter<FQAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemFqBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFqBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    var onClick: ((Int) -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvWord.text = item.words
            tvPerson.text = item.famousPerson
            root.setOnClickListener { onClick?.invoke(position) }
        }
    }

    override fun getItemCount(): Int = list.size
}