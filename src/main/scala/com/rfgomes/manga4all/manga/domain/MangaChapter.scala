package com.rfgomes.manga4all.manga.domain

import com.rfgomes.manga4all.scrapper.Source

case class MangaChapter(mangaId: String, chapterId: String, name: String, source: String)
