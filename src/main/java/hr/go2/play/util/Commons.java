package hr.go2.play.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import hr.go2.play.entities.ApplicationProperties;
import hr.go2.play.services.ApplicationPropertiesService;

@Component
public class Commons {

	// variables from properties
	// video finder
	@Value("${application.job.video-finder.search-folder-location}")
	public String application_job_videoFinder_searchFolderLocation;
	@Value("${application.job.video-finder.found-folder-location}")
	public String application_job_videoFinder_foundFolderLocation;
	@Value("${application.job.video-finder.error-folder-location}")
	public String application_job_videoFinder_errorFolderLocation;
	@Value("${application.job.video-finder.archive-folder-location}")
	public String application_job_videoFinder_archiveFolderLocation;
	// move to archive
	@Value("${application.job.move-to-archive.ip}")
	public String application_job_moveToArchive_ip;
	@Value("${application.job.move-to-archive.username}")
	public String application_job_moveToArchive_username;
	@Value("${application.job.move-to-archive.password}")
	public String application_job_moveToArchive_password;
	@Value("${application.job.move-to-archive.remote-archive-folder}")
	public String application_job_moveToArchive_remoteArchiveFolder;
	@Value("${application.job.move-to-archive.time-to-archive-found-videos-in-days}")
	public int application_job_moveToArchive_timeToArchiveFoundVideosInDays;
	@Value("${application.job.move-to-archive.time-to-archive-archived-videos-in-days}")
	public int application_job_moveToArchive_timeToArchiveArchivedVideosInDays;
	// scheduler
	@Value("${application.job.video-finder.interval}")
	public int application_job_videoFinder_interval;
	@Value("${application.job.invalidate-subscription.interval}")
	public int application_job_invalidateSubscription_interval;
	@Value("${application.job.move-to-archive.interval}")
	public int application_job_moveToArchive_interval;
	// user management
	@Value("${application.users.profile-photo-location}")
	public String application_users_profilePhotoLocation;
	// admin
	@Value("${application.admin.uploaded-video-location}")
	public String application_admin_uploadedVideoLocation;

	Logger logger = LoggerFactory.getLogger(Commons.class);

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ApplicationPropertiesService applicationPropertiesService;

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T getProperty(String propertyKey, Class classType) {
		if (propertyKey == null || propertyKey.isEmpty() || classType == null) {
			return null;
		}
		Object returnValue = null;
		try {
			// fetch from DB
			Optional<ApplicationProperties> applicationProperty = applicationPropertiesService.findByKey(propertyKey);
			if (applicationProperty.isPresent()) {
				String valueString = applicationProperty.get().getValue();
				returnValue = valueString;
				return (T) classType.cast(valueString);
			}
			// fetch from properties
			Object localVariable = fetchLocalVariable(propertyKey);
			if (localVariable == null) {
				// give another try, but prepare the variable name first
				propertyKey = prepareKey(propertyKey);
				localVariable = fetchLocalVariable(propertyKey);
			}
			returnValue = localVariable;
			return (T) classType.cast(localVariable);
		} catch (ClassCastException e) {
			logger.warn("Unable to cast " + returnValue + " to " + classType.toString());
			e.printStackTrace();
		}
		return (T) returnValue;
	}

	private Object fetchLocalVariable(String propertyKey) {
		// dirty reflection
		try {
			Field field = Commons.class.getField(propertyKey);
			Object value = field.get(this);
			return value;
		} catch (NoSuchFieldException e) {
			logger.debug("Variable with key: " + propertyKey + " not found");
			e.printStackTrace();
		} catch (SecurityException e) {
			logger.warn("SecurityException when accessing variable with key: " + propertyKey);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String prepareKey(String inputKey) {
		inputKey = inputKey.replaceAll("\\.", "_");
		inputKey = inputKey.replaceAll("-", " ");
		// all letters after space to upper case
		inputKey = Pattern.compile("\\b(.)(.*?)\\b").matcher(inputKey).replaceAll(match -> match.group(1).toUpperCase() + match.group(2));
		// except first letter
		inputKey = Character.toLowerCase(inputKey.charAt(0)) + inputKey.substring(1);
		inputKey = inputKey.replaceAll(" ", "");
		return inputKey;
	}
}
