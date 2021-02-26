package com.jiang.articlegenerator.database

import androidx.room.*
import com.jiang.articlegenerator.model.CrapWords
import com.jiang.articlegenerator.model.FamousQuotes

/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      CrapWordsDao
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/25 16:37
 */
@Dao
interface CrapWordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg model: CrapWords)

    @Query("select * from CrapWords")
    suspend fun queryAll(): MutableList<CrapWords>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(model: List<CrapWords>)

    @Delete
    suspend fun delete(vararg model: CrapWords)

    @Query("delete from CrapWords")
    suspend fun deleteAll()

    @Query("select * from CrapWords where type = :entityType ")
    suspend fun queryByType(entityType: Int): MutableList<CrapWords>
}