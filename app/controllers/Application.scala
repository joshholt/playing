package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.concurrent._

import models.Task



object Application extends Controller {

  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {
    Async {
      Task.all().orTimeout("Query timed out", 1000).map { resultOrTimeout =>
        resultOrTimeout.fold(
          result => Ok(views.html.index(result, taskForm)),
          timeout => InternalServerError(timeout)
        )
      }
    }
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => Async {
          Task.all().orTimeout("Query timed out", 1000).map { resultOrTimeout =>
            resultOrTimeout.fold(
              result => BadRequest(views.html.index(result, errors)),
              timeout => InternalServerError(timeout)
            )
          }
        },
      label  => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      }
    )
  }

  def deleteTask(id:Long) = Action {
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }

}