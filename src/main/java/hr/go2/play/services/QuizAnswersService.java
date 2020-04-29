package hr.go2.play.services;

import java.util.List;
import java.util.Optional;

import hr.go2.play.entities.QuizAnswers;
import hr.go2.play.entities.QuizQuestions;
import hr.go2.play.entities.User;

public interface QuizAnswersService {

	public abstract Optional<QuizAnswers> findByUserId(User user);

	public abstract QuizAnswers saveQuizAnswers(QuizAnswers quizAnswers);

	public abstract Optional<QuizAnswers> findByUserIdAndQuizId(User user, QuizQuestions quizId);

	public abstract List<QuizAnswers> getAllAnswersForQuiz(String quizName);

}
