package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.QuizQuestions;
import hr.go2.play.entities.User;

@Repository
public interface QuizQuestionsRepository extends JpaRepository<QuizQuestions, Long> {

	public Optional<QuizQuestions> findByName(String name);

	@Query(value = "SELECT qq FROM QuizQuestions qq LEFT OUTER JOIN QuizAnswers qa ON qq.id = qa.quizId and qa.userId = ?1 WHERE qq.status = 1 AND qa.id IS NULL ORDER BY qq.createdAt DESC")
	public Collection<QuizQuestions> getNewQuizesForUser(User user);

	@Query(value = "SELECT qq FROM QuizQuestions qq JOIN QuizAnswers qa ON qq.id = qa.quizId WHERE qq.status = 1 and qa.userId = ?1 ORDER BY qq.createdAt DESC")
	public Collection<QuizQuestions> getQuizesTakenByUser(User user);

	@Query(value = "SELECT COUNT(qa.id) FROM QuizQuestions qq JOIN QuizAnswers qa ON qq.id = qa.quizId WHERE qq.name = ?1")
	public int getNoOfUsersParticipatedInQuiz(String quizName);

}
