package com.rfgomes.manga4all

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, HttpApp, Route}
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.rfgomes.manga4all.history.FavoriteActor
import com.rfgomes.manga4all.history.FavoriteActor.{AddFavorite, GetAllFavorites}
import com.rfgomes.manga4all.manga.domain.MangaInfo
import com.rfgomes.manga4all.scrapper.Source
import spray.json._
import scala.concurrent.duration._
import scala.language.postfixOps

object Application extends App {
  implicit val system = ActorSystem("httpController")
  implicit val materializer = ActorMaterializer()


  val port: Int = sys.env.getOrElse("PORT", "8080").toInt
  WebServer.startServer("0.0.0.0", port)
}

// collect your json format instances into a support trait:
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val mangaInfo = jsonFormat4(MangaInfo)
}

object WebServer extends HttpApp with Directives with JsonSupport {
  val source = Source.manganelo.scrapper
  val system = ActorSystem("FavoriteActorSystem")
  val favoritesActor = system.actorOf(Props[FavoriteActor])


  override protected def routes: Route =
    concat(
      get {
        pathPrefix("hello") {
          complete(source.getLatest(1).get)
        }
      },
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