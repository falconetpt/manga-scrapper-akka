package com.rfgomes.manga4all.history

import akka.actor.{Actor, ActorLogging}
import com.rfgomes.manga4all.history.FavoriteActor.{AddFavorite, GetAllFavorites}
import com.rfgomes.manga4all.manga.domain.MangaInfo

class FavoriteActor extends Actor with ActorLogging {
  override def receive: Receive = historyHandler(List.empty)

  private def historyHandler(list: List[MangaInfo]): Receive = {
    case AddFavorite(manga) => context.become(historyHandler(list ++ List(manga)))
    case GetAllFavorites => sender() ! list
  }
}

object FavoriteActor {
  case class AddFavorite(mangaInfo: MangaInfo)
  case object GetAllFavorites
}