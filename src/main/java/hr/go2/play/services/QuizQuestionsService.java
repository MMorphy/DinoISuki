package hr.go2.play.services;

import java.util.List;
import java.util.Optional;

import hr.go2.play.entities.QuizQuestions;
import hr.go2.play.entities.User;

public interface QuizQuestionsService {

	public abstract QuizQuestions saveQuizQuestions(QuizQuestions quizQuestions);

	public abstract Optional<QuizQuestions> findByName(String name);

	public abstract List<QuizQuestions> findAll();

	public abstract List<QuizQuestions> getNewQuizesForUser(User user);

	public abstract List<QuizQuestions> getQuizesTakenByUser(User user);

}
