package com.rfgomes.manga4all.scrapper.client

import scala.util.Try

trait ScrapperClient[A] {
  def getClient(url: String): Try[A]
}

object ScrapperClient {
  val DEFAULT = JsoupScrapperClient
}