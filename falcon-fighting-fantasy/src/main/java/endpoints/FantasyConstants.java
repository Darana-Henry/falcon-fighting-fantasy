package endpoints;

public class FantasyConstants {

	public static final String BASE_URL = "https://understat.com/";
	public static final String LEAGUE_URL = "https://understat.com/league/leagueName/year";
	public static final String PLAYER_URL = "https://understat.com/player/{}";
	public static final String TEAM_URL = "https://understat.com/team/{}/{}";
	public static final String MATCH_URL = "https://understat.com/match/matchId/";
	public static final String APPEND_HOME = "(H)";
	public static final String APPEND_AWAY = "(A)";
	public static final String SEASON_2019_20 = "20192020";
	public static final String SEASON_2020_21 = "20202021";
	public static final String ENGLISH_PREMEIR_LEAGUE = "England Premier League";

	public static final String EPL = "EPL";
	public static final String BPL = "Bundesliga";
	public static final String LIGUE_1 = "Ligue_1";

	public static String LEAGUE = "";

	public static final String FULL_DATA_PREMIER_LEAGUE_ENDPOINT = "https://fantasy.premierleague.com/api/bootstrap-static/";
	public static final String FULL_DATA_ELITESERIEN_ENDPOINT = "https://en.fantasy.eliteserien.no/api/bootstrap-static/";
	public static final String EACH_GAMEWEEK_PREMIER_LEAGUE_ENDPOINT = "https://fantasy.premierleague.com/api/event/{GW}/live/";
	public static final String EACH_GAMEWEEK_ELITESERIEN_ENDPOINT = "https://en.fantasy.eliteserien.no/api/event/{GW}/live/";
	public static final String PLAYER_PREMIER_LEAGUE_ENDPOINT = "https://fantasy.premierleague.com/api/element-summary/{playerId}/";
	public static final String PLAYER_ELITESERIEN_ENDPOINT = "https://en.fantasy.eliteserien.no/api/element-summary/playerId/";

	public static final String underStatEndpoint = "https://understat.com/league/EPL/2019/data/2019-20/understat";
	public static final String statsPremierLeague = "https://www.premierleague.com/stats/top/players/ontarget_scoring_att";

}
