package com.rfgomes.manga4all.history.domain

import akka.actor.{Actor, ActorLogging}
import com.rfgomes.manga4all.history.domain.FavoriteActor.{AddFavorite, GetAllFavorites}

class FavoriteActor extends Actor with ActorLogging {
  override def receive: Receive = historyHandler(List.empty)

  private def historyHandler(list: List[FavoriteHistory]): Receive = {
    case AddFavorite(manga) => context.become(historyHandler(list ++ List(manga)))
    case GetAllFavorites => sender() ! list
  }
}

object FavoriteActor {
  case class AddFavorite(mangaInfo: FavoriteHistory)
  case object GetAllFavorites
}
