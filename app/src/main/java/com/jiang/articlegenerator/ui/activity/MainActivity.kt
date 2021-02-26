package com.jiang.articlegenerator.ui.activity

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.jiang.articlegenerator.R
import com.jiang.articlegenerator.database.DatabaseManager
import com.jiang.articlegenerator.databinding.ActivityMainBinding
import com.jiang.articlegenerator.tool.CrapHolder
import com.jiang.articlegenerator.tool.Crawler
import com.jiang.articlegenerator.tool.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val SP_NAME = "config"
const val SP_KEY = "isFirst"

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private val sp by lazy { getSharedPreferences(SP_NAME, Context.MODE_PRIVATE) }
    private val dialog by lazy {
        ProgressDialog(this).apply {
            setMessage(getString(R.string.wait))
            setCancelable(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initData() {
        DatabaseManager.initContext(applicationContext)
        if (sp.getBoolean(SP_KEY, true)) {
            dialog.show()
            lifecycleScope.launch(Dispatchers.IO) {
                val data = Crawler.getData()
                Log.d("TAG", "initData: ${data.size}")
                if (data.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        toast(R.string.fail)
                        binding.tvFail.visibility= View.VISIBLE
                        dialog.dismiss()
                    }
                    return@launch
                }
                DatabaseManager.famousQuotesDao.insertAll(data)
                DatabaseManager.crapWordsDao.insertAll(CrapHolder.getDefaultCrapList())
                DatabaseManager.crapWordsDao.insertAll(CrapHolder.getDefaultBeforeWords())
                DatabaseManager.crapWordsDao.insertAll(CrapHolder.getDefaultAfterWords())
                sp.edit().apply {
                    putBoolean(SP_KEY, false)
                    apply()
                }

                withContext(Dispatchers.Main) {
                    toast(R.string.success)
                    dialog.dismiss()
                }
            }
        }
    }

    private fun initView() {
        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    fun navigateByActionId(id: Int) {
        navController.navigate(id)
    }
}