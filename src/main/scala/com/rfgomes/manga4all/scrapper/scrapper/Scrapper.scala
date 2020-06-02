package com.rfgomes.manga4all.scrapper.scrapper

import com.rfgomes.manga4all.manga.domain.{MangaChapter, MangaChapterImages, MangaChapterList, MangaInfo, SearchManga}

import scala.util.Try

trait Scrapper {
  def getLatest(page: Int): Try[List[MangaInfo]]
  def getPopular(page: Int): Try[List[MangaInfo]]
  def search(search: SearchManga): Try[List[MangaInfo]]
  def extractChapterList(mangaInfo: MangaInfo): Try[MangaChapterList]
  def extractChapterImages(mangaChapter: MangaChapter): Try[MangaChapterImages]
}
