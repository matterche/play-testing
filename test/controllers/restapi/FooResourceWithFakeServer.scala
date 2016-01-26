package controllers.restapi

import gateways.FooGateway
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.libs.json._
import play.api.mvc._
import play.api.routing.sird._
import play.api.test._
import play.core.server.Server
import play.test.WithApplication

import scala.concurrent.Await
import scala.concurrent.duration._

class FooResourceWithFakeServer extends PlaySpec with Results with Status {
  "Gateway integration tests" when {
    "run against a faked webservice" should {
      "return a Json object" in new WithApplication with DefaultAwaitTimeout {
        Server.withRouter() {
          case GET(p"/get") => Action {
            Ok(Json.obj("hello" -> "foo"))
          }
        } { implicit port =>
          WsTestClient.withClient { client =>
            Await.result(new FooGateway(client, baseUrl = "").foo(), 10.seconds) mustBe Some(Json.obj("hello" -> "foo"))
          }
        }
      }
    }
  }
}
