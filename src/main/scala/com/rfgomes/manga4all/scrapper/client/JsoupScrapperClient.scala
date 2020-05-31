package com.rfgomes.manga4all.scrapper.client

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.util.Try

object JsoupScrapperClient extends ScrapperClient[Document] {
  override def getClient(url: String): Try[Document] = Try {
    Jsoup.connect(url).get()
  }
}
