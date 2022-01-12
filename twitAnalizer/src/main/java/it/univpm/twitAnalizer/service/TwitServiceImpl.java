package it.univpm.twitAnalizer.service;

import org.json.JSONObject;

import it.univpm.twitAnalizer.model.TwitModel;

public class TwitServiceImpl implements TwitService{
	
	public JSONObject getTwit(TwitModel tweet) {
		JSONObject obj = new JSONObject();
		obj.put("placeID", tweet.getPlaceId());
		obj.put("created", tweet.getCreated());
		return obj;
	};
	
	public JSONObject statistics() {
		JSONObject obj = new JSONObject();
		return obj;
	};

	public void twitAnalyzer(TwitModel tweet) {
		JSONObject temp = new JSONObject();
		
	};
}
// temp[{placeID:null,counter:270},{placeID:156f1afsdf86,counter:5}]
// statistiche{...+...}