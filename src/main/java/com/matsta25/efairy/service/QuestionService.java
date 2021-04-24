package com.matsta25.efairy.service;

import com.matsta25.efairy.model.Answer;
import com.matsta25.efairy.model.Question;
import com.matsta25.efairy.repository.AnswerRepository;
import com.matsta25.efairy.repository.QuestionRepository;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    public List<Question> getQuestions(Authentication authentication) {
        if (authentication
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_moderator"))) {
            return this.questionRepository.findAll();
        }
        return this.questionRepository.findByUserId(authentication.getName());
    }

    public Question getQuestion(Authentication authentication, Long id) {
        if (authentication
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_moderator"))) {
            return this.questionRepository.findById(id).get();
        }
        return this.questionRepository.findByIdAndUserId(id, authentication.getName());
    }

    public Question createQuestion(Authentication authentication, String questionContent) {
        return this.questionRepository.save(
                new Question(authentication.getName(), questionContent));
    }

    public Answer createAnswerForQuestion(Long questionId, String answerContent) {
        Question searchQuestion = questionRepository.findQuestionById(questionId);
        Answer newAnswer = this.answerRepository.save(new Answer(answerContent));
        searchQuestion.setAnswer(newAnswer);
        return questionRepository.save(searchQuestion).getAnswer();
    }
}
