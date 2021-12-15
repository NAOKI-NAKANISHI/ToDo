package jp.nlaboratory.ToDo.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="task_type")
public class TaskType implements Serializable  {
    
    private static final long serialVersionUID = 3453583737318640966L;

    /** ID **/
    @Id
    @Column(name="id", nullable = false, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** 種別名 **/
    @Column(name="task_name", nullable = false)
    private String taskName;

}
