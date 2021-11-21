package com.github.nikalaikina.coral

import com.github.nikalaikina.coral.config.AppConfig
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import java.io.{BufferedReader, InputStreamReader}

object Boot extends App {
  ConfigSource.default.load[AppConfig].foreach { config =>
    val p = Runtime.getRuntime.exec(config.processCommand)
    val input = new BufferedReader(new InputStreamReader(p.getInputStream))
    println(input.readLine)
    input.close()
  }
}
