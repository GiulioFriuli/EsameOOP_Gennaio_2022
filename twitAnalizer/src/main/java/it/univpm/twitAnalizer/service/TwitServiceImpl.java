package it.univpm.twitAnalizer.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import it.univpm.twitAnalizer.model.DateModel;
import it.univpm.twitAnalizer.model.TwitModel;

/*
 * @author Nicholas Bradach
 * @author Andrea Colonnini
 */

@Service
public class TwitServiceImpl implements TwitService{
	/*
	 * String url = Authorized twitter api for q=#travel & count=100
	 * Vector tempCounters = vector of the counters of the various placeID
	 * Vector tempName = vector of the names of the various placeID
	 * Vector tempPlace = vector of the various placeID
	 * Map statsMap = map of the various statistics
	 * int h = hours passed on saveEveryHour
	 * int d = days passed on saveEveryHour
	 * ScheduledExecutorService s = import of the specific interface
	 * 
	 * 
	 * possible integers can be added for weeks, months and years and called in saveEveryHour
	 */
	private String url = new String("https://wd4hfxnxxa.execute-api.us-east-2.amazonaws.com/dev/api/1.1/search/tweets.json?q=%23travel&count=100");
	private Vector<Integer> tempCounters = new Vector<>();
	private Vector<String> tempName = new Vector<>();
	private Vector <String> tempPlace = new Vector<>();
	private Map<Integer, JSONObject> statsMap = new HashMap<>();
	private int h = 0;
	private int d = 0;
	private final ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
	
	/*
	 * As soon as TwitServiceImpl() is called, clearFile is called
	 */
	public TwitServiceImpl() {
		clearFile();
	}

	/*
	 * copies all the information from the api to a JSONObject
	 * called in modelCreator()
	 */
	@Override
	public JSONObject getTwit() {
		JSONObject obj;
		RestTemplate rt = new RestTemplate();
		obj = new JSONObject(rt.getForObject(url, String.class));
		return obj;
	}

	/*
	 * given JSONObjects from getTwit(), it generates TwitModel
	 * calls getTwit(), twitAnalizer()
	 */
	@Override
	public void modelCreator() {
		statsMap.clear();
		int anno, giorno, ora;
		String mese, data, id, name;
		JSONObject temp, temp2;
		JSONArray twitArray = getTwit().getJSONArray("statuses");

		for(int i=0; i<twitArray.length(); i++) {

			temp = twitArray.getJSONObject(i);

			if(temp.isNull("place")) {
				id = "null";
				name = "null";
			}
			else {
				temp2 = temp.getJSONObject("place");
				id = temp2.getString("id");
				name = temp2.getString("name");	
			}

			data = temp.getString("created_at");
			anno = Integer.parseInt(data.substring(26));
			mese = data.substring(4, 6);
			giorno = Integer.parseInt(data.substring(8, 9));
			ora = Integer.parseInt(data.substring(11, 12));
			DateModel dm = new DateModel(anno, mese, giorno, ora);
			TwitModel tm = new TwitModel(id, name, dm);

			twitAnalyzer(tm);
		}

	}

	/*
	 * calculates percentages based on the counters of tempCounters & updates statsMap
	 * uses tempPlace, tempName, tempCounters
	 */
	@Override
	public void statistics(){
		String per = new String();
		double tot = 0;
		for(int i=0; i<tempCounters.size(); i++){
			tot += tempCounters.get(i);
		}
		for(int i=0; i<tempCounters.size(); i++){
			JSONObject obj = new JSONObject();
			obj.put("Name", tempName.get(i));
			obj.put("PlaceId", tempPlace.get(i));
			per = (tempCounters.get(i)/tot)*100+"%";
			obj.put("Percentage", per);
			statsMap.put(statsMap.size(), obj);
		}
	}

	/*
	 * Updates tempPlace, tempName, tempCounters by using modelCreator()'s TwitModel
	 */
	@Override
	public void twitAnalyzer(TwitModel tweet) {
		boolean find = false;
		for(int i=0; i<tempPlace.size(); i++) {
			if(tweet.getPlaceId().equals(tempPlace.get(i))){
				tempCounters.set(i, tempCounters.get(i)+1);
				find = true;
			}
		}
		if(!find) {
			tempPlace.add(tweet.getPlaceId());
			tempName.add(tweet.getName());
			tempCounters.add(1);
		}
	}

	/*
	 * Used to update statisticsJSON.txt
	 * called in printFile()
	 */
	@Override
	public String loadFile() {
		String ret = new String();
		int x=0;
		char c;
		try {
			BufferedReader br = new BufferedReader(new FileReader("statisticsJSON.txt"));
			do {
				x = br.read();
				c = (char) x;
				ret += c;
			}while(x!=-1);
			br.close();
		}
		catch(IOException e) {
			System.out.println(e);
		}
		return ret;
	}

	@Override
	public void clearFile() {
		try {
			FileWriter file = new FileWriter("statisticsJSON.txt");
			file.write("");
			file.close();
		} catch(IOException e) {
			System.out.println(e);
		}
	}

	/*
	 * prints on statisticsJSON.txt when statistics is called and statistics()
	 * calls loadFile()
	 */
	@Override	
	public void printFile(String clock) {
		try {
			String s = new String(); 
			File f = new File("statisticsJSON.txt");
			if(f.exists()) {
				s = loadFile(); 
			}
			PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			CharSequence charClock = clock;
			CharSequence charValue = statsMap.values().toString()+"\n";
			s += charClock;
			s += charValue;
			file.println(s);
			file.close();
		}

		catch(IOException e) {			
			System.out.println("FOUND ERROR I/0");
			System.out.println(e);
		}
	}

	/*
	 * calls modelCreator, statistics(), printFile() periodically
	 */
	@Override
	public void saveEveryHour(){
		final Runnable saveEvHr = new Runnable() {
			public void run() { String clock = new String();

			modelCreator();
			statistics();

			h++;
			if(h == 24) {
				h = 0;
				d++;
			}
			if(h < 10) {
				clock = d + "d0" + h + "h:\n";
			}
			else {
				clock = d + "d" + h + "h:\n";
			}
			printFile(clock);
			System.out.println("Saved: "+clock);
			}
		};
		final ScheduledFuture<?> sHandle = s.scheduleAtFixedRate(saveEvHr,0,1,TimeUnit.HOURS);
		s.schedule(new Runnable() {
			public void run() {sHandle.cancel(true);}
		},24,TimeUnit.HOURS);
	}

	/*
	 * searches in statsMap for specific place information
	 */
	@Override
	public String searchName(String n) {
		String name = new String();
		name = "Name not found";
		for(int k = 0; k < statsMap.size(); k++) {
			if(statsMap.get(k).get("Name").equals(n)) name = statsMap.get(k).toString();			
		}
		return name;
	}

	/*
	 * shows all statistics information about all place found
	 */
	@Override
	public String getStats(){
		if(statsMap.isEmpty()) {
			String ret = new String();
			ret = "No information saved, send PULL request";
			return ret;
		}
		return statsMap.values().toString();
	}
}