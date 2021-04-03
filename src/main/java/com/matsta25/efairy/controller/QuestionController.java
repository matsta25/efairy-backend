package com.matsta25.efairy.controller;

import com.matsta25.efairy.model.Answer;
import com.matsta25.efairy.model.Question;
import com.matsta25.efairy.service.QuestionService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> getQuestions() {
        return this.questionService.getQuestions();
    }

    @PostMapping
    public Question createQuestion(@RequestBody String questionContent) {
        return this.questionService.createQuestion(questionContent);
    }

    @PostMapping("/{questionId}/answer")
    public Answer createAnswerForQuestion(
            @PathVariable Long questionId, @RequestBody String answerContent) {
        return this.questionService.createAnswerForQuestion(questionId, answerContent);
    }
}
