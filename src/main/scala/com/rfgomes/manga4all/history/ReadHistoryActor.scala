package com.rfgomes.manga4all.history

import akka.actor.{Actor, ActorLogging}
import com.rfgomes.manga4all.history.ReadHistoryActor.{GetReadHistory, SaveReadManga}
import com.rfgomes.manga4all.manga.domain.MangaChapterImages

class ReadHistoryActor extends Actor with ActorLogging{
  override def receive: Receive = registerHistory(List.empty)

  def registerHistory(list: List[MangaChapterImages]): Receive = {
    case SaveReadManga(m) => context.become(registerHistory(list ++ List(m)))
    case GetReadHistory => sender() ! list
  }
}

object ReadHistoryActor {
  case class SaveReadManga(m: MangaChapterImages)
  case object GetReadHistory
}
