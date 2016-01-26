package services

import javax.inject.Inject

import gateways.{BarGateway, FooGateway}
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future


class FooBarService @Inject()(fooGateway: FooGateway, barGateway: BarGateway) {
  def fooBar(): Future[Option[JsValue]] = {

    val result = for {
      foo <- fooGateway.foo()
      bar <- barGateway.bar()
    } yield Some(Json.obj("foo" -> foo, "bar" -> bar))

    result.recover {
      case t: Throwable =>
        Logger.error(s"error getting stuff from internet", t)
        None
    }

    result
  }
}
