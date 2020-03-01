package hr.go2.play.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Commons {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	private static final String HR_DATE_FORMAT = "dd/MM/YYYY";
	private static final String PSQL_DATE_FORMAT = "yyyy-MM-dd";
	private static final String VIDEO_DATE_FORMAT = "yyyyMMdd_hhmm";

	private static final Logger LOGGER = LoggerFactory.getLogger(Commons.class);

	public String JSONfyReturnMessage(String returnMsg) {
		StringBuffer msg = new StringBuffer();
		msg.append("{\"message\":\"");
		msg.append(returnMsg);
		msg.append("\"}");
		return msg.toString();
	}

	public Boolean isPasswordEncripted(String password) {
		if (password.startsWith("$2a") || password.startsWith("$2b") || password.startsWith("$2y")) {
			return true;
		} else
			return false;
	}

	public String encodePassword(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	public Date formatDateFromString(String date, String format) {
		if (format.equals("HR")) {
			format = HR_DATE_FORMAT;
		} else if (format.equals("PSQL")){
			format = PSQL_DATE_FORMAT;
		} else if (format.equals("VIDEO")) {
			format = VIDEO_DATE_FORMAT;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.parse(date);
		} catch (Exception ex) {
			LOGGER.error("Error parsing date: " + date + " with format: " + format);
		}
		return null;
	}
}
