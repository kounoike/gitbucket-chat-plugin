
name := "gitbucket-chat-plugin"
organization := "io.github.kounoike"

version := "0.0.1"

scalaVersion := "2.12.4"
val JettyVersion = "9.3.19.v20170502"

libraryDependencies ++= Seq(
  "io.github.gitbucket" %% "gitbucket" % "4.19.0-SNAPSHOT" % "provided",
  "org.scalatra"                %% "scalatra" % "2.6.0-SNAPSHOT" % "provided",
  "org.scalatra"                %% "scalatra-atmosphere" % "2.6.0-SNAPSHOT" % "provided",
  "org.eclipse.jetty"   % "jetty-plus" % JettyVersion % "provided",
  "org.eclipse.jetty"   % "jetty-webapp" % JettyVersion % "provided",
  "org.eclipse.jetty.websocket"   % "websocket-server" % JettyVersion % "provided",
  "javax.servlet"        % "javax.servlet-api" % "3.1.0"  % "provided"
)

enablePlugins(SbtTwirl)
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
