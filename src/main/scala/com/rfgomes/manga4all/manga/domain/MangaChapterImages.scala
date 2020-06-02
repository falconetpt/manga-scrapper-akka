package com.rfgomes.manga4all.manga.domain

import com.rfgomes.manga4all.scrapper.Source

case class MangaChapterImages(mangaId: String,
                              chapter: String,
                              images: List[String],
                              previousChapter: Option[String],
                              nextChapter: Option[String],
                              source: String)
