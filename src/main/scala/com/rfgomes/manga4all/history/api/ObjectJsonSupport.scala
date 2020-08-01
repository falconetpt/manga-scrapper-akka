package com.rfgomes.manga4all.history.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

import scala.collection.mutable.ArrayBuffer

class ObjectJsonSupport extends SprayJsonSupport
  with DefaultJsonProtocol {
  implicit val data = jsonFormat1(TestDataObject)
  implicit val list = jsonFormat1(TestDataObjectResult)
}

case class TestDataObject(value: String)
case class TestDataObjectResult(list: List[TestDataObject])