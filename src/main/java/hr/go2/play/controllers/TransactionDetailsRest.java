package hr.go2.play.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.DTO.TransactionDetailsDTO;
import hr.go2.play.entities.TransactionDetails;
import hr.go2.play.services.TransactionDetailsService;
import hr.go2.play.util.Commons;

@RestController
@RequestMapping(path = "/api/transactions")
public class TransactionDetailsRest {

	Logger logger = LoggerFactory.getLogger(getClass());

	ModelMapper mapper = new ModelMapper();

	@Autowired
	private TransactionDetailsService transactionDetailsService;

	@Autowired
	private Commons commons;

	/*
	 * Description: Fetch transaction details
	 * Filtering options: by user name or by transactionId or all
	 * Call example:
	 * https://localhost:8443/api/transactions/getTransactionDetails?username=test6&transactionId=12345
	 *
	 */
	@GetMapping("/getTransactionDetails")
	public ResponseEntity<?> getTransactionDetails(@RequestParam(name = "username", required = false) String username, @RequestParam(name = "transactionId", required = false) String transactionId) {
		logger.debug("/api/transactions/getTransactionDetails Started");

		List<TransactionDetailsDTO> transactionDetailsDTOList = new ArrayList<TransactionDetailsDTO>();

		if (transactionId != null && !transactionId.isEmpty()) {
			Optional<TransactionDetails> transactionDetails = transactionDetailsService.findByTransactionId(transactionId);
			if (transactionDetails.isEmpty()) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("Transaction with transactin id: " + transactionId + " not found."), HttpStatus.NOT_FOUND);
			} else {
				TransactionDetailsDTO transactionDetailsDTO = mapper.map(transactionDetails.get(), TransactionDetailsDTO.class);
				transactionDetailsDTOList.add(transactionDetailsDTO);
				return new ResponseEntity<List<TransactionDetailsDTO>>(transactionDetailsDTOList, HttpStatus.OK);
			}
		}

		if (username != null && !username.isEmpty()) {
			Collection<TransactionDetails> transactionDetailsCollection = transactionDetailsService.findByUsername(username);
			if (transactionDetailsCollection.size() == 0) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("No transactions found for user: " + username), HttpStatus.NOT_FOUND);
			}
			transactionDetailsCollection.forEach(transactionDetails -> {
				TransactionDetailsDTO transactionDetailsDTO = mapper.map(transactionDetails, TransactionDetailsDTO.class);
				transactionDetailsDTOList.add(transactionDetailsDTO);
			});
			return new ResponseEntity<List<TransactionDetailsDTO>>(transactionDetailsDTOList, HttpStatus.OK);
		}

		Collection<TransactionDetails> transactionDetailsCollection = transactionDetailsService.findAllTransactionDetails();
		if (transactionDetailsCollection.size() == 0) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("No transactions found"), HttpStatus.NOT_FOUND);
		}
		transactionDetailsCollection.forEach(transactionDetails -> {
			TransactionDetailsDTO transactionDetailsDTO = mapper.map(transactionDetails, TransactionDetailsDTO.class);
			transactionDetailsDTOList.add(transactionDetailsDTO);
		});
		return new ResponseEntity<List<TransactionDetailsDTO>>(transactionDetailsDTOList, HttpStatus.OK);
	}

	/*
	 * Description: Add transaction details
	 * Input params: TransactionDetailsDTO ( id is ignored )
	 * {
			"id" : 44,
			"username": "abc",
			"transactionId": "12348",
			"timestamp": "2020-01-01T16:00:00.000Z",
			"transactionStatus": "FAILED"
		}
	 * Call example: https://localhost:8443/api/transactions/saveTransactionDetails
	 *
	 */
	@Transactional
	@PostMapping("/saveTransactionDetails")
	public ResponseEntity<?> saveTransactionDetails(@RequestBody TransactionDetailsDTO transactionDetailsDTO) {
		logger.debug("/api/transactions/saveTransactionDetails Started");
		if (transactionDetailsDTO == null) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("Transaction details not provided"), HttpStatus.BAD_REQUEST);
		}
		if (transactionDetailsDTO.getUsername() == null || transactionDetailsDTO.getUsername().isEmpty()) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("Username not provided"), HttpStatus.BAD_REQUEST);
		}
		if (transactionDetailsDTO.getTransactionId() == null || transactionDetailsDTO.getTransactionId().isEmpty()) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("Transaction id not provided"), HttpStatus.BAD_REQUEST);
		}
		if (transactionDetailsDTO.getTimestamp() == null) {
			transactionDetailsDTO.setTimestamp(new Date());
		}
		if (transactionDetailsDTO.getTransactionStatus() == null) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("Transaction status not provided"), HttpStatus.BAD_REQUEST);
		}
		// does it already exist?
		Optional<TransactionDetails> existingTransaction = transactionDetailsService.findByTransactionId(transactionDetailsDTO.getTransactionId());
		if (existingTransaction.isPresent()) {
			transactionDetailsDTO.setTransactionId(transactionDetailsDTO.getTransactionId() + "-" + transactionDetailsDTO.getTimestamp().getTime());
		}

		TransactionDetails transactionDetails = mapper.map(transactionDetailsDTO, TransactionDetails.class);
		transactionDetails.setId(null);

		transactionDetailsService.saveTransactionDetails(transactionDetails);
		logger.debug("/api/transactions/saveTransactionDetails Finished");
		return new ResponseEntity<>(commons.JSONfyReturnMessage("Transaction details added"), HttpStatus.OK);
	}

	/*
	 * Description: Delete transaction details
	 * Input params: notification id
	 * Call example: https://localhost:8443/api/transactions/deleteTransactionDetails?id=1
	 *
	 */
	@Transactional
	@GetMapping("/deleteTransactionDetails")
	public ResponseEntity<?> deleteTransactionDetails(@RequestParam(name = "id") Long id) {
		logger.debug("/api/transactions/deleteTransactionDetails Started");
		if (id == null || id <= 0) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("Invalid id provided"), HttpStatus.BAD_REQUEST);
		}
		Optional<TransactionDetails> optionalTransactionDetails = transactionDetailsService.findTransactionDetailsById(id);
		if (optionalTransactionDetails.isEmpty()) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("Transaction details not found"), HttpStatus.NOT_FOUND);
		}
		transactionDetailsService.deleteById(id);
		logger.debug("/api/transactions/deleteTransactionDetails Finished");
		return new ResponseEntity<>(commons.JSONfyReturnMessage("Transaction details deleted"), HttpStatus.OK);
	}

}
