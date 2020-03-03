package hr.go2.play.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import hr.go2.play.entities.TransactionDetails;

public interface TransactionDetailsService {

	public abstract List<TransactionDetails> findAllTransactionDetails();

	public abstract Optional<TransactionDetails> findTransactionDetailsById(Long id);

	public abstract void deleteTransactionDetailsById(Long id);

	public abstract TransactionDetails saveTransactionDetails(TransactionDetails transactionDetails);

	public abstract void deleteAllTransactionDetails();

	public abstract Boolean existsById(Long id);

	public abstract Collection<TransactionDetails> findByUsername(String username);

	public abstract void deleteByUsername(String username);

	public abstract Optional<TransactionDetails> findByTransactionId(String transactionId);

	public abstract void deleteByTransactionId(String transactionId);

	public abstract Collection<TransactionDetails> findByTransactionStatus(String transactionStatus);

	public abstract void deleteById(Long id);
}
