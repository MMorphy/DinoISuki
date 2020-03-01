package hr.go2.play.DTO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class UserDTOCustomDeserializer extends JsonDeserializer<UserDTO> {

	Logger logger = LoggerFactory.getLogger(UserDTOCustomDeserializer.class);

	@Override
	public UserDTO deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);

		UserDTO userDTO = new UserDTO();

		if (node.get("createdAt") != null) {
			Date createdAt;
			try {
				createdAt = createDate(node.get("createdAt").asText());
			} catch (ParseException e) {
				logger.warn("Invalid date format for createdAt: " + node.get("createdAt").asText() + " error:" + e.getMessage());
				createdAt = new Date((new Date()).getTime() + (1000 * 60 * 60 * 24));
			}
			userDTO.setCreatedAt(createdAt);
		} else {
			userDTO.setCreatedAt(new Date());
		}
		if (node.get("enabled") != null) {
			userDTO.setEnabled(Boolean.parseBoolean(node.get("enabled").asText()));
		} else {
			userDTO.setEnabled(Boolean.FALSE);
		}
		if (node.get("username") != null) {
			userDTO.setUsername(node.get("username").asText());
		} else {
			userDTO.setUsername(null);
		}
		if (node.get("password") != null) {
			userDTO.setPassword(node.get("password").asText());
		} else {
			userDTO.setPassword(null);
		}
		if (node.get("dateOfBirth") != null) {
			String dateOfBirthString = node.get("dateOfBirth").asText() + "T00:00:00.000Z";
			Date dateOfBirth;
			try {
				dateOfBirth = createDate(dateOfBirthString);
			} catch (ParseException e) {
				logger.warn("Invalid date format for dateOfBirthString: " + node.get("dateOfBirth").asText() + " error:" + e.getMessage());
				dateOfBirth = new Date((new Date()).getTime() + (1000 * 60 * 60 * 24));
			}
			userDTO.setDateOfBirth(dateOfBirth);
		} else {
			userDTO.setDateOfBirth(null);
		}
		return userDTO;
	}

	private Date createDate(String dateString) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		return format.parse(dateString);
	}

}
