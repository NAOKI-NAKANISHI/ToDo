package jp.nlaboratory.ToDo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.nlaboratory.ToDo.Entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findById(int id);
}