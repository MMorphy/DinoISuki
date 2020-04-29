package hr.go2.play.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.QuizAnswers;
import hr.go2.play.entities.QuizQuestions;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.QuizAnswersRepository;
import hr.go2.play.services.QuizAnswersService;

@Service
@Transactional
public class QuizAnswersServiceImpl implements QuizAnswersService {

	@Autowired
	private QuizAnswersRepository quizAnswersRepo;

	@Override
	public Optional<QuizAnswers> findByUserId(User user) {
		return quizAnswersRepo.findByUserId(user);
	}

	@Override
	public QuizAnswers saveQuizAnswers(QuizAnswers quizAnswers) {
		return quizAnswersRepo.save(quizAnswers);
	}

	@Override
	public Optional<QuizAnswers> findByUserIdAndQuizId(User user, QuizQuestions quizId) {
		return quizAnswersRepo.findByUserIdAndQuizId(user, quizId);
	}

}
