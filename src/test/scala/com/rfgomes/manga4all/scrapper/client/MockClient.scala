package com.rfgomes.manga4all.scrapper.client

import java.io.File
import java.nio.charset.Charset

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.util.Try

case class MockClient(html: String) extends ScrapperClient[Document] {
  override def getClient(url: String): Try[Document] = Try {
    Jsoup.parse(html)
  }
}