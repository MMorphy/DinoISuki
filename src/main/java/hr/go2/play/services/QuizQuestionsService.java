package hr.go2.play.services;

import java.util.List;
import java.util.Optional;

import hr.go2.play.entities.QuizQuestions;

public interface QuizQuestionsService {

	public abstract QuizQuestions saveQuizQuestions(QuizQuestions quizQuestions);

	public abstract Optional<QuizQuestions> findByName(String name);

	public abstract List<QuizQuestions> findAll();

}
