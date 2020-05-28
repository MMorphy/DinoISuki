package hr.go2.play.services;

import java.util.Date;
import java.util.List;

import hr.go2.play.entities.User;
import hr.go2.play.entities.UserSession;

public interface UserSessionService {

	public abstract UserSession save(UserSession userSession);

	public abstract List<UserSession> findByUser(User user);

	public abstract List<UserSession> findOpenSessionsByUser(User user);

	public abstract void closeSessions(User user, Date date);
}
