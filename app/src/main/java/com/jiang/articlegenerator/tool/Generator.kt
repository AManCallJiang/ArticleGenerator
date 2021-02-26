package com.jiang.articlegenerator.tool

import com.jiang.articlegenerator.database.DatabaseManager
import com.jiang.articlegenerator.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.SecureRandom


/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      Generator
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/26 20:48
 */

object Generator {
    private const val CHANCE_FAMOUS = 30
    private const val CHANCE_KEYWORD = 90
    private val stringBuilder by lazy { StringBuilder() }
    private val random by lazy { SecureRandom() }
    private val fqList by lazy { ArrayList<FamousQuotes>() }
    private val crapList by lazy { ArrayList<CrapWords>() }
    private val headList by lazy { ArrayList<CrapWords>() }
    private val endList by lazy { ArrayList<CrapWords>() }

    private var crapSize = 0
    private var headSize = 0
    private var endSize = 0
    suspend fun generate(keyWord: String, wordCount: Int): String {
        return withContext(Dispatchers.IO) {
            stringBuilder.clear()
            fqList.clear()
            fqList.addAll(DatabaseManager.famousQuotesDao.queryAll())
            if (fqList.isEmpty()) {
                return@withContext "名人名言库为空，生成错误！"
            }
            crapList.clear()
            crapList.addAll(DatabaseManager.crapWordsDao.queryByType(ENTITY_TYPE_CRAP))
            if (crapList.isEmpty()) {
                return@withContext "废话词库为空，生成错误！"
            }
            headList.clear()
            headList.addAll(DatabaseManager.crapWordsDao.queryByType(ENTITY_TYPE_HEAD))
            if (headList.isEmpty()) {
                return@withContext "句首库为空，生成错误！"
            }
            endList.clear()
            endList.addAll(DatabaseManager.crapWordsDao.queryByType(ENTITY_TYPE_END))
            if (endList.isEmpty()) {
                return@withContext "句尾库为空，生成错误！"
            }

            crapSize= crapList.lastIndex
            headSize = headList.lastIndex
            endSize = endList.lastIndex

            //开头格式
            stringBuilder.append("\u3000\u3000")
            while (stringBuilder.length < wordCount) {
                val chance = random.nextInt(100)
                when {
                    chance <= CHANCE_FAMOUS -> useFamousQuote()
                    chance <= CHANCE_KEYWORD -> {
                        //围绕关键词
                        stringBuilder.append(crapList[random.nextInt(crapSize)].remakeCrap(keyWord))
                        useFamousQuote()
                    }
                    else -> stringBuilder.append("\n\u3000\u3000")//换行
                }
            }
            return@withContext stringBuilder.toString()
        }
    }

    private suspend fun useFamousQuote() {
        //移空了就重新补充
        if (fqList.isEmpty()) {
            fqList.addAll(DatabaseManager.famousQuotesDao.queryAll())
        }
        //随机引用，用过就移出随机池
        val choice: Int = random.nextInt(fqList.lastIndex)
        val removeAt = fqList.removeAt(choice)

        stringBuilder.append(removeAt.famousPerson.replace(" ", ""))
            .append(headList[random.nextInt(headSize)].content)
            .append(removeAt.words)
            .append(endList[random.nextInt(endSize)].content)
    }
}