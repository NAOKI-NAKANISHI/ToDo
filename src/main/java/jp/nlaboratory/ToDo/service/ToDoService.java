package jp.nlaboratory.ToDo.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.nlaboratory.ToDo.Entity.Task;
import jp.nlaboratory.ToDo.Entity.TaskType;
import jp.nlaboratory.ToDo.model.TaskModel;
import jp.nlaboratory.ToDo.repository.TaskRepository;
import jp.nlaboratory.ToDo.repository.TaskTypeRepository;

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
     * @return タスク
     * @throws Exception
     */
    public Task getTaskById(int id) throws Exception {

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

    /**
     *タスクを全件取得（削除済みのタスクは除く）
     * 
     * @return すべてのタスク
     * @throws Exception
     */
    public List<Task> getAllAndDelFlg() throws Exception {

    	return taskRepository.findAllAndDelFlg();

    }

    /**
     * タスク作成/更新
     *　
     * @param model タスク情報を格納したモデル
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveTask(TaskModel model) throws Exception {

        Task task = null;
        
        if (model.getTaskId() == 0) {

            // 新規作成の場合
            task = new Task();
            task.setCreatedDate(ZonedDateTime.now(ZoneId.of("Asia/Tokyo")));

        } else {

            // 更新対象のタスクを取得
            task = getTaskById(model.getTaskId());
            if (task == null) {
                // 更新対象のタスクが存在しない場合はエラー
                throw new Exception("更新対象のタスクが見つかりませんでした。タスクID=" + model.getTaskId());
            }

        }

        task.setTaskType(getTaskTypeById(model.getTaskTypeId()));
        task.setTitle(model.getTitle());
        task.setDetail(model.getDetail());
        task.setLimitDate(strToZonedDateTime(model.getLimitDate()));
        task.setUpdatedDate(ZonedDateTime.now(ZoneId.of("Asia/Tokyo")));

        // タスクの保存
        taskRepository.save(task);

    }

     /**
     * タスク削除(論理削除)
     *
     * @param taskId タスクID
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTask(int taskId) throws Exception {

        // 削除対象のタスクを取得
        Task task = getTaskById(taskId);
        if (task == null) {
            // 削除対象のタスクが存在しない場合はエラー
            throw new Exception("削除対象のタスクが見つかりませんでした。タスクID=" + taskId);
        }

        // 削除フラグをtrueにする
        task.setDelFlg(true);

        // タスクの更新
        taskRepository.save(task);

    }

     /**
     * 文字列からZonedDateTimeに変換
     *
     * @param dateStr 日付（文字列）
     * @throws Exception
     * @return 日付（ZonedDateTime）
     */
    public ZonedDateTime strToZonedDateTime(String dateStr) throws Exception {

        LocalDateTime date = LocalDateTime.parse(dateStr); 
		ZoneId zone = ZoneId.systemDefault();

        return ZonedDateTime.of(date, zone);
        
    }

}