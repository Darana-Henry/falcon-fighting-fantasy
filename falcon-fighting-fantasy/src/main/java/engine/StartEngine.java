package engine;

import bundesliga.runners.Runner;
import choices.EventChoices;
import choices.FixOrEData;
import choices.LeagueChoices;
import events.EventData;
import fixtures.Fixtures;

public class StartEngine {

	public static void main(String[] args) {

		EventChoices elementChoice = EventChoices.MINUTES;
		LeagueChoices leagueChoice = LeagueChoices.PREMIERLEAGUE;
		FixOrEData fixOrEData = FixOrEData.EDATA;

		switch (fixOrEData) {
		case EFIXTURES:
			Fixtures fixtures = new Fixtures();
			fixtures.getFixtures(leagueChoice);
			break;
		case EDATA:
			EventData eventData = new EventData();
			if (leagueChoice == LeagueChoices.BUNDESLIGA)
				(new Runner()).startRunning();
			else
				eventData.getEventData(elementChoice, leagueChoice);
			break;
		}
	}
}
