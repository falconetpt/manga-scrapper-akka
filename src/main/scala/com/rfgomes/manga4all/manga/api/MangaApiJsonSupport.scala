package com.rfgomes.manga4all.manga.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.rfgomes.manga4all.manga.domain.{MangaChapter, MangaChapterImages, MangaChapterList, MangaInfo, SearchManga}
import spray.json.DefaultJsonProtocol

trait MangaApiJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val mangaInfo = jsonFormat4(MangaInfo)
  implicit val mangaChapterImages = jsonFormat6(MangaChapterImages)
  implicit val searchManga = jsonFormat2(SearchManga)
  implicit val mangaChapter = jsonFormat4(MangaChapter)
  implicit val mangaChapterList = jsonFormat5(MangaChapterList)
}
