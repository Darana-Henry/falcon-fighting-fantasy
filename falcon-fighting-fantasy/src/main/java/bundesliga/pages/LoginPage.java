package bundesliga.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import bundesliga.bases.BasePage;
import bundesliga.notes.Notes;

public class LoginPage extends BasePage {

	private By btnLogin = By.xpath("//a[@href='/okta/login']");
	private By txtEmail = By.xpath("//input[@type='email']");
	private By txtPassword = By.xpath("//input[@type='password']");
	private By btnSubmit = By.xpath("//button[@type='submit']");
	private By btnAcceptCookies = By.xpath("//button[contains(text(),'Accept All Cookies')]");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public LoginPage navigateToLoginPage() {
		driver.get(Notes.LOGIN_PAGE);
		return this;
	}

	public LoginPage acceptCookies() {
		eWait.until(ExpectedConditions.elementToBeClickable(btnAcceptCookies));
		driver.findElement(btnAcceptCookies).click();
		return this;
	}

	public LoginPage login(String email, String password) {
		eWait.until(ExpectedConditions.elementToBeClickable(btnLogin));
		driver.findElement(btnLogin).click();

		eWait.until(ExpectedConditions.visibilityOfElementLocated(btnSubmit));
		driver.findElement(txtEmail).sendKeys(email);
		driver.findElement(txtPassword).sendKeys(password);
		driver.findElement(btnSubmit).click();
		return this;
	}
}
