package com.rfgomes.manga4all.scrapper.scrapper

import java.util.concurrent.TimeUnit

import com.rfgomes.manga4all.manga.domain.{MangaChapter, MangaChapterImages, MangaChapterList, MangaInfo, SearchManga}

import scala.util.Try
import com.google.common.cache.{CacheBuilder, CacheLoader, LoadingCache}

case class ScrapperDecorator(scrapper: Scrapper) extends Scrapper {
  private val maxSizeCache = 1000

  private val getLatestCached = generateCache(scrapper.getLatest, (12, TimeUnit.HOURS))
  private val getPopularCached = generateCache(scrapper.getPopular, (12, TimeUnit.HOURS))
  private val searchCached = generateCache(scrapper.search, (24, TimeUnit.HOURS))
  private val chapterListCached = generateCache(scrapper.extractChapterList, (2, TimeUnit.HOURS))
  private val chapterImagesCached = generateCache(scrapper.extractChapterImages, (12, TimeUnit.HOURS))


  override def getLatest(page: Int): Try[List[MangaInfo]] = getLatestCached.get(page)

  override def getPopular(page: Int): Try[List[MangaInfo]] = getPopularCached.get(page)

  override def search(search: SearchManga): Try[List[MangaInfo]] = searchCached.get(search)

  override def extractChapterList(mangaInfo: MangaInfo): Try[MangaChapterList] = chapterListCached.get(mangaInfo)

  override def extractChapterImages(mangaChapter: MangaChapter): Try[MangaChapterImages] = chapterImagesCached.get(mangaChapter)

  private def generateCache[I, O](f: I => O, config: (Int, TimeUnit)): LoadingCache[I, O] = {
    val cacheLoader: CacheLoader[I, O] = new CacheLoader[I, O]() {
      override def load(key: I): O = f(key)
    }

    CacheBuilder.newBuilder()
      .expireAfterAccess(1, TimeUnit.HOURS)
      .maximumSize(maxSizeCache)
      .build[I, O](cacheLoader)
  }
}
