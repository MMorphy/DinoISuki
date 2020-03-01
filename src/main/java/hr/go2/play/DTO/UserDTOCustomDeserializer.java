package hr.go2.play.DTO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class UserDTOCustomDeserializer extends JsonDeserializer<UserDTO> {

	@Override
	public UserDTO deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);

		UserDTO userDTO = new UserDTO();

		if (node.get("createdAt") != null) {
			Date createdAt = createDate(node.get("createdAt").asText());
			userDTO.setCreatedAt(createdAt);
		} else {
			userDTO.setCreatedAt(new Date());
		}
		if (node.get("enabled") != null) {
			userDTO.setEnabled(Boolean.parseBoolean(node.get("enabled").asText()));
		} else {
			userDTO.setEnabled(Boolean.FALSE);
		}
		userDTO.setUsername(node.get("username").asText());
		if (node.get("password") != null) {
			userDTO.setPassword(node.get("password").asText());
		} else {
			userDTO.setPassword(null);
		}
		if (node.get("dateOfBirth") != null) {
			String dateOfBirthString = node.get("dateOfBirth").asText() + "T00:00:00.000Z";
			Date dateOfBirth = createDate(dateOfBirthString);
			userDTO.setDateOfBirth(dateOfBirth);
		} else {
			userDTO.setDateOfBirth(null);
		}
		return userDTO;
	}

	private Date createDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
