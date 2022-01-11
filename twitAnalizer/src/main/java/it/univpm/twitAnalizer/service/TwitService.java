package it.univpm.twitAnalizer.service;

import com.fasterxml.jackson.databind.util.JSONPObject;

import it.univpm.twitAnalizer.model.TwitModel;

public interface TwitService {
	public abstract JSONPObject getTwit(TwitModel tweet);
	public abstract JSONPObject statistics();
}
