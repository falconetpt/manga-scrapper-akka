package com.rfgomes.manga4all.history.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import com.rfgomes.manga4all.WebServer.{as, complete, concat, entity, get, pathPrefix, post}

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps

object ObjectData extends ObjectJsonSupport {
  val system = ActorSystem("ObjectsActor")
  val elements = ArrayBuffer[TestDataObject]()

  def route(): Route = {
    concat(
      get {
        pathPrefix("metrics") {
          complete(TestDataObjectResult(elements.toList))
        }
      },
      post {
        pathPrefix("metrics") {
          entity(as[TestDataObject]) { e =>
            elements.addOne(e)
            complete("Completed")
          }
        }
      }
    )
  }
}
