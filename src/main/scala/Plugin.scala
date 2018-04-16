import javax.servlet.ServletContext

import gitbucket.core.controller.Context
import gitbucket.core.plugin._
import gitbucket.core.service.{RepositoryService, SystemSettingsService}
import io.github.gitbucket.chat.controller.ChatController
import io.github.gitbucket.chat.servlet.ChatServlet
import io.github.gitbucket.solidbase.model.Version
import org.scalatra.servlet.RichServletContext

class Plugin extends gitbucket.core.plugin.Plugin{
  override val pluginId: String = "chat"
  override val pluginName: String = "Chat Plugin"
  override val description: String = "This plugin adds chat feature to GitBucket"
  override val versions: List[Version] = List(
    new Version("0.0.1")
  )

  override val repositoryMenus = Seq(
    (repository: RepositoryService.RepositoryInfo, context: Context) => Some(Link("chat", "Chat", "/chat"))
  )

  override val controllers = Seq(
    "/*" -> new ChatController()
  )

  override val assetsMappings = Seq("/chat" -> "/gitbucket/chat/assets")

  override def initialize(registry: PluginRegistry, context: ServletContext, settings: SystemSettingsService.SystemSettings): Unit = {
    super.initialize(registry, context, settings)
    //RichServletContext(context).mount(new ChatServlet, "/*")
    //context.setInitParameter("org.atmosphere.cpr.asyncSupport", "org.atmosphere.container.JSR356AsyncSupport")
    context.setInitParameter("org.atmosphere.cpr.asyncSupport", "org.atmosphere.container.TomcatCometSupport")
    context.setInitParameter("org.atmosphere.useNative", "true")
    val name = "chatServlet"
    context.addServlet(name, new ChatServlet)
    val sr = context.getServletRegistration(name)
    sr.addMapping("/*")
  }

}
