package com.matsta25.efairy.repository;

import com.matsta25.efairy.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findQuestionById(Long id);
}
