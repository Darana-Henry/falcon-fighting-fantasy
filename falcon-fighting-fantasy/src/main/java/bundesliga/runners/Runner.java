package bundesliga.runners;

import org.openqa.selenium.WebDriver;
import bundesliga.bases.DriverManager;
import bundesliga.pages.LoginPage;
import bundesliga.pages.TransfersPage;

public class Runner {

	public void startRunning() {

		DriverManager.setDriver();
		WebDriver driver = DriverManager.getDriver();
		driver.manage().window().maximize();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.navigateToLoginPage().acceptCookies().login("", "");

		TransfersPage transfersPage = new TransfersPage(driver);
		transfersPage.navigateToTransfersPage().selectSortByTotalPoints();

		for (int i = 13; i < 19; i++) {
			transfersPage.selectTeamByIndex(i);
			try {
				Thread.sleep(7000);
			} catch (Exception e) {
			}
			transfersPage.getPlayerHighLevelStats();
		}

		DriverManager.closeBrowser();

	}

}
