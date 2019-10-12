package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Location;
import hr.go2.play.entities.User;
import hr.go2.play.entities.WorkingHours;

public interface LocationRepository extends JpaRepository<Location, Long>{

	public Optional<Location> findByAddress(String address);

	public Optional<Location> findByAddressLike(String address);

	public Optional<Location> findByContactUser(User contactUser);

	public Collection<Location> findByHours(WorkingHours wh);

	public Optional<Location> findByFields_Sport(String sport);
}
