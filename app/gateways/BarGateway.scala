package gateways

import javax.inject._

import play.api.http.Status._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import play.api.libs.ws.WSClient

import scala.concurrent.Future

class BarGateway @Inject()(ws: WSClient) {
  def bar(): Future[Option[JsValue]] = {
    ws.url("http://httpbin.org/get").get().map {
      response =>
        response.status match {
          case OK => Some(response.json)
          case _ => None
        }
    }
  }
}
