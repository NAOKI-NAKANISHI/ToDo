package jp.nlaboratory.ToDo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        	model.addAttribute("allTask", toDoService.getAllTask());
            // タスク種別の全件取得
        	model.addAttribute("allTaskType", toDoService.getAllTaskType());

        } catch (Exception e) {

            log.error("予期せぬエラーが発生しました。", e);
            return "error";

        }

        return "index";

    }

}