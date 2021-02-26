package com.jiang.articlegenerator.database

import android.content.Context
import androidx.room.Room

/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      DatabaseManager
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/25 16:45
 */
private const val DATABASE_NAME = "ArticleGenerator_db"

object DatabaseManager {
    private lateinit var context: Context
    private val database by lazy {
        Room.databaseBuilder(context, MyDatabase::class.java, DATABASE_NAME).build()
    }
    val famousQuotesDao by lazy { database.getFamousQuotesDao() }
    val crapWordsDao by lazy { database.getCrapWordsDao() }
    fun initContext(appContext: Context) {
        if (!this::context.isInitialized) {
            context = appContext
        }
    }
}