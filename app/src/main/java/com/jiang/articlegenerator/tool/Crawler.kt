package com.jiang.articlegenerator.tool

import android.util.Log
import com.jiang.articlegenerator.model.FamousQuotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      Crawler
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/25 16:01
 */
const val USER_AGENT =
    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
private const val TAG = "Crawler"

object Crawler {
    private const val url = "https://www.lz13.cn/mingrenmingyan/4163.html"
    suspend fun getData(): List<FamousQuotes> {
        return withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(url).header("user-agent", USER_AGENT).timeout(3000).get()
                val pElement = doc.select("div[class=PostContent]").select("p")

                pElement.removeFirst()
                val map = pElement.map { element ->
                    val split = element.text().split(Regex("[0-9]+[.]"))
                    split.last()
                }

                return@withContext map.map {
                    val split = it.split("——")
                    FamousQuotes(null, split.last(), split[0])
                }
            }catch (e:Exception){
                Log.e(TAG, "getData:${e.localizedMessage} ", e)
                return@withContext emptyList()
            }

        }
    }

}