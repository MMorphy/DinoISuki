package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.User;
import hr.go2.play.entities.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

	List<UserSession> findByUserId(User user);

	@Query("SELECT us FROM UserSession us WHERE us.sessionEnd IS NULL AND us.userId = (?1)")
	Collection<UserSession> findOpenSessionsByUser(User user);

	@Modifying
	@Query("UPDATE UserSession us SET us.sessionEnd = CURRENT_TIMESTAMP WHERE us.userId = (?1) AND us.sessionStart < (?2) AND us.sessionEnd IS NULL")
	void closeSessions(User user, Date date);

}
