package com.rfgomes.manga4all

import akka.actor.ActorSystem
import akka.http.scaladsl.server.{Directives, HttpApp, Route}
import akka.stream.ActorMaterializer
import com.rfgomes.manga4all.manga.api.{FavoritesController, MangaController, ReadHistoryController}
import com.rfgomes.manga4all.scrapper.Source

import scala.language.postfixOps

object Application extends App {
  implicit val system = ActorSystem("httpController")
  implicit val materializer = ActorMaterializer()


  val port: Int = sys.env.getOrElse("PORT", "8080").toInt
  WebServer.startServer("0.0.0.0", port)
}

object WebServer extends HttpApp with Directives {
  val source = Source.manganelo.scrapper


  override protected def routes: Route =
    concat(
      MangaController.route(),
      FavoritesController.route(),
      ReadHistoryController.route()
    )
}