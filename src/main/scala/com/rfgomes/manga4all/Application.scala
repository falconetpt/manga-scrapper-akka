package com.rfgomes.manga4all

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import com.rfgomes.manga4all.manga.domain.MangaInfo
import com.rfgomes.manga4all.scrapper.Source
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn



// collect your json format instances into a support trait:
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val mangaInfo = jsonFormat4(MangaInfo) // contains List[Item]
}

object Application extends App with Directives with JsonSupport {
  implicit val system = ActorSystem("httpController")
  implicit val materializer = ActorMaterializer()


  val route: Route =
    concat(
      get {
        pathPrefix("hello") {
          complete(Source.manganelo.scrapper.getLatest(1).get)
        }
      }
    )

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
//  StdIn.readLine() // let it run until user presses return
//  bindingFuture
//    .flatMap(_.unbind()) // trigger unbinding from the port
//    .onComplete(_ => system.terminate()) // and shutdown when done
}
