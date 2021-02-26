package com.jiang.articlegenerator.database

import androidx.room.*
import com.jiang.articlegenerator.model.FamousQuotes

/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      FamousQuotesDao
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/25 16:33
 */
@Dao
interface FamousQuotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg model: FamousQuotes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(model: List<FamousQuotes>)

    @Query("select * from FamousQuotes")
    suspend fun queryAll(): MutableList<FamousQuotes>

    @Delete
    suspend fun delete(vararg model: FamousQuotes)

    @Query("delete from FamousQuotes")
    suspend fun deleteAll()

}