package it.univpm.twitAnalizer.service;

import java.util.Collection;

import org.json.JSONObject;

import it.univpm.twitAnalizer.model.TwitModel;

public interface TwitService {
	public abstract void everyHour();
	public abstract JSONObject getTwit();
	public abstract void fillVector();
	public abstract void statistics();
	public abstract void twitAnalyzer(TwitModel tweet);
	public abstract void printFile(String clock);
	public abstract void saveEveryHour();
	public abstract Collection<JSONObject> getStats();
}
// added on main branch