package hr.go2.play.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Term;

public interface TermRepository extends JpaRepository<Term, Long>{

}
