package com.rfgomes.manga4all.manga.api

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.rfgomes.manga4all.WebServer.{as, complete, concat, entity, get, onSuccess, pathPrefix, post}
import com.rfgomes.manga4all.history.FavoriteActor
import com.rfgomes.manga4all.history.FavoriteActor.{AddFavorite, GetAllFavorites}
import com.rfgomes.manga4all.manga.domain.MangaInfo

import scala.concurrent.duration._
import scala.language.postfixOps

object FavoritesController extends MangaApiJsonSupport {
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
          entity(as[MangaInfo]) { mangaInfo =>
            favoritesActor ! AddFavorite(mangaInfo)
            complete("ok post")
          }
        }
      }
    )
  }
}

