package controllers.restapi

import javax.inject.{Provider, Singleton}

import mockws.MockWS
import org.scalatest.{Matchers, WordSpecLike}
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.libs.json.Json.obj
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Result, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future

class WSClientProvider() extends Provider[WSClient] with Results {
  def get() = MockWS {
    case ("GET", "http://httpbin.org/get") => Action {
      Ok(obj("hello" -> "foo"))
    }
  }
}

class FooResourceSpec extends WordSpecLike with Matchers {


  class FooTestScope {

    val application = new GuiceApplicationBuilder()
      .overrides(bind[WSClient].toProvider[WSClientProvider].in[Singleton])
      .build

    val fooResource: FooResource = application.injector.instanceOf[FooResource]
    val result: Future[Result] = fooResource.foo().apply(FakeRequest())

  }

  "GET /foo" when {
    "called" should {
      "return a Json object" in new FooTestScope() {
        contentAsJson(result) shouldEqual obj("foo" -> obj("hello" -> "foo"), "bar" -> obj("hello" -> "foo"))
      }
    }
  }

}
