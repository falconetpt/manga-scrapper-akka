package com.rfgomes.manga4all.user.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.rfgomes.manga4all.WebServer.{as, complete, entity, get, pathPrefix}
import com.rfgomes.manga4all.user.domain.User
import com.rfgomes.manga4all.user.dto.LoginDto

class UserController extends UserApiJsonSupport {
  def route(): Route = {
    concat(
      get {
        pathPrefix("/user/login") {
          entity(as[LoginDto]) { user =>
            complete(user)
          }
        }
      },
      post {
        pathPrefix("user/create") {
          entity(as[User]) { user =>
            complete(user)
          }
        }
      }
    )
  }
}
