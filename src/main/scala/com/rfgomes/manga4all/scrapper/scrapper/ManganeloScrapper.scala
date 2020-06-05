package com.rfgomes.manga4all.scrapper.scrapper

import com.rfgomes.manga4all.manga.domain._
import com.rfgomes.manga4all.scrapper.Source
import com.rfgomes.manga4all.scrapper.client.ScrapperClient
import org.jsoup.nodes.{Document, Element}

import scala.jdk.CollectionConverters._
import scala.util.Try


case class ManganeloScrapper(client: ScrapperClient[Document]) extends Scrapper {
  private val url = "https://manganelo.com"


  override def getLatest(page: Int): Try[List[MangaInfo]] = Try {
    val document = client.getClient(s"$url/genre-all/$page").get

    extractMangaInfo(document)
  }

  override def getPopular(page: Int): Try[List[MangaInfo]] = Try {
    val document = client.getClient(s"$url/genre-all/$page?type=topview").get

    extractMangaInfo(document)
  }


  override def search(search: SearchManga): Try[List[MangaInfo]] = Try {
    val searchText= search.searchText.replaceAll(" ", "_")
    val document = client.getClient(s"$url/search/$searchText?page=${search.page}").get

    val elements = document.body()
      .getElementsByClass("panel-search-story")
      .first()
      .getElementsByClass("search-story-item")
      .iterator()
      .asScala

    elements
      .map(x => x.selectFirst("a"))
      .map(extractMangaInfo)
      .toList
  }

  override def extractChapterList(mangaInfo: MangaInfo): Try[MangaChapterList] = Try {
    val document = client.getClient(s"$url/manga/${mangaInfo.id}").get

    val elements = document.body()
      .getElementsByClass("row-content-chapter")
      .first()
      .select("a")
      .iterator()
      .asScala

    val chapterList = elements
      .map(x => (x.attr("href").split("/").last, x.attr("title")))
      .map(x => MangaChapter(mangaInfo.id, x._1, x._2, Source.manganelo.name))
      .toList

    MangaChapterList(mangaInfo.id, mangaInfo.name, "", mangaInfo.imageUrl, chapterList)
  }


  override def extractChapterImages(mangaChapter: MangaChapter): Try[MangaChapterImages] = Try {
    val document = client.getClient(s"$url/chapter/${mangaChapter.mangaId}/${mangaChapter.chapterId}").get

    val images = document.body()
      .getElementsByClass("container-chapter-reader")
      .first()
      .select("img")
      .iterator()
      .asScala

    val prev = document.body()
      .getElementsByClass("navi-change-chapter-btn-prev")
      .iterator()
      .asScala
      .flatMap(e => e.attr("href").split("/").lastOption)
      .toList
      .headOption

    val next = document.body()
      .getElementsByClass("navi-change-chapter-btn-next")
      .iterator()
      .asScala
      .flatMap(e => e.attr("href").split("/").lastOption)
      .toList
      .headOption

    val imageList = images.map(e => e.attr("src")).toList

    MangaChapterImages(mangaChapter.mangaId, mangaChapter.chapterId, imageList, prev, next, Source.manganelo.name)
  }


  private def extractMangaInfo(document: Document): List[MangaInfo] = {
    val elements = document.body()
      .getElementsByClass("panel-content-genres")
      .first()
      .getElementsByClass("content-genres-item")
      .iterator()
      .asScala

    elements
      .map(x => x.selectFirst("a"))
      .map(extractMangaInfo)
      .toList
  }

  private def extractMangaInfo(element: Element): MangaInfo = {
    val id = element.attr("href").split("/").last
    val title = element.attr("title")
    val imgUrl = element.selectFirst("img").attr("src")

    MangaInfo(id = id, name = title, imageUrl = imgUrl, source = Source.manganelo.name)
  }

}
