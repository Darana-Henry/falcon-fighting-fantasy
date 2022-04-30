package fixtures;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import choices.LeagueChoices;
import endpoints.FantasyConstants;
import utils.AllsvenskanUtils;
import utils.EliteserienUtils;
import utils.GenericUtils;
import utils.PremierLeagueUtils;

public class Fixtures {

	public void getFixtures(LeagueChoices leagueChoice) {

		GenericUtils genericUtils = new GenericUtils();
		String[] gameFixtures = null;

		Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

		WebTarget targetFullStatic = client.target(genericUtils.getFullEndpoint(leagueChoice));
		Response responseFullStatic = targetFullStatic.request(MediaType.APPLICATION_JSON).get();
		String responseFullStaticS = responseFullStatic.readEntity(String.class);
		// System.out.println(responseFullStaticS);

		JsonParser parserFullStatic = new JsonParser();
		JsonArray teams = parserFullStatic.parse(responseFullStaticS).getAsJsonObject().getAsJsonArray("teams");

		WebTarget target = client.target(genericUtils.getFixtureEndpoint(leagueChoice));
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		String responseF = response.readEntity(String.class);
		// System.out.println(responseF);
		// System.out.println(genericUtils.getFixtureEndpoint(leagueChoice));

		JsonParser parser = new JsonParser();
		JsonArray fixtures = parser.parse(responseF).getAsJsonArray();

		for (JsonElement element : teams) {

			gameFixtures = new String[40];

			JsonObject team = element.getAsJsonObject();
			String teamId = team.get("id").getAsString();

			String teamName = Fixtures.getTeamName(leagueChoice, String.valueOf(teamId));

			System.out.println();
			System.out.print(teamName + "\t");
			for (JsonElement elementFixture : fixtures) {

				JsonObject fixture = elementFixture.getAsJsonObject();

				String homeTeamId = fixture.get("team_h").getAsString();
				String awayTeamId = fixture.get("team_a").getAsString();

				String eventS = "";
				eventS = fixture.get("event") != JsonNull.INSTANCE ? fixture.get("event").getAsString() : null;

				if (eventS != null) {
					int event = Integer.valueOf(eventS);

					String homeTeamDifficulty;
					String awayTeamDifficulty;

					homeTeamDifficulty = (leagueChoice == LeagueChoices.PREMIERLEAGUE)
							? fixture.get("team_h_difficulty").getAsString()
							: (fixture.get("team_h_score") != JsonNull.INSTANCE
									? fixture.get("team_h_score").getAsString()
									: null);
					awayTeamDifficulty = (leagueChoice == LeagueChoices.PREMIERLEAGUE)
							? fixture.get("team_a_difficulty").getAsString()
							: (fixture.get("team_a_score") != JsonNull.INSTANCE
									? fixture.get("team_a_score").getAsString()
									: null);

					if (String.valueOf(teamId).equalsIgnoreCase(homeTeamId)
							|| String.valueOf(teamId).equalsIgnoreCase(awayTeamId)) {

						if (String.valueOf(teamId).equalsIgnoreCase(homeTeamId)) {
							String opponentTeam = Fixtures.getTeamName(leagueChoice, String.valueOf(awayTeamId));
							gameFixtures[event - 1] = (gameFixtures[event - 1] == null)
									? opponentTeam + " " + FantasyConstants.APPEND_HOME + " (" + homeTeamDifficulty
											+ ")"
									: gameFixtures[event - 1] + " / " + opponentTeam + " "
											+ FantasyConstants.APPEND_HOME + " (" + homeTeamDifficulty + ")";
							// System.out.print(opponentTeam + " " + FantasyConstants.APPEND_HOME + " (" +
							// homeTeamDifficulty + ")" + "\t");
						} else if (String.valueOf(teamId).equalsIgnoreCase(awayTeamId)) {
							String opponentTeam = Fixtures.getTeamName(leagueChoice, String.valueOf(homeTeamId));
							gameFixtures[event - 1] = (gameFixtures[event - 1] == null)
									? opponentTeam + " " + FantasyConstants.APPEND_AWAY + " (" + awayTeamDifficulty
											+ ")"
									: gameFixtures[event - 1] + " / " + opponentTeam + " "
											+ FantasyConstants.APPEND_AWAY + " (" + awayTeamDifficulty + ")";
							// System.out.print(opponentTeam + " " + FantasyConstants.APPEND_AWAY + " (" +
							// awayTeamDifficulty+ ")" + "\t");
						}
					}
				}
			}

			for (String gameF : gameFixtures) {
				System.out.print((gameF == null) ? "" + "\t" : gameF + "\t");

			}
		}
	}

	public static String getTeamName(LeagueChoices leagueChoice, String teamId) {

		AllsvenskanUtils allsvenskanUtils = new AllsvenskanUtils();
		PremierLeagueUtils premierLeagueUtils = new PremierLeagueUtils();
		EliteserienUtils eliteserienUtils = new EliteserienUtils();

		switch (leagueChoice) {
		case ALLSVENSKAN:
			return allsvenskanUtils.getTeamNameForFixtures(String.valueOf(teamId));
		case ELITESERIEN:
			return eliteserienUtils.getTeamNameForFixtures(String.valueOf(teamId));
		case PREMIERLEAGUE:
			return premierLeagueUtils.getTeamNameForFixtures(String.valueOf(teamId));
		}
		return null;
	}
}
