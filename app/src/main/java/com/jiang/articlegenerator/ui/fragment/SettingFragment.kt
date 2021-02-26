package com.jiang.articlegenerator.ui.fragment

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.jiang.articlegenerator.R
import com.jiang.articlegenerator.database.DatabaseManager
import com.jiang.articlegenerator.databinding.FragmentSettingBinding
import com.jiang.articlegenerator.tool.CrapHolder
import com.jiang.articlegenerator.tool.Crawler
import com.jiang.articlegenerator.tool.toast
import com.jiang.articlegenerator.ui.activity.SP_KEY
import com.jiang.articlegenerator.ui.activity.SP_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val dialog by lazy {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.are_you_sure))
            .setCancelable(false)
            .setPositiveButton(R.string.reset_thres) { _, _ -> reset() }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }
    private val loadingDialog by lazy {
        ProgressDialog(requireContext()).apply {
            setMessage(getString(R.string.wait))
            setCancelable(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.btnReset.setOnClickListener { dialog.show() }
        return binding.root
    }

    private fun reset() {
        loadingDialog.show()
        lifecycleScope.launch(Dispatchers.IO) {
            DatabaseManager.apply {
                famousQuotesDao.deleteAll()
                crapWordsDao.deleteAll()
                val data = Crawler.getData()
                if (data.isEmpty()) {
                    val edit =
                        requireActivity().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
                    edit.putBoolean(SP_KEY, true)
                    edit.apply()
                    withContext(Dispatchers.Main) {
                        loadingDialog.dismiss()
                        toast(R.string.fail)
                    }
                    return@launch
                }

                famousQuotesDao.insertAll(data)
                crapWordsDao.insertAll(CrapHolder.getDefaultCrapList())
                crapWordsDao.insertAll(CrapHolder.getDefaultBeforeWords())
                crapWordsDao.insertAll(CrapHolder.getDefaultAfterWords())
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    toast(R.string.success)
                }
            }

        }
    }
}