package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import models.Task
import repositories.AnormTaskRepository
import services.TaskService

object Application extends Controller {

  val taskService = new TaskService(new AnormTaskRepository)

  val taskForm = Form(
    "label" -> nonEmptyText)

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action.async {
    Future { taskService.findAll() }.map { tasks =>
      Ok(views.html.index(tasks, taskForm))
    }
  }

  def newTask = Action.async { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => {
        Future { taskService.findAll() }.map { tasks =>
          BadRequest(views.html.index(tasks, errors))
        }
      },
      label => {
        Future { taskService.create(label) }.map { nil =>
          Redirect(routes.Application.tasks)
        }
      })
  }

  def deleteTask(id: Long) = Action.async {
    Future { taskService.delete(id) }.map { nil =>
      Redirect(routes.Application.tasks)
    }
  }

}