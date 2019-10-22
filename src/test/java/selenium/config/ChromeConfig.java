package selenium.config;

public class ChromeConfig {
    public static final String DIR = (System.getProperty("user.dir"));
    public static final String DEFAULTURL = System.getProperty("baseUtl", "https://www.google.com"); //temporary example
    public static final String BROWSER = System.getProperty("browser", "chrome");
    public static final String BROWSERPATH = System.setProperty("webdriver.chrome.driver", DIR + "src/main/resources/drivers/chromedriver");
    public static final String HOST = System.getProperty("host", "localhost");
    public static final String BROWSERVERSION = System.getProperty("browserVersion", "77");
    public static final String PLATFORM = System.getProperty("platform", "CentOs 7");
    public static final String SAUCEUSER = System.getProperty("SAUCE_USERNAME");
    public static final String SAUCEPASS = System.getProperty("SAUCE_PASSWORD");
    public static final String SAUCEKEY = System.getProperty("SAUCE_KEY");
    public static final String BASEURL = "https";

}
