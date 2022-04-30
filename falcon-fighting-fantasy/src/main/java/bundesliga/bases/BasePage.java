package bundesliga.bases;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait eWait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		eWait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}
}
