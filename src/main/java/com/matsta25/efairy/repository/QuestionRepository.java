package com.matsta25.efairy.repository;

import com.matsta25.efairy.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findQuestionById(Long id);

    List<Question> findByUserId(String userId);

    Question findByIdAndUserId(Long id, String userId);
}
