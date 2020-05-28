package hr.go2.play.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.User;
import hr.go2.play.entities.UserSession;
import hr.go2.play.repositories.UserSessionRepository;
import hr.go2.play.services.UserSessionService;

@Service
@Transactional
public class UserSessionServiceImpl implements UserSessionService {

	@Autowired
	private UserSessionRepository UserSessionRepo;

	@Override
	public UserSession save(UserSession userSession) {
		return UserSessionRepo.save(userSession);
	}

	@Override
	public List<UserSession> findByUser(User user) {
		return UserSessionRepo.findByUserId(user);
	}

	@Override
	public List<UserSession> findOpenSessionsByUser(User user) {
		return (List<UserSession>) UserSessionRepo.findOpenSessionsByUser(user);
	}

	@Override
	public void closeSessions(User user, Date date) {
		UserSessionRepo.closeSessions(user, date);
	}

}
