package com.jiang.articlegenerator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      FamousQuotes
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/25 16:27
 */
@Entity(tableName = "FamousQuotes")
data class FamousQuotes(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var famousPerson: String,
    var words: String
)
