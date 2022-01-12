package it.univpm.twitAnalizer.service;

import org.json.JSONObject;

import it.univpm.twitAnalizer.model.TwitModel;

public interface TwitService {
	public abstract JSONObject getTwit(TwitModel tweet);
	public abstract JSONObject statistics();
	public abstract void twitAnalyzer(TwitModel tweet);
}
// added on main branch