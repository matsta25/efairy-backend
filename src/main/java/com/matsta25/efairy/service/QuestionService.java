package com.matsta25.efairy.service;

import com.matsta25.efairy.model.Answer;
import com.matsta25.efairy.model.Question;
import com.matsta25.efairy.repository.AnswerRepository;
import com.matsta25.efairy.repository.QuestionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(
            QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public List<Question> getQuestions(String userId) {
        return this.questionRepository.findByUserId(userId);
    }

    public Question createQuestion(String userId, String questionContent) {
        return this.questionRepository.save(new Question(userId, questionContent));
    }

    public Answer createAnswerForQuestion(Long questionId, String answerContent) {
        Question searchQuestion = questionRepository.findQuestionById(questionId);
        Answer newAnswer = this.answerRepository.save(new Answer(answerContent));
        searchQuestion.setAnswer(newAnswer);
        return questionRepository.save(searchQuestion).getAnswer();
    }
}
