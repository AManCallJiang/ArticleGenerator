package com.jiang.articlegenerator.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiang.articlegenerator.R
import com.jiang.articlegenerator.adapter.FQAdapter
import com.jiang.articlegenerator.adapter.WordsAdapter
import com.jiang.articlegenerator.database.DatabaseManager
import com.jiang.articlegenerator.databinding.DialogFqBinding
import com.jiang.articlegenerator.databinding.DialogWordBinding
import com.jiang.articlegenerator.databinding.FragmentWordsListBinding
import com.jiang.articlegenerator.model.*
import com.jiang.articlegenerator.tool.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordsListFragment(private val type: Type) : Fragment() {
    private lateinit var binding: FragmentWordsListBinding
    private val fqList by lazy { ArrayList<FamousQuotes>() }
    private val fqAdapter by lazy { FQAdapter(fqList) }
    private val wordList by lazy { ArrayList<CrapWords>() }
    private val wordsAdapter by lazy { WordsAdapter(wordList) }
    var actionArray = arrayOf("修改", "删除")
    private val actionDialog by lazy {
        AlertDialog.Builder(requireContext())
            .setItems(actionArray) { _, which -> if (which == 0) showUpdateItem() else deleteItem() }
    }
    private var selectPos = 0
    private val fqDialogBinding by lazy {
        DialogFqBinding.inflate(LayoutInflater.from(requireContext()), null, false)
    }

    private val updateDialog by lazy {
        AlertDialog.Builder(requireContext())
            .setView(if (type == Type.FQ) fqDialogBinding.root else wordDialogBinding.root)
            .setCancelable(false)
            .create()
    }
    private val wordDialogBinding by lazy {
        DialogWordBinding.inflate(LayoutInflater.from(requireContext()), null, false)
    }

    private fun deleteItem() {
        lifecycleScope.launch(Dispatchers.IO) {
            when (type) {
                Type.FQ -> {
                    DatabaseManager.famousQuotesDao.delete(fqList[selectPos])
                    withContext(Dispatchers.Main) { toast(R.string.success) }
                }
                else -> {
                    DatabaseManager.crapWordsDao.delete(wordList[selectPos])
                    withContext(Dispatchers.Main) { toast(R.string.success) }
                }
            }
            getListData()
        }
    }

    private fun showUpdateItem() {
        updateDialog.show()
        if (type == Type.FQ) {
            fqDialogBinding.apply {
                val famousQuotes = fqList[selectPos]
                etContent.setText(famousQuotes.words)
                etName.setText(famousQuotes.famousPerson)
            }
        } else {
            wordDialogBinding.etContent.setText(wordList[selectPos].content)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordsListBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }


    private fun initView() {
        binding.apply {
            rv.layoutManager = LinearLayoutManager(requireContext())
            rv.adapter = when (type) {
                Type.FQ -> fqAdapter.apply {
                    onClick = {
                        actionDialog.show()
                        selectPos = it
                    }
                }

                else -> wordsAdapter.apply {
                    onClick = {
                        actionDialog.show()
                        selectPos = it
                    }
                }
            }
        }

        if (type == Type.FQ) {
            fqDialogBinding.apply {
                btnCancel.setOnClickListener { updateDialog.dismiss() }
                btnCommit.setOnClickListener { updateFq() }
            }
        } else {
            wordDialogBinding.apply {
                btnCancel.setOnClickListener { updateDialog.dismiss() }
                btnCommit.setOnClickListener { updateWords() }
            }
        }
    }

    private fun updateWords() {
        wordDialogBinding.apply {
            val content = etContent.text.toString()
            if (content.isEmpty()) {
                toast(R.string.not_be_empty)
                return
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val model = wordList[selectPos].apply { this.content = content }
                DatabaseManager.crapWordsDao.insert(model)
                withContext(Dispatchers.Main) {
                    toast(R.string.success)
                    updateDialog.dismiss()
                }
                getListData()
            }
        }
    }

    private fun updateFq() {
        fqDialogBinding.apply {
            val content = etContent.text.toString()
            val name = etName.text.toString()
            if (content.isEmpty() || name.isEmpty()) {
                toast(R.string.not_be_empty)
                return
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val model = fqList[selectPos].apply {
                    famousPerson = name
                    words = content
                }
                DatabaseManager.famousQuotesDao.insert(model)
                withContext(Dispatchers.Main) {
                    toast(R.string.success)
                    updateDialog.dismiss()
                }
                getListData()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getListData()
    }

    fun getListData() {
        lifecycleScope.launch(Dispatchers.IO) {
            when (type) {
                Type.FQ -> {
                    fqList.clear()
                    fqList.addAll(DatabaseManager.famousQuotesDao.queryAll())
                }
                Type.CRAP -> {
                    wordList.clear()
                    wordList.addAll(DatabaseManager.crapWordsDao.queryByType(ENTITY_TYPE_CRAP))
                }
                Type.HEAD -> {
                    wordList.clear()
                    wordList.addAll(DatabaseManager.crapWordsDao.queryByType(ENTITY_TYPE_HEAD))
                }
                else -> {
                    wordList.clear()
                    wordList.addAll(DatabaseManager.crapWordsDao.queryByType(ENTITY_TYPE_END))
                }
            }
            withContext(Dispatchers.Main) {
                refreshUI()
            }
        }
    }

    private fun refreshUI() {
        when (type) {
            Type.FQ -> fqAdapter.notifyDataSetChanged()
            else -> wordsAdapter.notifyDataSetChanged()
        }
    }
}

enum class Type {
    FQ, CRAP, HEAD, END
}