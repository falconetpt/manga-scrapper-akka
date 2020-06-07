package com.rfgomes.manga4all.manga.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.LongNumber
import akka.http.scaladsl.server.Route
import com.rfgomes.manga4all.WebServer.{as, complete, entity, get, pathPrefix, source}
import com.rfgomes.manga4all.manga.domain.{MangaChapter, MangaInfo, SearchManga}

object MangaController extends MangaApiJsonSupport {
  def route(): Route = {
    concat(
      get {
        pathPrefix("latest" / LongNumber) { page =>
          complete(source.getLatest(page.toInt).get)
        }
      },
      get {
        pathPrefix("popular" / LongNumber) { page =>
          complete(source.getPopular(page.toInt).get)
        }
      },
      post {
        pathPrefix("search") {
          entity(as[SearchManga]) { manga =>
            complete(source.search(manga).get)
          }
        }
      },
      post {
        pathPrefix("chapter" / "list") {
          entity(as[MangaInfo]) { manga =>
            println(manga)
            complete(source.extractChapterList(manga).get)
          }
        }
      },
      post {
        pathPrefix("chapter" / "extract") {
          entity(as[MangaChapter]) { manga =>
            complete(source.extractChapterImages(manga).get)
          }
        }
      }
    )
  }
}