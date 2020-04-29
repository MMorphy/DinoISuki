package hr.go2.play.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.QuizQuestions;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.QuizQuestionsRepository;
import hr.go2.play.services.QuizQuestionsService;

@Service
@Transactional
public class QuizQuestionsServiceImpl implements QuizQuestionsService {

	@Autowired
	private QuizQuestionsRepository quizQuestionsRepo;

	@Override
	public QuizQuestions saveQuizQuestions(QuizQuestions quizQuestions) {
		return quizQuestionsRepo.save(quizQuestions);
	}

	@Override
	public Optional<QuizQuestions> findByName(String name) {
		return quizQuestionsRepo.findByName(name);
	}

	@Override
	public List<QuizQuestions> findAll() {
		return quizQuestionsRepo.findAll();
	}

	@Override
	public List<QuizQuestions> getNewQuizesForUser(User user) {
		return (List<QuizQuestions>) quizQuestionsRepo.getNewQuizesForUser(user);
	}

	@Override
	public List<QuizQuestions> getQuizesTakenByUser(User user) {
		return (List<QuizQuestions>) quizQuestionsRepo.getQuizesTakenByUser(user);
	}

}
