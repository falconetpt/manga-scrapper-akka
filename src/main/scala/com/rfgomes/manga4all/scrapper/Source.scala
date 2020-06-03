package com.rfgomes.manga4all.scrapper

import com.rfgomes.manga4all.scrapper.client.ScrapperClient
import com.rfgomes.manga4all.scrapper.scrapper.{ManganeloScrapper, Scrapper, ScrapperDecorator}

trait Source {
  def name: String
  def scrapper: Scrapper
}

case object Manganelo extends Source {
  override def name: String = "Manganelo"

  override def scrapper: Scrapper = ScrapperDecorator(ManganeloScrapper(ScrapperClient.DEFAULT))
}

object Source {
  val manganelo = Manganelo

  val sources: Set[Source] = Set(
    manganelo
  )

  def getSource(name: String): Option[Source] = {
    sources.find(s => s.name == name)
  }
}