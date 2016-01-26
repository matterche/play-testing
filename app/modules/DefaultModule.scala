package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names

class DefaultModule extends AbstractModule {
  def configure() = {
    bindConstant()
      .annotatedWith(Names.named("baseUrl"))
      .to("http://httpbin.org")
  }
}
