package com.rfgomes.manga4all.history.api

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.rfgomes.manga4all.WebServer.{as, complete, concat, entity, get, onSuccess, pathPrefix, post}
import com.rfgomes.manga4all.history.domain.{FavoriteActor, FavoriteHistory}
import com.rfgomes.manga4all.manga.api.MangaApiJsonSupport
import com.rfgomes.manga4all.manga.domain.MangaInfo
import akka.pattern.ask
import com.rfgomes.manga4all.history.domain.FavoriteActor.{AddFavorite, GetAllFavorites}

import scala.concurrent.duration._
import scala.language.postfixOps

object FavoritesController extends MangaHistoryJsonSupport {
  val system = ActorSystem("FavoriteActorSystem")
  val favoritesActor = system.actorOf(Props[FavoriteActor])

  def route(): Route = {
    concat(
      get {
        pathPrefix("favorite") {
          implicit val askTimeout = Timeout(200 millis)

          onSuccess(favoritesActor ? GetAllFavorites) {
            case x: List[MangaInfo] => complete(x)
          }
        }
      },
      post {
        pathPrefix("favorite") {
          entity(as[FavoriteHistory]) { mangaInfo =>
            favoritesActor ! AddFavorite(mangaInfo)
            complete("ok post")
          }
        }
      }
    )
  }
}
