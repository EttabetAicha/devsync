package org.aicha.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.aicha.model.Task;
import org.aicha.model.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TaskService {
    @PersistenceContext(unitName="myPU")
    private EntityManager entityManager;
    public Task getTaskById(long id){
        return entityManager.find(Task.class, id);
    }
    public List<Task> getAllTasks(){
        return entityManager.createQuery("SELECT t FROM Task t ",Task.class).getResultList();
    }
    public List<Task> getTaskByUserId(long userId){
        TypedQuery<Task> query= entityManager.createQuery("SELECT t FROM Task t WHERE t.userId = :userId", Task.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    public Task createTask(String title, String description, LocalDateTime dueDate, Long userId) {
        Task task = new Task(title, description, dueDate, userId);
        entityManager.persist(task);
        return task;
    }
    public Task updateTask(Task task) {
        return entityManager.merge(task);
    }
    public void deleteTask(long id) {
        Task task = entityManager.find(Task.class, id);
        if (task!= null) {
            entityManager.remove(task);
        }
    }
    public void updateTaskStatus(Long id, TaskStatus status) {
        Task task=getTaskById(id);
        if(task!=null){
            task.setStatus(status);
            entityManager.merge(task);
        }
    }



}
