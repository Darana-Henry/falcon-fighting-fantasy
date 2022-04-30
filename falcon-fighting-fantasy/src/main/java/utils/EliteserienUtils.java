package utils;

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

import endpoints.FantasyEndpoints;

public class EliteserienUtils {

	public String getFullPosition(String elementType) {

		switch (elementType) {
		case "1":
			return "Goalkeepers";
		case "2":
			return "Defenders";
		case "3":
			return "Midfielders";
		case "4":
			return "Forwards";
		}
		return elementType;
	}

	public String getTeamNameForFixtures(String teamCode) {

		Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
		WebTarget target = client.target(FantasyEndpoints.ELITESERIEN_FULL_ENDPOINT);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		String responseS = response.readEntity(String.class);
		// System.out.println(response);

		JsonParser parser = new JsonParser();
		JsonArray teams = parser.parse(responseS).getAsJsonObject().getAsJsonArray("teams");
		;

		for (JsonElement element : teams) {
			JsonObject team = element.getAsJsonObject();
			String teamId = team.get("id").getAsString();
			String name = team.get("name").getAsString();

			if (teamCode.equalsIgnoreCase(teamId))
				return name;
		}

		return teamCode;
	}

	public String getTeamNameForEData(String teamCode) {

		Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
		WebTarget target = client.target(FantasyEndpoints.ELITESERIEN_FULL_ENDPOINT);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		String responseS = response.readEntity(String.class);
		// System.out.println(response);

		JsonParser parser = new JsonParser();
		JsonArray teams = parser.parse(responseS).getAsJsonObject().getAsJsonArray("teams");
		;

		for (JsonElement element : teams) {
			JsonObject team = element.getAsJsonObject();
			String teamId = team.get("code").getAsString();
			String name = team.get("name").getAsString();

			if (teamCode.equalsIgnoreCase(teamId))
				return name;
		}
		return responseS;
	}

}
