package jp.nlaboratory.ToDo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.nlaboratory.ToDo.Entity.Task;
import jp.nlaboratory.ToDo.Entity.TaskType;
import jp.nlaboratory.ToDo.repository.TaskRepository;
import jp.nlaboratory.ToDo.repository.TaskTypeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ToDoService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskTypeRepository taskTypeRepository;

    /**
     * タスク種別IDをもとにタスク種別を取得
     * 
     * @param id タスク種別ID
     * @return タスク種別
     * @throws Exception
     */
    public TaskType getTaskTypeById(int id) throws Exception {

    	return taskTypeRepository.findById(id);

    }

    /**
     *タスク種別を全件取得
     * 
     * @return すべてのタスク種別
     * @throws Exception
     */
    public List<TaskType> getAllTaskType() throws Exception {

    	return taskTypeRepository.findAll();

    }

    /**
     * タスクIDをもとにタスクを取得
     * 
     * @param id タスクID
     * @return すべてのタスク
     * @throws Exception
     */
    public Task getAllTaskById(int id) throws Exception {

    	return taskRepository.findById(id);

    }

    /**
     *タスクを全件取得
     * 
     * @return すべてのタスク
     * @throws Exception
     */
    public List<Task> getAllTask() throws Exception {

    	return taskRepository.findAll();

    }

}