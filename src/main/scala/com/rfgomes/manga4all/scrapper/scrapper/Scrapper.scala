package com.rfgomes.manga4all.scrapper.scrapper

import com.rfgomes.manga4all.manga.domain.{MangaChapterList, MangaInfo, SearchManga}

import scala.util.Try

trait Scrapper {
  def getLatest(page: Int): Try[List[MangaInfo]]
  def getPopular(page: Int): Try[List[MangaInfo]]
  def search(search: SearchManga): Try[List[MangaInfo]]
  def extractChapterList(mangaInfo: MangaInfo): Try[MangaChapterList]
}
