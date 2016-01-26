package controllers.restapi

import org.scalatest.TestData
import org.scalatestplus.play.{OneServerPerTest, PlaySpec}
import play.api.http.Status
import play.api.libs.json.{JsDefined, JsString}
import play.api.libs.ws.WS
import play.api.mvc._
import play.api.test._

class FooResourceWithTestServerSpec extends PlaySpec with Results with Status with OneServerPerTest {

  implicit override def newAppForTest(testData: TestData): FakeApplication =
    FakeApplication(
      withoutPlugins = Seq("play.api.cache.EhCachePlugin", "de.deutschepost.e42.play.ws.E42WSPlugin", "de.leanovate.play.fastcgi.FastCGIPlugin"),
      additionalConfiguration = Map("ehcacheplugin" -> "disabled", "play.modules.disabled" -> Seq("play.api.cache.EhCacheModule", "de.leanovate.play.fastcgi.FastCGIPlugin"))
    )

  "Broad stack integration tests" when {
    "run with full http stack" should {
      "use the real endpoints" in new DefaultAwaitTimeout {

        // Runs a real http server with the fake application and call it via Http.
        // Requests to webservices are not mocked

        val response = Helpers.await(WS.url(s"http://localhost:$port/foo").get())

        response.status mustBe (OK)
        (response.json \ "bar" \ "url") mustBe JsDefined(JsString("http://httpbin.org/get"))
        (response.json \ "foo" \ "url") mustBe JsDefined(JsString("http://httpbin.org/get"))
      }
    }
  }

}
