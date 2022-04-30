package events;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import choices.EventChoices;
import choices.LeagueChoices;
import utils.AllsvenskanUtils;
import utils.EliteserienUtils;
import utils.GenericUtils;
import utils.PremierLeagueUtils;

public class EventData {

	@SuppressWarnings("incomplete-switch")
	public void getEventData(EventChoices elementChoice, LeagueChoices leagueChoice) {
		AllsvenskanUtils allsvenskanUtils = new AllsvenskanUtils();
		PremierLeagueUtils premierLeagueUtils = new PremierLeagueUtils();
		EliteserienUtils eliteserienUtils = new EliteserienUtils();
		GenericUtils genericUtils = new GenericUtils();
		String[] gameweekData = null;

		Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

		WebTarget targetFullStatic = client.target(genericUtils.getFullEndpoint(leagueChoice));
		Response responseFullStatic = targetFullStatic.request(MediaType.APPLICATION_JSON).get();
		String responseFullStaticS = responseFullStatic.readEntity(String.class);
		JsonParser parserFullStatic = new JsonParser();
		// System.out.println(responseFullStaticS);

		JsonObject playerList = parserFullStatic.parse(responseFullStaticS).getAsJsonObject();
		JsonArray listOfPlayers = playerList.getAsJsonArray("elements");

		for (JsonElement element : listOfPlayers) {
			JsonObject eachPlayer = element.getAsJsonObject();
			String firstName = eachPlayer.get("first_name").getAsString();
			String secondName = eachPlayer.get("second_name").getAsString();
			String id = eachPlayer.get("id").getAsString();
			String nowCost = eachPlayer.get("now_cost").getAsString();
			String teamCode = eachPlayer.get("team_code").getAsString();
			String elementType = eachPlayer.get("element_type").getAsString();
			String selectedBy = eachPlayer.get("selected_by_percent").getAsString();
			String fullName = firstName + " " + secondName;
			float currentPrice = Float.valueOf(nowCost) / 10;

			String position = null;
			String teamName = null;

			switch (leagueChoice) {
			case ALLSVENSKAN:
				position = allsvenskanUtils.getFullPosition(elementType);
				break;
			case ELITESERIEN:
				position = eliteserienUtils.getFullPosition(elementType);
				break;
			case PREMIERLEAGUE:
				position = premierLeagueUtils.getFullPosition(elementType);
				break;
			}

			switch (leagueChoice) {
			case ALLSVENSKAN:
				teamName = allsvenskanUtils.getTeamNameForEData(teamCode);
				break;
			case ELITESERIEN:
				teamName = eliteserienUtils.getTeamNameForEData(teamCode);
				break;
			case PREMIERLEAGUE:
				teamName = premierLeagueUtils.getTeamNameForEData(teamCode);
				break;
			}

			System.out.print(position + "\t" + fullName + "\t" + teamName + "\t" + currentPrice + "\t" + selectedBy);

			WebTarget target = client.target(genericUtils.getPlayerEndpoint(leagueChoice).replaceAll("playerId", id));
			Response response = target.request(MediaType.APPLICATION_JSON).get();
			String responseS = response.readEntity(String.class);
			// System.out.println(responseS);
			gameweekData = new String[38];

			try {
				JsonParser parser = new JsonParser();
				JsonObject player = parser.parse(responseS).getAsJsonObject();
				JsonArray playerHistory = player.getAsJsonArray("history");

				for (JsonElement elementData : playerHistory) {
					JsonObject eachGameweek = elementData.getAsJsonObject();
					String round = null;

					switch (elementChoice) {
					case POINTS:
						String totalPoints = eachGameweek.get("total_points").getAsString();
						round = eachGameweek.get("round").getAsString();
						if ((gameweekData[Integer.valueOf(round) - 1]) != null)
							gameweekData[Integer.valueOf(round) - 1] = String
									.valueOf(Integer.valueOf(gameweekData[Integer.valueOf(round) - 1])
											+ Integer.valueOf(totalPoints));
						else
							gameweekData[Integer.valueOf(round) - 1] = totalPoints;
						// System.out.print("\t" + totalPoints);
						break;
					case MINUTES:
						String totalminutes = eachGameweek.get("minutes").getAsString();
						round = eachGameweek.get("round").getAsString();
						if ((gameweekData[Integer.valueOf(round) - 1]) != null)
							gameweekData[Integer.valueOf(round) - 1] = String
									.valueOf(Integer.valueOf(gameweekData[Integer.valueOf(round) - 1])
											+ Integer.valueOf(totalminutes));
						else
							gameweekData[Integer.valueOf(round) - 1] = totalminutes;
						// System.out.print("\t" + totalminutes);
						break;
					default:
						break;
					}

				}
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				System.out.print("\t" + "--");
			}
			for (String data : gameweekData) {
				System.out.print("\t" + ((data == null) ? "" : data));
			}
			System.out.println();

		}
	}

}
