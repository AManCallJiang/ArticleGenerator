package com.jiang.articlegenerator.tool

import com.jiang.articlegenerator.model.*

/**
 *
 * @ProjectName:    ArticleGenerator
 * @ClassName:      CrapHolder
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2021/2/26 13:05
 */
object CrapHolder {
    private val CRAP_LIST = listOf(
        "现在, 解决x的问题, 是非常非常重要的。所以, ",
        "我们不得不面对一个非常尴尬的事实, 那就是, ",
        "x的发生, 到底需要如何做到, 不x的发生, 又会如何产生. ",
        "而这些并不是完全重要, 更加重要的问题是, ",
        "x, 到底应该如何实现。 ",
        "带着这些问题, 我们来审视一下x。 ",
        "所谓x, 关键是x需要如何写。 ",
        "我们一般认为, 抓住了问题的关键, 其他一切则会迎刃而解。",
        "问题的关键究竟为何? ",
        "x因何而发生?",
        "每个人都不得不面对这些问题。在面对这种问题时, ",
        "一般来讲, 我们都必须务必慎重的考虑考虑. ",
        "要想清楚, x, 到底是一种怎么样的存在. ",
        "了解清楚x到底是一种怎么样的存在, 是解决一切问题的关键。",
        "就我个人来说, x对我的意义, 不能不说非常重大。",
        "本人也是经过了深思熟虑,在每个日日夜夜思考这个问题。",
        "x, 发生了会如何, 不发生又会如何。",
        "在这种困难的抉择下, 本人思来想去, 寝食难安。",
        "生活中, 若x出现了, 我们就不得不考虑它出现了的事实。",
        "这种事实对本人来说意义重大, 相信对这个世界也是有一定意义的。",
        "我们都知道, 只要有意义, 那么就必须慎重考虑。",
        "既然如此, ",
        "那么, ",
        "我认为, ",
        "一般来说, ",
        "总结的来说, ",
        "既然如何, ",
        "经过上述讨论, ",
        "这样看来, ",
        "从这个角度来看, ",  // "我们不妨可以这样来想: ",
        "这是不可避免的。 ",
        "可是，即使是这样，x的出现仍然代表了一定的意义。",
        "x似乎是一种巧合，但如果我们从一个更大的角度看待问题，这似乎是一种不可避免的事实。",
        "在这种不可避免的冲突下，我们必须解决这个问题。",
        "对我个人而言，x不仅仅是一个重大的事件，还可能会改变我的人生。"
    )
    private val BEFORE_LIST: List<String> = listOf(
        "曾经说过",
        "在不经意间这样说过",
        "说过一句著名的话",
        "曾经提到过",
        "说过一句富有哲理的话",
        "也这样讲过"
    )
    private val AFTER_LIST: List<String> = listOf(
        "这不禁令我深思。 ",
        "带着这句话, 我们还要更加慎重的审视这个问题。 ",
        "这启发了我。 ",
        "我希望诸位也能好好地体会这句话。 ",
        "这句话语虽然很短, 但令我浮想联翩。",
        "这句话看似简单，但其中的阴郁不禁让人深思。",
        "这句话把我们带到了一个新的维度去思考这个问题。",
        "这似乎解答了我的疑惑。 "
    )

    fun getDefaultCrapList(): List<CrapWords> =
        CRAP_LIST.map { CrapWords(null, it, ENTITY_TYPE_CRAP) }

    fun getDefaultBeforeWords() = BEFORE_LIST.map { CrapWords(null, it, ENTITY_TYPE_HEAD) }

    fun getDefaultAfterWords() = AFTER_LIST.map { CrapWords(null, it, ENTITY_TYPE_END) }
}