package hr.go2.play.util;

public class Commons {

	public String JSONfyReturnMessage(String returnMsg) {
		StringBuffer msg = new StringBuffer();
		msg.append("{\"message\":\"");
		msg.append(returnMsg);
		msg.append("\"}");
		return msg.toString();
	}
}