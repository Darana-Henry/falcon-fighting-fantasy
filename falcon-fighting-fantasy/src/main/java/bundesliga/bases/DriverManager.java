package bundesliga.bases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import constants.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	public static void setDriver() {
		WebDriverManager.chromedriver().cachePath(Constants.DRIVER_STORAGE_LOCATION).setup();
		ChromeOptions cOptions = new ChromeOptions();
		// cOptions.setHeadless(true);
		driver.set(new ChromeDriver());
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void closeBrowser() {
		driver.get().close();
		driver.remove();
	}
}
