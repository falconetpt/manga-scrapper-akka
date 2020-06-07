package com.rfgomes.manga4all.history.domain

import akka.actor.{Actor, ActorLogging}
import com.rfgomes.manga4all.history.domain.ReadHistoryActor.{GetReadHistory, SaveReadManga}

class ReadHistoryActor extends Actor with ActorLogging{
  override def receive: Receive = registerHistory(List.empty)

  def registerHistory(list: List[ReadHistory]): Receive = {
    case SaveReadManga(m) => context.become(registerHistory(list ++ List(m)))
    case GetReadHistory => sender() ! list
  }
}

object ReadHistoryActor {
  case class SaveReadManga(m: ReadHistory)
  case object GetReadHistory
}
