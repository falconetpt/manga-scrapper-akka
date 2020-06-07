package com.rfgomes.manga4all.user.domain

import akka.actor.{Actor, ActorLogging}
import com.rfgomes.manga4all.user.domain.UserActor.{Login, Register}

class UserActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case Login(u) => log.info(s"user $u tried to login")
    case Register(u) => log.info(s"user $u tried to register")
  }
}

object UserActor {
  case class Login(user: User)
  case class Register(user: User)
}
