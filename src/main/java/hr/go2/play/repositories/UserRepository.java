package hr.go2.play.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
