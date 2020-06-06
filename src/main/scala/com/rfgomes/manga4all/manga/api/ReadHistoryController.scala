package com.rfgomes.manga4all.manga.api

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.rfgomes.manga4all.WebServer.{as, complete, concat, entity, get, onSuccess, pathPrefix, post}
import com.rfgomes.manga4all.history.ReadHistoryActor
import com.rfgomes.manga4all.history.ReadHistoryActor.{GetReadHistory, SaveReadManga}
import com.rfgomes.manga4all.manga.domain.MangaChapterImages

import scala.concurrent.duration._
import scala.language.postfixOps

object ReadHistoryController extends MangaApiJsonSupport {
  val system = ActorSystem("FavoriteActorSystem")
  val readHistory = system.actorOf(Props[ReadHistoryActor])

  def route(): Route = {
    concat(
      get {
        pathPrefix("favorite") {
          implicit val askTimeout = Timeout(200 millis)

          onSuccess(readHistory ? GetReadHistory) {
            case x: List[MangaChapterImages] => complete(x)
          }
        }
      },
      post {
        pathPrefix("favorite") {
          entity(as[MangaChapterImages]) { manga =>
            readHistory ! SaveReadManga(manga)
            complete("ok post")
          }
        }
      }
    )
  }
}

