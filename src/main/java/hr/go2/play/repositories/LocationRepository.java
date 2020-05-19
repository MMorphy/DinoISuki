 package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.go2.play.DTO.VideoDetailsDTO;
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Location;
import hr.go2.play.entities.WorkingHours;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{

	public Optional<Location> findByAddress(String address);

	public Optional<Location> findByAddressLike(String address);

	public Collection<Location> findByHours(WorkingHours wh);

	public Optional<Location> findByName(String name);

	public Optional<Location> findByContactInformation(ContactInformation info);

	public Optional<Location> findByContactInformation_Email (String email);

	public Optional<Location> findByContactInformation_TelephoneNumber(String num);

	@Query("SELECT new hr.go2.play.DTO.VideoDetailsDTO(v.id, v.startedAt, l.name, v.location) "
		+ "FROM Location l "
		+ "JOIN l.fields f "
		+ "JOIN f.cameras c "
			+ "JOIN c.videos v WHERE v.archived = FALSE")
	public Collection<VideoDetailsDTO> findAllVideoDetails();

	@Query("SELECT l FROM Location l JOIN l.fields f JOIN f.cameras c WHERE c.name = ?1")
	public Optional<Location> findLocationByCameraName(String cameraName);

	@Query("SELECT h FROM Location l JOIN l.hours h WHERE l.id = ?1")
	public Collection<WorkingHours> findWorkingHoursByLocationId(Long locationId);
}
