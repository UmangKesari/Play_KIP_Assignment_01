package controllers

import javax.inject._

import akka.util.ByteString
import play.api._
import play.api.http.HttpEntity
import play.api.libs.iteratee.Enumerator
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController extends Controller{

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    //  Ok(views.html.index

    Ok("Please enter your name and password in the above as query URL")

   /* Result(
      header = ResponseHeader(404),
      body = HttpEntity.Strict(ByteString("Please enter your name and password in the above as query URL"),Some("text/plain"))
    )*/
  }

  def setUserSession(name : String, password : String) = Action{ implicit request : Request[AnyContent] =>
    /* Result(*/
    /*   header = ResponseHeader(200),*/
    /*   body = HttpEntity.Strict(ByteString(s"$id ==> $name"),Some("text/plain"))*/
    /* )*/
    Redirect(routes.HomeController.getUserSession()).withSession("name" -> s"$name","password"-> s"$password")
  }

  def getUserSession() = Action {
    implicit  request : Request[AnyContent] =>
      val username = request.session.get("name")
      val password =request.session.get("password")

      val (userKey,message) = {
        if (username.equals("UmangKesari") && password.equals("OldTrafford"))
          ("Success", s"Welcome, $username")
        else
          ("Error", "Either Username or password is invalid ")
      }
      /*
      Result(
        header = ResponseHeader(200),
          body = HttpEntity.Strict(ByteString(s"$name + $password"),Some("text/plain"))
      )*/
        Redirect(routes.HomeController.printMessage()).flashing(userKey -> message)
  }
  def printMessage() = Action{
    implicit request : Request[AnyContent] =>
      Ok(views.html.index(request.flash))
  }


}
