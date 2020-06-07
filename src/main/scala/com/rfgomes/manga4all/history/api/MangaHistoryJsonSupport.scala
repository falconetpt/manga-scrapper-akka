package com.rfgomes.manga4all.history.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.rfgomes.manga4all.history.domain.{FavoriteHistory, ReadHistory}
import com.rfgomes.manga4all.manga.api.MangaApiJsonSupport
import spray.json.DefaultJsonProtocol

class MangaHistoryJsonSupport extends SprayJsonSupport
  with DefaultJsonProtocol
  with MangaApiJsonSupport {
  implicit val favorites = jsonFormat2(FavoriteHistory)
  implicit val readHistory = jsonFormat2(ReadHistory)
}
