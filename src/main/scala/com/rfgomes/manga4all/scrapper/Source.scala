package com.rfgomes.manga4all.scrapper

trait Source {
  def name: String
  def scrapper: Scrapper
}

case class Manganelo() extends Source {
  override def name: String = classOf[Manganelo].getSimpleName

  override def scrapper: Scrapper = ???
}

object Source {
  val sources: Set[Source] = Set(Manganelo)

  def getSource(name:String): Option[Source] = {
    sources.find(s => s.name == name)
  }
}