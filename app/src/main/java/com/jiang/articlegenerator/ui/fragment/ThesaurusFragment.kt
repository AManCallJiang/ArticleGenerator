package com.jiang.articlegenerator.ui.fragment



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiang.articlegenerator.R
import com.jiang.articlegenerator.database.DatabaseManager
import com.jiang.articlegenerator.databinding.DialogFqBinding
import com.jiang.articlegenerator.databinding.DialogWordBinding
import com.jiang.articlegenerator.databinding.FragmentThesaurusBinding
import com.jiang.articlegenerator.model.CrapWords
import com.jiang.articlegenerator.model.FamousQuotes
import com.jiang.articlegenerator.tool.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThesaurusFragment : Fragment() {
    private val fragments by lazy {
        arrayOf(
            WordsListFragment(Type.FQ),
            WordsListFragment(Type.CRAP),
            WordsListFragment(Type.HEAD),
            WordsListFragment(Type.END)
        )
    }
    private val fragmentStateAdapter by lazy {
        object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment = fragments[position]
        }
    }
    private lateinit var binding: FragmentThesaurusBinding

    private val addFqDialogBinding by lazy {
        DialogFqBinding.inflate(LayoutInflater.from(requireContext()), null, false)
    }

    private val addFqDialog by lazy {
        AlertDialog.Builder(requireContext())
            .setView(addFqDialogBinding.root)
            .setCancelable(false)
            .create()
    }
    private val addWordDialogBinding by lazy {
        DialogWordBinding.inflate(LayoutInflater.from(requireContext()), null, false)
    }
    private val addWordDialog by lazy {
        AlertDialog.Builder(requireContext())
            .setView(addWordDialogBinding.root)
            .setCancelable(false)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThesaurusBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            vp.adapter = fragmentStateAdapter
            TabLayoutMediator(tabLayout, vp) { tab, i ->
                tab.text = when (i) {
                    0 -> getString(R.string.famous_quotes)
                    1 -> getString(R.string.crap_words)
                    2 -> getString(R.string.head_of_sentence)
                    else -> getString(R.string.end_of_sentence)
                }
            }.attach()
            initDialog()
            btnAdd.setOnClickListener {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        when (binding.vp.currentItem) {
            0 -> addFqDialog.show()
            else -> addWordDialog.show()
        }
    }

    private fun initDialog() {
        addFqDialogBinding.apply {
            btnCancel.setOnClickListener { addFqDialog.dismiss() }
            btnCommit.setOnClickListener {
                val content = etContent.text.toString()
                val name = etName.text.toString()
                if (content.isEmpty() || name.isEmpty()) {
                    toast(R.string.not_be_empty)
                    return@setOnClickListener
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    DatabaseManager.famousQuotesDao.insert(FamousQuotes(null, name, content))
                    fragments[binding.vp.currentItem].getListData()
                    withContext(Dispatchers.Main) { addFqDialog.dismiss() }
                }
            }
        }
        addWordDialogBinding.apply {
            btnCancel.setOnClickListener { addWordDialog.dismiss() }
            btnCommit.setOnClickListener {
                val content = etContent.text.toString()

                if (content.isEmpty()) {
                    toast(R.string.not_be_empty)
                    return@setOnClickListener
                }

                lifecycleScope.launch(Dispatchers.IO) {
                    val currentItem = binding.vp.currentItem
                    DatabaseManager.crapWordsDao.insert(CrapWords(null, content, currentItem - 1))
                    fragments[currentItem].getListData()
                    withContext(Dispatchers.Main) { addWordDialog.dismiss() }
                }
            }
        }
    }

}