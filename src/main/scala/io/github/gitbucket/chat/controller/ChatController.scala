package io.github.gitbucket.chat.controller

import gitbucket.core.controller.ControllerBase
import gitbucket.core.service.{AccountService, RepositoryService}
import gitbucket.core.util.ReadableUsersAuthenticator

class ChatController extends ControllerBase
  with AccountService with RepositoryService with ReadableUsersAuthenticator {


  get("/:owner/:repository/chat")(readableUsersOnly { repository =>
    gitbucket.chat.html.chat(repository)
  })
}
