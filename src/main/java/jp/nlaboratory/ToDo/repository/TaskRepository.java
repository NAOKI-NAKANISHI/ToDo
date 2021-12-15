package jp.nlaboratory.ToDo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jp.nlaboratory.ToDo.Entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Task findById(int id);

	@Query(value = "select e from Task e where del_flg = 0")
	List<Task> findAllAndDelFlg();

}