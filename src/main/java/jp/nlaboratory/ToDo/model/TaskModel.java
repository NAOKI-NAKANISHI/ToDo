package jp.nlaboratory.ToDo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TaskModel implements Serializable {

    private static final long serialVersionUID = 3453351722218641237L;
	
    // タスクID
	private  int taskId;
    // タスク種別ID
	private  int taskTypeId;
    // やること
    private String title;
    // 詳細
    private String detail;
    // 期限
    private String limitDate;

}
