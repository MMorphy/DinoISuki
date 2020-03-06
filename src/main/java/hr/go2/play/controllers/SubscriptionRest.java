package hr.go2.play.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import hr.go2.play.DTO.SubscriptionDTO;
import hr.go2.play.DTO.SubscriptionTypeDTO;
import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.SubscriptionType;
import hr.go2.play.entities.User;
import hr.go2.play.services.SubscriptionService;
import hr.go2.play.services.SubscriptionTypeService;
import hr.go2.play.services.UserService;
import hr.go2.play.util.Commons;

@RestController
@RequestMapping(path = "/api/subscriptions")
public class SubscriptionRest {

	Logger logger = LoggerFactory.getLogger(getClass());

	ModelMapper mapper = new ModelMapper();

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private SubscriptionTypeService subscriptionTypeService;

	@Autowired
	private UserService userService;

	@Autowired
	private Commons commons;

	/*
	 * Description: Fetch all subscription types
	 * Call example:
	 * https://localhost:8443/api/subscriptions/getSubscriptionTypes
	 *
	 */
	@GetMapping("/getSubscriptionTypes")
	public ResponseEntity<?> getSubscriptionTypes() {
		logger.debug("/api/subscriptions/getSubscriptionTypes Started");

		List<SubscriptionType> subscriptionTypeList = subscriptionTypeService.findAllSubscriptionTypes();
		List<SubscriptionTypeDTO> subscriptionTypeDTOList = subscriptionTypeList.stream().map(subscriptionType -> mapper.map(subscriptionType, SubscriptionTypeDTO.class)).collect(Collectors.toList());

		logger.debug("/api/subscriptions/getSubscriptionTypes Finished");
		return new ResponseEntity<>(subscriptionTypeDTOList, HttpStatus.OK);
	}

	/*
	 * Description: Save subscription
	 * Input params: SubscriptionDTO ( id is ignored )
	 * {
			"valid": true,
			"validFrom": "2020-03-01T00:00:00.000Z",
			"validTo": "2020-03-10T00:00:00.000Z",
			"subscriptionTypeName": "DAILY",
			"username": "test6"
		}
	 * Call example: https://localhost:8443/api/subscriptions/saveSubscription
	 *
	 */
	@Transactional
	@PostMapping("/saveSubscription")
	public ResponseEntity<?> saveSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
		logger.debug("/api/subscriptions/saveSubscription Started");
		User user = null;
		if (subscriptionDTO.getUsername() == null || subscriptionDTO.getUsername().isEmpty()) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("Username not provided"), HttpStatus.BAD_REQUEST);
		} else {
			user = userService.findUserByUsername(subscriptionDTO.getUsername());
			if (user == null) {
				return new ResponseEntity<>(commons.JSONfyReturnMessage("User with username: '" + subscriptionDTO.getUsername() + "' not found"), HttpStatus.BAD_REQUEST);
			}
		}
		SubscriptionType subscriptionType = null;
		if (subscriptionDTO.getSubscriptionTypeName() == null || subscriptionDTO.getSubscriptionTypeName().isEmpty()) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("Subscription type not provided"), HttpStatus.BAD_REQUEST);
		} else {
			List<SubscriptionType> subscriptionTypeList = subscriptionTypeService.findSubscriptionTypeByName(subscriptionDTO.getSubscriptionTypeName());
			if (subscriptionTypeList.isEmpty()) {
				return new ResponseEntity<>(commons.JSONfyReturnMessage("Unrecognized subscription type provided"), HttpStatus.BAD_REQUEST);
			} else {
				// should be unique?
				subscriptionType = subscriptionTypeList.get(0);
			}
		}
		// get currently active subscription
		List<Subscription> activeSubscriptionList = userService.findByIdAndValidSubscription(user.getId(), Boolean.TRUE);
		Subscription longestLastingActiveSubscription = new Subscription();
		for (Subscription activeSubscription : activeSubscriptionList) {
			if (activeSubscription.getValidTo().after(new Date())) { // still active
				if (longestLastingActiveSubscription.getValidTo() == null || activeSubscription.getValidTo().after(longestLastingActiveSubscription.getValidTo())) {
					longestLastingActiveSubscription = activeSubscription;
				}
			}
		}

		if (subscriptionDTO.getValidFrom() == null || subscriptionDTO.getValidTo() == null) {
			SubscriptionValidityDates subscriptionValidityDates = SubscriptionValidityDates.calculateSubscriptionValidityDates(subscriptionDTO.getSubscriptionTypeName(), longestLastingActiveSubscription.getValidTo());
			if (subscriptionValidityDates.getValidFrom() == null || subscriptionValidityDates.getValidTo() == null) {
				return new ResponseEntity<>(commons.JSONfyReturnMessage("Unrecognized valid from and/or valid to dates provided"), HttpStatus.BAD_REQUEST);
			} else {
				subscriptionDTO.setValidFrom(subscriptionValidityDates.getValidFrom());
				subscriptionDTO.setValidTo(subscriptionValidityDates.getValidTo());
			}
		} else {
			if (subscriptionDTO.getValidFrom().after(subscriptionDTO.getValidTo())) {
				return new ResponseEntity<>(commons.JSONfyReturnMessage("Invalid valid from and/or to dates provided"), HttpStatus.BAD_REQUEST);
			} else {
				if (longestLastingActiveSubscription.getValidTo() != null) {
					long difference = subscriptionDTO.getValidTo().getTime() - subscriptionDTO.getValidFrom().getTime();
					subscriptionDTO.setValidFrom(longestLastingActiveSubscription.getValidTo());
					subscriptionDTO.setValidTo(new Date(longestLastingActiveSubscription.getValidTo().getTime() + difference));
				}
			}
		}

		Subscription subscription = mapper.map(subscriptionDTO, Subscription.class);
		subscription.setId(null);
		subscription.setSubscriptionType(subscriptionType);
		Subscription savedSubscription = subscriptionService.saveSubscription(subscription);
		Collection<Subscription> subscriptionList = user.getSubscriptions();
		if (subscriptionList != null) {
			subscriptionList.add(savedSubscription);
		} else {
			subscriptionList = new ArrayList<Subscription>();
			subscriptionList.add(savedSubscription);
		}
		user.setSubscriptions(subscriptionList);
		userService.saveUser(user);
		logger.debug("/api/subscriptions/saveSubscription Finished");
		return new ResponseEntity<>(commons.JSONfyReturnMessage("Subscription saved"), HttpStatus.OK);
	}

	/*
	 * Description: Fetch all active subscriptions for given user
	 * Call example: https://localhost:8443/api/subscriptions/getActiveSubscription?username=test6
	 *
	 */
	@GetMapping("/getActiveSubscription")
	public ResponseEntity<?> getActiveSubscription(@RequestParam(name = "username") String username) {
		logger.debug("/api/subscriptions/getActiveSubscription Started");
		if (username == null || username.isEmpty()) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("No username provided"), HttpStatus.BAD_REQUEST);
		}
		User user = userService.findUserByUsername(username);
		if (user == null) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("No user with username '" + username + "' found"), HttpStatus.BAD_REQUEST);
		}

		List<Subscription> activeSubscriptions = userService.findByIdAndValidSubscription(user.getId(), Boolean.TRUE);

		List<SubscriptionDTO> subscriptionsDTOList = activeSubscriptions.stream().map(subscription -> new SubscriptionDTO(subscription.getId(), subscription.isValid(), subscription.getValidFrom(), subscription.getValidTo(), subscription.getSubscriptionType().getName(), username)).collect(Collectors.toList());

		logger.debug("/api/subscriptions/getActiveSubscription Finished");
		return new ResponseEntity<>(subscriptionsDTOList, HttpStatus.OK);
	}

	/*
	 * Description: Fetch all active subscriptions for given user
	 * Call example: https://localhost:8443/api/subscriptions/getInactiveSubscription?username=test6
	 *
	 */
	@GetMapping("/getInactiveSubscription")
	public ResponseEntity<?> getInactiveSubscription(@RequestParam(name = "username") String username) {
		logger.debug("/api/subscriptions/getInactiveSubscription Started");
		if (username == null || username.isEmpty()) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("No username provided"), HttpStatus.BAD_REQUEST);
		}
		User user = userService.findUserByUsername(username);
		if (user == null) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("No user with username '" + username + "' found"), HttpStatus.BAD_REQUEST);
		}

		List<Subscription> inactiveSubscriptions = userService.findByIdAndValidSubscription(user.getId(), Boolean.FALSE);

		List<SubscriptionDTO> insubscriptionsDTOList = inactiveSubscriptions.stream().map(subscription -> new SubscriptionDTO(subscription.getId(), subscription.isValid(), subscription.getValidFrom(), subscription.getValidTo(), subscription.getSubscriptionType().getName(), username)).collect(Collectors.toList());

		logger.debug("/api/subscriptions/getInactiveSubscription Finished");
		return new ResponseEntity<>(insubscriptionsDTOList, HttpStatus.OK);
	}

	/*
	 * Description: Delete all active subscriptions for given user or single one by id
	 * Call example: https://localhost:8443/api/subscriptions/deleteSubscription?username=test6
	 *
	 */
	@GetMapping("/deleteSubscription")
	public ResponseEntity<?> deleteSubscription(@RequestParam(name = "username") String username, @RequestParam(name = "subscriptionId", required = false) Long subscriptionId) {
		logger.debug("/api/subscriptions/deleteSubscription Started");

		if (username == null || username.isEmpty()) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("No username provided"), HttpStatus.BAD_REQUEST);
		}
		User user = userService.findUserByUsername(username);
		if (user == null) {
			return new ResponseEntity<>(commons.JSONfyReturnMessage("No user with username '" + username + "' found"), HttpStatus.BAD_REQUEST);
		}

		List<Subscription> subscriptionList = new ArrayList<Subscription>();
		if (subscriptionId != null) {
			Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId);
			if (subscription == null) {
				return new ResponseEntity<>(commons.JSONfyReturnMessage("Provided subscription does not exist"), HttpStatus.BAD_REQUEST);
			}
			if (!user.getSubscriptions().contains(subscription)) {
				return new ResponseEntity<>(commons.JSONfyReturnMessage("Provided subscription does not belong to provided user"), HttpStatus.BAD_REQUEST);
			}
			subscriptionList.add(subscription);
		} else {
			subscriptionList = userService.findByIdAndValidSubscription(user.getId(), Boolean.TRUE);
		}

		user.getSubscriptions().removeAll(subscriptionList);
		userService.saveUser(user);
		for (Subscription subscription : subscriptionList) {
			subscriptionService.deleteSubscriptionById(subscription.getId());
		}

		logger.debug("/api/subscriptions/deleteSubscription Finished");
		return new ResponseEntity<>(commons.JSONfyReturnMessage("Subscription(s) deleted"), HttpStatus.OK);
	}

}

class SubscriptionValidityDates {
	private Date validFrom;
	private Date validTo;

	public SubscriptionValidityDates() {
	}

	public SubscriptionValidityDates(Date validFrom, Date validTo) {
		super();
		this.validFrom = validFrom;
		this.validTo = validTo;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	static public SubscriptionValidityDates calculateSubscriptionValidityDates(String subscriptionTypeName, Date from) {
		SubscriptionValidityDates subscriptionValidityDates = new SubscriptionValidityDates();
		Date dateFrom = new Date();
		if (from != null) {
			dateFrom = from;
		}
		subscriptionValidityDates.setValidFrom(dateFrom);
		Calendar c = Calendar.getInstance();
		c.setTime(dateFrom);
		switch (subscriptionTypeName) {
		case "DAILY":
			c.add(Calendar.DATE, 1);
			subscriptionValidityDates.setValidTo(c.getTime());
			break;
		case "WEEKLY":
			c.add(Calendar.DATE, 7);
			subscriptionValidityDates.setValidTo(c.getTime());
			break;
		case "MONTHLY":
			c.add(Calendar.MONTH, 1);
			subscriptionValidityDates.setValidTo(c.getTime());
			break;
		case "YEARLY":
			c.add(Calendar.YEAR, 1);
			subscriptionValidityDates.setValidTo(c.getTime());
			break;
		}
		return subscriptionValidityDates;
	}

}
