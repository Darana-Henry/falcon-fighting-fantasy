package bundesliga.pages;

import org.bouncycastle.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;

import bundesliga.bases.BasePage;

public class PlayerPage extends BasePage {

	private By boxEachMatchdayData = By.xpath("//div[@class='neoBox__body__content']//div[@class='neoBox__title ']");
	private By labelOverall = By.xpath("//span[contains(text(),'Overall')]");
	private By totalPoints = By.xpath("(//div[@class='playerStat-table']//td)[1]");
	private By hasThePageLoaded = By.xpath("//div[contains(@class,'playerStat__header__nickname')]");

	String getMatchday = "(//div[@class='neoBox__body__content']//div[@class='neoBox__title '])[modifier]";
	String originalWindow;
	WebDriver newTab;

	public PlayerPage(WebDriver driver) {
		super(driver);
	}

	public PlayerPage navigateToPlayerLink(String link) {
		originalWindow = driver.getWindowHandle();
		newTab = driver.switchTo().newWindow(WindowType.TAB);
		newTab.get(link);
		return this;
	}

	public PlayerPage closeOpenedTab() {
		newTab.close();
		driver.switchTo().window(originalWindow);
		return this;
	}

	public int getNumberOfMatchdayDataBoxes() {
		eWait.until(ExpectedConditions.visibilityOfElementLocated(labelOverall));
		return driver.findElements(boxEachMatchdayData).size();
	}

	public int getTotalPoints() {
		eWait.until(ExpectedConditions.visibilityOfElementLocated(hasThePageLoaded));
		return Integer.valueOf(driver.findElement(totalPoints).getAttribute("innerText").trim());
	}

	private int getMatchdayNum(int i) {
		String getMatchday = this.getMatchday;
		By element = By.xpath(getMatchday.replaceAll("modifier", String.valueOf(i)));
		return Integer.valueOf(driver.findElement(element).getText().trim().replaceAll("Matchday ", ""));

	}

	private PlayerPage navigateTotMatchdayNum(int i) {
		String getMatchday = this.getMatchday;
		By element = By.xpath(getMatchday.replaceAll("modifier", String.valueOf(i)));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(element));

		return this;
	}

	private PlayerPage openMatchdayBox(int i) {
		String matchdayOpenBox = "(//span[contains(@class,'playerStat__box__')])[modifier]//parent::span//parent::div//parent::div[@class='neoBox__box']";
		By element = By.xpath(matchdayOpenBox.replaceAll("modifier", String.valueOf(i)));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-100)");

		driver.findElement(element).click();
		return this;
	}

	private int getMatchdayPoints(int i) {
		String matchdayPoints = "((//div[@class='neoBox__body__content'])[modifier]//div[contains(@class,'playerStat__row__headline')][2]//div[@class='playerStat__row__value'])";
		By element = By.xpath(matchdayPoints.replaceAll("modifier", String.valueOf("1")));
		return Integer.valueOf(driver.findElement(element).getAttribute("innerText").trim());
	}

	private int getAnyDataPoints(int i) {
		String anyData = "((//div[@class='neoBox__body__content'])[1]//div[@class='neoBox__box'])[modifier]//div[@class='playerStat__row']";
		By element = By.xpath(anyData.replaceAll("modifier", String.valueOf(i)));
		return driver.findElements(element).size();
	}

	private PlayerPage scrollToTop() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
		return this;
	}

	private PlayerPage printPlayerLowLevelStats(int[] data) {
		for (int i : data)
			System.out.print(i + "\t");
		return this;
	}

	public PlayerPage getPlayerLowLevelStats() {
		int[] historicInfo = new int[33];
		Arrays.fill(historicInfo, 0);
		if (getTotalPoints() > 0) {
			for (int i = getNumberOfMatchdayDataBoxes(); i >= 3; i--) {
				navigateTotMatchdayNum(i).openMatchdayBox(i);

				if (getAnyDataPoints(i) > 0)
					historicInfo[getMatchdayNum(i) - 1] = getMatchdayPoints(i);
				else
					historicInfo[getMatchdayNum(i) - 1] = 0;
				scrollToTop();
			}
			printPlayerLowLevelStats(historicInfo);
		} else
			printPlayerLowLevelStats(historicInfo);
		return this;
	}
}
