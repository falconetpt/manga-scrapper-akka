package com.rfgomes.manga4all.user.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.rfgomes.manga4all.user.domain.User
import com.rfgomes.manga4all.user.dto.LoginDto
import spray.json.DefaultJsonProtocol

trait UserApiJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val userJson = jsonFormat3(User)
  implicit val userLogin = jsonFormat2(LoginDto)
}
