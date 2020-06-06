package com.rfgomes.manga4all.manga.domain

case class MangaChapterImages(mangaId: String,
                              chapterId: String,
                              images: List[String],
                              previousChapter: Option[String],
                              nextChapter: Option[String],
                              source: String)
