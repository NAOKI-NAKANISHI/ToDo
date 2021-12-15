package jp.nlaboratory.ToDo.Entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="task")
public class Task implements Serializable  {
    
    private static final long serialVersionUID = 3453583737318640866L;

    /** ID **/
    @Id
    @Column(name="id", nullable = false, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** タスク種別 **/
    @ManyToOne
    @JoinColumn(name="task_type_id", nullable = false, referencedColumnName = "id")
    private TaskType taskType;

    /** やること **/
    @Column(name="title", nullable = false)
    private String title;

    /** 詳細 **/
    @Column(name="detail", nullable = false)
    private String detail;

    /** 期限 **/
    @Column(name="limit_date", nullable = false)
    private ZonedDateTime limitDate;

    /** 作成日時 **/
    @Column(name="created_date", nullable = false)
    private ZonedDateTime createdDate;

    /** 更新日時 **/
    @Column(name="updated_date", nullable = true)
    private ZonedDateTime updatedDate;
    
    /** 削除フラグ **/
    @Column(name="del_flg", nullable = false, length = 1)
    private boolean delFlg;

}
