package controllers

import org.scalatest.{Matchers, WordSpec}
import play.api.mvc.Result
import play.api.test.Helpers._
import play.api.test.{FakeApplication, FakeRequest}

import scala.concurrent.Future

class HelloControllerSpec extends WordSpec with Matchers {
  "HelloController" when {
    "index is called" should {
      "greet" in {
        val application: FakeApplication = FakeApplication()
        val helloController: HelloController = application.injector.instanceOf[HelloController]
        val result: Future[Result] = helloController.index().apply(FakeRequest())
        contentAsString(result) shouldEqual "Hello World"
      }
    }
  }
}
