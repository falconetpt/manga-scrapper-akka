package com.rfgomes.manga4all

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, HttpApp, Route}
import akka.stream.ActorMaterializer
import com.rfgomes.manga4all.manga.domain.MangaInfo
import com.rfgomes.manga4all.scrapper.Source
import spray.json._



// collect your json format instances into a support trait:
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val mangaInfo = jsonFormat4(MangaInfo) // contains List[Item]
}

object Application extends App {
  implicit val system = ActorSystem("httpController")
  implicit val materializer = ActorMaterializer()


  val port: Int = sys.env.getOrElse("PORT", "8080").toInt
  WebServer.startServer("0.0.0.0", port)

  println(s"Server online at http://localhost:$port/\nPress RETURN to stop...")
//  StdIn.readLine() // let it run until user presses return
//  bindingFuture
//    .flatMap(_.unbind()) // trigger unbinding from the port
//    .onComplete(_ => system.terminate()) // and shutdown when done
}


object WebServer extends HttpApp with Directives with JsonSupport {

  override protected def routes: Route =
    concat(
      get {
        pathPrefix("hello") {
          complete(Source.manganelo.scrapper.getLatest(1).get)
        }
      }
    )
}