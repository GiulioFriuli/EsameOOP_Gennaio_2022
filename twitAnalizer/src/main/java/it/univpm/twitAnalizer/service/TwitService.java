package it.univpm.twitAnalizer.service;

import org.json.JSONObject;

import it.univpm.twitAnalizer.model.TwitModel;

public interface TwitService {
	public abstract JSONObject getTwit();
	public abstract void fillVector();
	public abstract JSONObject statistics();
	public abstract void twitAnalyzer(TwitModel tweet);
	public abstract void stampaFile(JSONObject tweet);
}
// added on main branch