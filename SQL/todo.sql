drop database if exists todo;

CREATE DATABASE todo DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
use todo;

-- タスク種別テーブルの作成
drop table if exists task_type;

CREATE TABLE task_type (
  id int(11) NOT NULL AUTO_INCREMENT,
  task_name varchar(255) NOT NULL COMMENT 'タスク種別',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='タスク種別テーブル';

INSERT INTO `task_type` VALUES (1, '緊急');
INSERT INTO `task_type` VALUES (2, '重要');
INSERT INTO `task_type` VALUES (3, 'できれば');

-- タスクテーブルの作成
drop table if exists task;

CREATE TABLE task (
  id int(11) NOT NULL AUTO_INCREMENT,
  task_type_id int(11) NOT NULL COMMENT '種別ID',
  title varchar(255) NOT NULL COMMENT 'やること',
  detail text NOT NULL COMMENT '詳細',
  limit_date datetime NOT NULL COMMENT '期限',
  created_date datetime NOT NULL COMMENT '作成日時',
  updated_date datetime DEFAULT NULL COMMENT '更新日時',
  del_flg tinyint(1) NOT NULL DEFAULT '0' COMMENT '削除フラグ',
  PRIMARY KEY (id),
  FOREIGN KEY(task_type_id)
  REFERENCES task_type(id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='タスクテーブル';
