package it.univpm.twitAnalizer.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Vector;

import it.univpm.twitAnalizer.model.DateModel;
import it.univpm.twitAnalizer.model.TwitModel;

@Service
public class TwitServiceImpl implements TwitService{
	private String url = new String("https://wd4hfxnxxa.execute-api.us-east-2.amazonaws.com/dev/api/1.1/search/tweets.json?q=%23travel&count=100");
	private Vector<TwitModel> twitVector = new Vector<>();
	
	@Override
	public JSONObject getTwit() {
		JSONObject obj;
		RestTemplate rt = new RestTemplate();
		obj = new JSONObject(rt.getForObject(url, String.class));
		return obj;
	}
	
	@Override
	public void fillVector() {
		int anno, giorno, ora;
		String mese, data, id;
		JSONObject temp;
		JSONArray twitArray = getTwit().getJSONArray("statuses");
		
		for(int i=0; i<twitArray.length(); i++) {
			temp = twitArray.getJSONObject(i);
			data = temp.getString("created_at");
			id = temp.getString("place_id");
			anno = Integer.parseInt(data.substring(26, 29));
			mese = data.substring(4, 6);
			giorno = Integer.parseInt(data.substring(8, 9));
			ora = Integer.parseInt(data.substring(11, 12));
			DateModel dm = new DateModel(anno, mese, giorno, ora);
			TwitModel tm = new TwitModel(id, dm);
			twitVector.add(tm);
		}
	}
	
	@Override
	public JSONObject statistics() {
		JSONObject obj = new JSONObject();
		return obj;
	}

	@Override
	public void twitAnalyzer(TwitModel tweet) {
		JSONObject temp = new JSONObject();
		
	}

	@Override	
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