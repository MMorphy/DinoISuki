package hr.go2.play.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.TransactionDetails;
import hr.go2.play.repositories.TransactionDetailsRepository;
import hr.go2.play.services.TransactionDetailsService;
@Service
@Transactional
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

	@Autowired
	private TransactionDetailsRepository transactionDetailsRepo;

	@Override
	public List<TransactionDetails> findAllTransactionDetails() {
		return transactionDetailsRepo.findAll();
	}

	@Override
	public Optional<TransactionDetails> findTransactionDetailsById(Long id) {
		return transactionDetailsRepo.findById(id);
	}

	@Override
	public void deleteTransactionDetailsById(Long id) {
		transactionDetailsRepo.deleteById(id);
	}

	@Override
	public TransactionDetails saveTransactionDetails(TransactionDetails transactionDetails) {
		return transactionDetailsRepo.save(transactionDetails);
	}

	@Override
	public void deleteAllTransactionDetails() {
		transactionDetailsRepo.deleteAll();
	}

	@Override
	public Collection<TransactionDetails> findByUsername(String username) {
		return transactionDetailsRepo.findByUsername(username);
	}

	@Override
	public void deleteByUsername(String username) {
		transactionDetailsRepo.deleteByUsername(username);
	}

	@Override
	public Optional<TransactionDetails> findByTransactionId(String transactionId) {
		return transactionDetailsRepo.findByTransactionId(transactionId);
	}

	@Override
	public void deleteByTransactionId(String transactionId) {
		transactionDetailsRepo.deleteByTransactionId(transactionId);
	}

	@Override
	public Collection<TransactionDetails> findByTransactionStatus(String transactionStatus) {
		return transactionDetailsRepo.findByTransactionStatus(transactionStatus);
	}

	@Override
	public Boolean existsById(Long id) {
		return transactionDetailsRepo.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		transactionDetailsRepo.deleteById(id);
	}

}
