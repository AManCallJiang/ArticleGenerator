package com.jiang.articlegenerator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jiang.articlegenerator.model.CrapWords
import com.jiang.articlegenerator.model.FamousQuotes

/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      MyDatabases
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/25 16:35
 */
@Database(entities = [FamousQuotes::class, CrapWords::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getFamousQuotesDao(): FamousQuotesDao
    abstract fun getCrapWordsDao(): CrapWordsDao
}