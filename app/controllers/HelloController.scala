package controllers

import play.api.mvc._

class HelloController extends Controller {

  def index = Action {
    Ok("Hello World")
  }

}
