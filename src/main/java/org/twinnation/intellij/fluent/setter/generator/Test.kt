package org.twinnation.intellij.fluent.setter.generator


public class ArticleTag {

    private var id: Long? = null

    private var article: String? = null

    private var tag: String? = null


    constructor(article: String, tag: String) {
        this.article = article
        this.tag = tag
    }




}
