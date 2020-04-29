package hr.go2.play.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.QuizAnswers;
import hr.go2.play.entities.QuizQuestions;
import hr.go2.play.entities.User;

@Repository
public interface QuizAnswersRepository extends JpaRepository<QuizAnswers, Long> {

	public Optional<QuizAnswers> findByUserId(User userId);

	public Optional<QuizAnswers> findByUserIdAndQuizId(User user, QuizQuestions quizId);

}
