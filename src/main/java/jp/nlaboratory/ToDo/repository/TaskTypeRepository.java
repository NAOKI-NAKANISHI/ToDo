package jp.nlaboratory.ToDo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.nlaboratory.ToDo.Entity.TaskType;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Integer> {
    TaskType findById(int id);
}