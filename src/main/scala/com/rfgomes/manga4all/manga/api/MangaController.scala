package com.rfgomes.manga4all.manga.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.LongNumber
import akka.http.scaladsl.server.Route
import com.rfgomes.manga4all.WebServer.{complete, get, pathPrefix, source}
import com.rfgomes.manga4all.manga.domain.MangaInfo
import spray.json.DefaultJsonProtocol

trait MangaApiJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val mangaInfo = jsonFormat4(MangaInfo)
}

object MangaController extends MangaApiJsonSupport {
  def route(): Route = {
    concat(
      get {
        pathPrefix("latest" / LongNumber) { page =>
          complete(source.getLatest(page.toInt).get)
        }
      },
      get {
        pathPrefix("popular" / LongNumber) { page =>
          complete(source.getPopular(page.toInt).get)
        }
      }
    )
  }
}