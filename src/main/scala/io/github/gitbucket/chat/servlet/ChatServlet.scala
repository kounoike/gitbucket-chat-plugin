package io.github.gitbucket.chat.servlet

import java.util.Date

import gitbucket.core.model.Account
import gitbucket.core.util.Keys
import org.json4s.JsonDSL._
import org.json4s._
import org.json4s.DefaultFormats
import org.scalatra._
import org.scalatra.atmosphere._
import org.scalatra.json.JacksonJsonSupport

import scala.concurrent.ExecutionContext.Implicits.global

class ChatServlet extends ScalatraServlet
  with JacksonJsonSupport with SessionSupport
  with AtmosphereSupport {

  implicit val jsonFormats = DefaultFormats

  atmosphere("/:owner/:repository/chat-atmosphere"){
    new AtmosphereClient{
      def receive = {
        case Connected =>
          println("Connected")
        case Disconnected(disconnector, Some(error)) =>
          println("Disconnected")
        case Error(Some(error)) =>
          println("Error")
        case TextMessage(text) =>
          send("ECHO: " + text)
        case JsonMessage(json) =>
          val msgJson = json merge (("time" -> (new Date().getTime().toString)): JValue)
          broadcast(msgJson)
          send(msgJson)
      }
    }
  }
}
