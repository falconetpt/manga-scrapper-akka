package com.rfgomes.manga4all.history.api

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.rfgomes.manga4all.WebServer.{as, complete, concat, entity, get, onSuccess, pathPrefix, post}
import com.rfgomes.manga4all.history.domain.FavoriteActor.{AddFavorite, GetAllFavorites}
import com.rfgomes.manga4all.history.domain.{FavoriteActor, FavoriteHistory}
import com.rfgomes.manga4all.manga.domain.MangaInfo

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration._
import scala.language.postfixOps

object ObjectData {
  val system = ActorSystem("FavoriteActorSystem")
  val favoritesActor = system.actorOf(Props[FavoriteActor])
  val data = ArrayBuffer[Any]()

  def route(): Route = {
    concat(
      get {
        pathPrefix("metrics/get") {
          implicit val askTimeout = Timeout(200 millis)

          complete(data.toString())
        }
      },
      post {
        pathPrefix("metrics/save") {
          entity(as[Any]) { value =>
            data.addOne(value)
            complete("Completed")
          }
        }
      }
    )
  }
}
