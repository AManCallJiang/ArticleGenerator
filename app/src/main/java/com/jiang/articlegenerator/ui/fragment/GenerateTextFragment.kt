package com.jiang.articlegenerator.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import com.jiang.articlegenerator.R
import com.jiang.articlegenerator.databinding.FragmentGenerateTextBinding
import com.jiang.articlegenerator.tool.Generator
import com.jiang.articlegenerator.tool.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GenerateTextFragment : Fragment() {
    private lateinit var binding: FragmentGenerateTextBinding
    private val wordCountArray by lazy { resources.getStringArray(R.array.word_count) }
    private var select = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenerateTextBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            btnGen.setOnClickListener { generateText() }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    select = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun generateText() {
        val key = binding.etKeyword.text.toString()
        if (key.isEmpty()) {
            toast(R.string.not_be_empty)
            return
        }
        lifecycleScope.launch(Dispatchers.Main) {
            val text = Generator.generate(key, wordCountArray[select].toInt())
            toast(R.string.success)
            binding.tvContent.text = text
        }
    }
}