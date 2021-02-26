package com.jiang.articlegenerator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      CrapWords
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/25 16:31
 */
@Entity(tableName = "CrapWords")
data class CrapWords(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var content: String,
    var type: Int
) {
    fun remakeCrap(keyword: String) = content.replace("x", keyword)
}

const val ENTITY_TYPE_CRAP = 0
const val ENTITY_TYPE_HEAD = 1
const val ENTITY_TYPE_END = 2