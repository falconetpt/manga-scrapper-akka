package com.rfgomes.manga4all

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, concat, get, pathPrefix}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext.Implicits.global

import scala.io.StdIn

object Application extends App {
  implicit val system = ActorSystem("httpController")
  implicit val materializer = ActorMaterializer()


  val route: Route =
    concat(
      get {
        pathPrefix("hello") {
          complete(
            HttpEntity(
              ContentTypes.`text/plain(UTF-8)`,
              "hello"
            )
          )
        }
      }
    )

  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
