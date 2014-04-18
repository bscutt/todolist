package repositories

import models.Task

trait TaskRepository {

  def all(): List[Task]

  def create(label: String)

  def delete(id: Long)

}