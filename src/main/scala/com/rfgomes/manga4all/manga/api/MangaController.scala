package com.rfgomes.manga4all.manga.api

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.LongNumber
import akka.http.scaladsl.server.Route
import com.rfgomes.manga4all.WebServer.{as, complete, entity, get, pathPrefix, source}
import com.rfgomes.manga4all.manga.domain.MangaActor.GetManga
import com.rfgomes.manga4all.manga.domain.{MangaActor, MangaChapter, MangaInfo, SearchManga}

object MangaController extends MangaApiJsonSupport {
  private val system = ActorSystem("mangaController")
  private val mangaActor = system.actorOf(Props[MangaActor])

  def route(): Route = {
    concat(
      get {
        pathPrefix("latest" / LongNumber) { page =>
          mangaActor ! GetManga(() => source.getLatest(page.toInt + 1).get)
          complete(source.getLatest(page.toInt).get)
        }
      },
      get {
        pathPrefix("popular" / LongNumber) { page =>
          mangaActor ! GetManga(() => source.getPopular(page.toInt + 1).get)
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
            complete(source.extractChapterList(manga).get)
          }
        }
      },
      post {
        pathPrefix("chapter" / "extract") {
          entity(as[MangaChapter]) { manga =>
            val result = source.extractChapterImages(manga.copy(name = "")).get

            //async fetch next and prev page
            List(result.previousChapter, result.nextChapter).flatten
                .map(c => MangaChapter(manga.mangaId, c, "", manga.source))
                .foreach(c => mangaActor ! GetManga(() => source.extractChapterImages(c)))

            complete(result)
          }
        }
      }
    )
  }
}