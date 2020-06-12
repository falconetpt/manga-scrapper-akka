package com.rfgomes.manga4all.manga.domain

import akka.actor.{Actor, ActorLogging}
import com.rfgomes.manga4all.manga.domain.MangaActor.GetManga

class MangaActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case GetManga(f) =>
      val result = f()
      log.info(s"fetching async: $result")
      result
  }
}

object MangaActor {
  case class GetManga(f : () => Any)
}
