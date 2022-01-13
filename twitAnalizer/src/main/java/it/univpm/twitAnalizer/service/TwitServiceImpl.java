package it.univpm.twitAnalizer.service;

import org.json.JSONObject;
import java.io.*;
import java.util.Scanner;

import it.univpm.twitAnalizer.model.TwitModel;

public class TwitServiceImpl implements TwitService{

	public JSONObject getTwit(TwitModel tweet) {
		JSONObject obj = new JSONObject();
		obj.put("placeID", tweet.getPlaceId());
		obj.put("created", tweet.getCreated());
		return obj;
	};

	public JSONObject statistics(JSONObject tweet) {
		JSONObject obj = new JSONObject();
		return obj;
	};

	public void twitAnalyzer(TwitModel tweet) {
		JSONObject temp = new JSONObject();
	};
	
	// saving/updating JSON objects on txt files
	public void stampaFile(JSONObject tweet) {
		try {			
			PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("statisticsJSON.txt")));
			file.println(tweet.toString());
			file.close();
		}

		catch(IOException e) {
			System.out.println("FOUND ERROR I/0");
			System.out.println(e);
		}
	}
}
// temp[{placeID:null,counter:270},{placeID:156f1afsdf86,counter:5}]
// statistiche{...+...}
//added on main branch