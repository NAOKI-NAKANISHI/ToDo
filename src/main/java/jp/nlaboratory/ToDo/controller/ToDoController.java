package jp.nlaboratory.ToDo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.nlaboratory.ToDo.model.TaskModel;
import jp.nlaboratory.ToDo.service.ToDoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ToDoController {

    @Autowired
    ToDoService toDoService;

    /**
     * TOPページ遷移処理
     *
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) throws IOException {

        try {

            // タスクの全件取得
        	model.addAttribute("allTask", toDoService.getAllAndDelFlg());
            // タスク種別の全件取得
        	model.addAttribute("allTaskType", toDoService.getAllTaskType());

        } catch (Exception e) {

            log.error("予期せぬエラーが発生しました。", e);

            return "error";

        }

        return "index";

    }

    /**
     * タスクの作成/更新
     *
     * @param model タスク情報を格納したモデル
     * @return resultMap
     */
    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> update(@RequestBody TaskModel model) throws Exception {

        String method = model.getTaskId() == 0 ? "作成" : "更新";
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("resultCode", 9);
        resultMap.put("msg", "タスクの" + method + "に失敗しました。");

        try {

            // タスクの作成/更新
            toDoService.saveTask(model);


        } catch (Exception e) {

            log.error("タスクの" + method + "に失敗しました。id={}", model.getTaskId(), e);

    		return resultMap;

        }

        resultMap.put("resultCode", 0);
        resultMap.put("msg", "タスクの" + method + "に成功しました。");

        return resultMap;

    }

    /**
     * タスクの削除(論理削除)
     *
     * @param model タスク情報を格納したモデル
     * @return resultMap
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody TaskModel model) throws Exception {

    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("resultCode", 9);
        resultMap.put("msg", "タスクの削除に失敗しました。");

        try {

            // タスクの削除
            toDoService.deleteTask(model.getTaskId());


        } catch (Exception e) {

            log.error("タスクの削除に失敗しました。id={}", model.getTaskId(), e);

    		return resultMap;

        }

        resultMap.put("resultCode", 0);
        resultMap.put("msg", "タスクの削除に成功しました。");

        return resultMap;

    }

}