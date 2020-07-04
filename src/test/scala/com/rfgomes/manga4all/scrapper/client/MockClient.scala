package com.rfgomes.manga4all.scrapper.client

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.util.Try

case class MockClient(html: String) extends ScrapperClient[Document] {
  override def getClient(url: String): Try[Document] = Try {
    Jsoup.parse(html)
  }
}