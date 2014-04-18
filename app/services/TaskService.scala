package services

import repositories.TaskRepository

class TaskService(repository: TaskRepository) {

  def findAll() = { repository.all() }

  def create(label: String) = { repository.create(label) }

  def delete(id: Long) = { repository.delete(id) }

}