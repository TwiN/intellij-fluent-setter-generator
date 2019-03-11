# IntelliJ Fluent Setter Generator

Allows you to generate fluent setters with or without a prefix.

Ironically enough, the code is in Kotlin, but it doesn't support Kotlin (yet).


![Dialog](assets/dialog.png)

You can even use the existing setters to set your parameters.


```java
// ...
public ArticleTag withId(Long id) {
	this.id = id;
	return this;
}


public ArticleTag withArticle(Article article) {
	this.article = article;
	return this;
}


public ArticleTag withTag(Tag tag) {
	this.tag = tag;
	return this;
}
// ...
```

```java
new ArticleTag()
    .withId(...)
    .withArticle(...)
    .withTag(...)
```