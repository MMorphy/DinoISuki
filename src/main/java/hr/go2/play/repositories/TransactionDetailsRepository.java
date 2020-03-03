package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.TransactionDetails;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {

	public Collection<TransactionDetails> findByUsername(String username);

	public void deleteByUsername(String username);

	public Optional<TransactionDetails> findByTransactionId(String transactionId);

	public void deleteByTransactionId(String transactionId);

	public Collection<TransactionDetails> findByTransactionStatus(String transactionStatus);

}
