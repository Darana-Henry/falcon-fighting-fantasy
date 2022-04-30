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

import choices.LeagueChoices;
import endpoints.FantasyEndpoints;

public class GenericUtils {

	public String getFullEndpoint(LeagueChoices leagueChoice) {

		switch (leagueChoice) {
		case ALLSVENSKAN:
			return FantasyEndpoints.ALLSVENSKAN_FULL_ENDPOINT;
		case PREMIERLEAGUE:
			return FantasyEndpoints.PREMIERLEAGUE_FULL_ENDPOINT;
		case ELITESERIEN:
			return FantasyEndpoints.ELITESERIEN_FULL_ENDPOINT;
		}
		return null;
	}

	public String getPlayerEndpoint(LeagueChoices leagueChoice) {

		switch (leagueChoice) {
		case ALLSVENSKAN:
			return FantasyEndpoints.ALLSVENSKAN_PLAYER_ENDPOINT;
		case PREMIERLEAGUE:
			return FantasyEndpoints.PREMIERLEAGUE_PLAYER_ENDPOINT;
		case ELITESERIEN:
			return FantasyEndpoints.ELITESERIEN_PLAYER_ENDPOINT;
		}
		return null;
	}

	public String getFixtureEndpoint(LeagueChoices leagueChoice) {

		switch (leagueChoice) {
		case ALLSVENSKAN:
			return FantasyEndpoints.ALLSVENSKAN_FIXTURE_ENDPOINT;
		case PREMIERLEAGUE:
			return FantasyEndpoints.PREMIERLEAGUE_FIXTURE_ENDPOINT;
		case ELITESERIEN:
			return FantasyEndpoints.ELITESERIEN_FIXTURE_ENDPOINT;
		}
		return null;
	}

}
