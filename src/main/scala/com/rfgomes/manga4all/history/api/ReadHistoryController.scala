package com.rfgomes.manga4all.history.api

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.rfgomes.manga4all.WebServer.{as, complete, concat, entity, get, onSuccess, pathPrefix, post}
import com.rfgomes.manga4all.history.domain.{ReadHistory, ReadHistoryActor}
import com.rfgomes.manga4all.history.domain.ReadHistoryActor.{GetReadHistory, SaveReadManga}
import com.rfgomes.manga4all.manga.api.MangaApiJsonSupport
import com.rfgomes.manga4all.manga.domain.MangaChapterImages
import akka.pattern.ask

import scala.concurrent.duration._
import scala.language.postfixOps

object ReadHistoryController extends MangaHistoryJsonSupport {
  val system = ActorSystem("FavoriteActorSystem")
  val readHistoryActor = system.actorOf(Props[ReadHistoryActor])

  def route(): Route = {
    concat(
      get {
        pathPrefix("favorite") {
          implicit val askTimeout = Timeout(200 millis)

          onSuccess(readHistoryActor ? GetReadHistory) {
            case x: List[MangaChapterImages] => complete(x)
          }
        }
      },
      post {
        pathPrefix("favorite") {
          entity(as[ReadHistory]) { manga =>
            readHistoryActor ! SaveReadManga(manga)
            complete("ok post")
          }
        }
      }
    )
  }
}
