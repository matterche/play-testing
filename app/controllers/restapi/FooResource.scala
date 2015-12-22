package controllers.restapi

import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import play.api.mvc.{Action, Controller}
import services.FooBarService


class FooResource @Inject()(fooBarService: FooBarService) extends Controller {

  def foo() = Action.async { request =>
    fooBarService.fooBar().map {
      case Some(json: JsValue) => Ok(json)
      case _ => BadRequest
    }
  }

}
