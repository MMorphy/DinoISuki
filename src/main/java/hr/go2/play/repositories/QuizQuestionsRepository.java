package hr.go2.play.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.QuizQuestions;

@Repository
public interface QuizQuestionsRepository extends JpaRepository<QuizQuestions, Long> {

	public Optional<QuizQuestions> findByName(String name);

}
