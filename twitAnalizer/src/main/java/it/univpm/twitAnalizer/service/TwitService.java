package it.univpm.twitAnalizer.service;

import org.json.JSONObject;

import it.univpm.twitAnalizer.model.TwitModel;

public interface TwitService {
	public abstract JSONObject getTwit();
	public abstract void fillVector();
	public abstract JSONObject statistics();
	public abstract JSONObject twitAnalyzer(TwitModel tweet);
	public abstract void printFile(String clock, JSONObject tweet);
	public abstract void saveEveryHour();
}
// added on main branch