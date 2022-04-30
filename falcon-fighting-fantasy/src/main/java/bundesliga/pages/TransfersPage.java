package bundesliga.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import bundesliga.bases.BasePage;
import bundesliga.notes.Notes;

public class TransfersPage extends BasePage {

	private By dropdownStatsSelector = By.xpath("//select[@data-select2-id='34']");
	private By dropdownTeamSelector = By.xpath("//select[@data-select2-id='25']");
	private By playerPageNavigator = By.xpath("//div[contains(@class, 'paginator__item')]");
	private By playerCount = By.xpath("//div[@class='players-list']//a[contains(@href,'fantasy')]");
	private By labelMyTeam = By.xpath("//a[contains(text(),'My Team')]");
	private By secondPageNavigator = By.xpath("(//div[@data-offset='25'])[2]");

	public TransfersPage(WebDriver driver) {
		super(driver);
	}

	public TransfersPage navigateToTransfersPage() {
		eWait.until(ExpectedConditions.elementToBeClickable(labelMyTeam));
		driver.get(Notes.TRANSFERS_PAGE);
		return this;
	}

	public TransfersPage selectSortByTotalPoints() {
		eWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownStatsSelector));
		Select select = new Select(driver.findElement(dropdownStatsSelector));
		select.selectByValue("total_points");
		return this;

	}

	public TransfersPage selectTeamByIndex(int i) {
		eWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownTeamSelector));
		Select select = new Select(driver.findElement(dropdownTeamSelector));
		select.selectByIndex(i);
		return this;
	}

	public String getTeamName() {
		eWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownTeamSelector));
		Select select = new Select(driver.findElement(dropdownTeamSelector));

		for (int p = 0; p < Notes.TEAM_ABBREVIATIONS.length; p++) {
			if (Notes.TEAM_ABBREVIATIONS[p][0].equals(select.getFirstSelectedOption().getText().trim())) {
				return Notes.TEAM_ABBREVIATIONS[p][1];
			}
		}

		return select.getFirstSelectedOption().getText().trim();
	}

	public int clickNextPageOnPlayerList() {
		return driver.findElements(playerPageNavigator).size();
	}

	public int getNumberofPagesOnPlayerList() {
		return driver.findElements(playerPageNavigator).size();
	}

	private int getPlayerCount() {
		return driver.findElements(playerCount).size();
	}

	private String getPlayerPosition(int i) {
		String playerPosition = "(//div[@class='transf__playerInList__clubAndPosSection']//span)[modifier]";
		By element = By.xpath(playerPosition.replaceAll("modifier", String.valueOf(i)));

		for (int p = 0; p < Notes.POSITON_ABBREVIATIONS.length; p++) {
			if (Notes.POSITON_ABBREVIATIONS[p][0].equals(driver.findElement(element).getText().trim())) {
				return Notes.POSITON_ABBREVIATIONS[p][1];
			}
		}

		return driver.findElement(element).getText().trim();
	}

	private String getPlayerName(int i) {
		String name = "(//div[@class='players-list']//a[contains(@href,'fantasy')])[modifier]";
		By element = By.xpath(name.replaceAll("modifier", String.valueOf(i)));
		return driver.findElement(element).getText().trim();
	}

	private String getPlayerValue(int i) {
		String value = "(//div[@class='transf__playerInList__prize'])[modifier]";
		By element = By.xpath(value.replaceAll("modifier", String.valueOf(i)));
		return driver.findElement(element).getText().trim();
	}

	private String getPlayerPopularity(int i) {
		String popularity = "(//div[@class='players-list']//div[@class='player-stats__inner'])[modifier]//tr[2]//td[5]";
		By element = By.xpath(popularity.replaceAll("modifier", String.valueOf(i)));
		return driver.findElement(element).getText().trim();
	}

	private String getPlayerLink(int i) {
		String link = "(//div[@class='players-list']//a[contains(@href,'fantasy')])[modifier]";
		return driver.findElement(By.xpath(link.replaceAll("modifier", String.valueOf(i)))).getAttribute("href");
	}

	private String getPlayerPoints(int i) {
		String points = "((//div[@class='neoBox__body__content'])[modifier]//div[contains(@class,'playerStat__row__headline')][2]//div[@class='playerStat__row__value'])";
		By element = By.xpath(points.replaceAll("modifier", String.valueOf(i)));
		return driver.findElement(element).getText().trim();
	}

	public void getPlayerHighLevelStats() {
		for (int p = 1; p <= clickNextPageOnPlayerList(); p++) {
			for (int i = 1; i <= getPlayerCount(); i++) {
				System.out.print(getPlayerPosition(i) + "\t");
				System.out.print(getPlayerName(i) + "\t");
				System.out.print(getTeamName() + "\t");
				System.out.print(getPlayerValue(i).replaceAll("M", "") + "\t");
				System.out.print(getPlayerPopularity(i) + "\t");
				PlayerPage playerPage = new PlayerPage(driver);
				playerPage.navigateToPlayerLink(getPlayerLink(i)).getPlayerLowLevelStats().closeOpenedTab();
				System.out.println();
			}
			if (clickNextPageOnPlayerList() > 1 && p < 2)
				driver.findElement(secondPageNavigator).click();
		}
	}
}
