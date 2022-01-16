package it.univpm.twitAnalizer.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.io.*;
import java.util.Vector;

import it.univpm.twitAnalizer.model.DateModel;
import it.univpm.twitAnalizer.model.TwitModel;

@Service
public class TwitServiceImpl implements TwitService{
	private String url = new String("https://wd4hfxnxxa.execute-api.us-east-2.amazonaws.com/dev/api/1.1/search/tweets.json?q=%23travel&count=100");
	private Vector<TwitModel> twitVector = new Vector<>();
	private Vector<Integer> counters = new Vector<>();
	private Vector <String> tempPlace = new Vector<>();
	// int for hours, days
	// weeks, months and years can be added here and applied in saveEveryHour
	private int h = 0;
	private int d = 0;

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

			if(temp.getString("place").equals(null)) {
				id = null;
			}
			else {
				id = temp.getString("place_id");
			}
			temp = twitArray.getJSONObject(i);
			
			if(temp.getString("place").equals(null)) {
				id = null;
			}
			else {
				id = temp.getString("place_id");
			}
			
			data = temp.getString("created_at");
			anno = Integer.parseInt(data.substring(26));
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
		String per = new String();
		int tot = 0;
		for(int k = 0; k < counters.size();k++) {
			obj.put("PlaceID", tempPlace.get(k));
			tot += counters.get(k);
		}
		for(int k = 0; k < counters.size();k++) {
			per = (counters.get(k)/tot)*100 + "%";
			obj.put("Percentage", per);
		}
		return obj;
	}

	@Override
	public JSONObject twitAnalyzer(TwitModel tweet) {
		JSONObject temp = new JSONObject();
		int i = 0;
		
		for(int k = 0; k < twitVector.size();k++) {
			if(temp.getString("PlaceID").equals(tweet.getPlaceId())) {
				if(!temp.get("Created").equals(tweet.getCreated())) {
					temp.put("PlaceID", tweet.getPlaceId());
					temp.put("Created", tweet.getCreated());
					while(temp.getString("PlaceID") != tempPlace.get(i)) {
						i++;
					}
					counters.set(i, counters.get(i)+1);
				}
			}
			if(!temp.getString("PlaceID").equals(tweet.getPlaceId())) {
				temp.put("PlaceID", tweet.getPlaceId());
				temp.put("Created", tweet.getCreated());
				tempPlace.add(tweet.getPlaceId());
				counters.add(1);
			}
		}
		return temp;
	}

	@Override	
	// saving/updating JSON objects on txt files
	public void printFile(String clock, JSONObject tweet) {
		try {			
			PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(clock + ".txt")));
			file.println(clock + " " + tweet.toString());
			file.close();
		}

		catch(IOException e) {
			System.out.println("FOUND ERROR I/0");
			System.out.println(e);
		}
	}
	
	@Override
	// saving every hour / ongoing statistics
	public void saveEveryHour(){
		ScheduledExecutorService sched = Executors.newSingleThreadScheduledExecutor();
		sched.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String clock = new String();
				h++;
				if(h == 24) {
					h = 0;
					d++;
				}
				clock = d + ":" + h;
				printFile(clock, statistics());
			}
		}, 0, 24, TimeUnit.HOURS);
	}
}
// data y - data x = 24h
// temp[{placeID:null,counter:270},{placeID:156f1afsdf86,counter:5}]
// statistiche{...+...}
//added on main branch



// 0g1h.txt => {"PlaceID":null,"Percentage":95}
// 1g0h.txt => {...}

/*
 *  file di testo
 *  
 *  000g:01h statistics()
 *  000g:02h statistics()
 *  ...
 *  001g:00h statistics()
 */
