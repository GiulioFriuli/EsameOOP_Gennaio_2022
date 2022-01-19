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

@Service
public class TwitServiceImpl implements TwitService{
	private String url = new String("https://wd4hfxnxxa.execute-api.us-east-2.amazonaws.com/dev/api/1.1/search/tweets.json?q=%23travel&count=100");
	private Vector<Integer> counters = new Vector<>();
	private Vector<String> tempName = new Vector<>();
	private Vector <String> tempPlace = new Vector<>();
	private Map<Integer, JSONObject> statsMap = new HashMap<>();
	// int for hours, days
	// weeks, months and years can be added here and applied in saveEveryHour
	private int h = 0;
	private int d = 0;
	
	public TwitServiceImpl() {
		clearFile();
	}
	
	private final ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
	@Override
	public void everyHour() {
		final Runnable sr = new Runnable() {
			public void run() { System.out.println("Test");}
	};
	final ScheduledFuture<?> sHandle = s.scheduleAtFixedRate(sr,0,1,TimeUnit.SECONDS);
	s.schedule(new Runnable() {
		public void run() {sHandle.cancel(true);}
		},5,TimeUnit.SECONDS);
	}

	@Override
	public JSONObject getTwit() {
		System.out.println("getTwit");
		JSONObject obj;
		RestTemplate rt = new RestTemplate();
		obj = new JSONObject(rt.getForObject(url, String.class));
		System.out.println("Test");
		return obj;
	}

	@Override
	public void fillVector() {
		statsMap.clear();
		System.out.println("fillVector");
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
			System.out.println("Test");
		}
		
	}
	
    @Override
	public void statistics(){
		System.out.println("statistics");
		String per = new String();
		double tot = 0;
		for(int i=0; i<counters.size(); i++){
			tot += counters.get(i);
		}
		for(int i=0; i<counters.size(); i++){
			JSONObject obj = new JSONObject();
			obj.put("Name", tempName.get(i));
			obj.put("PlaceId", tempPlace.get(i));
			per = (counters.get(i)/tot)*100+"%";
			obj.put("Percentage", per);
			statsMap.put(statsMap.size(), obj);
		}
		System.out.println("Test");
	}
	   
 
	@Override
	public void twitAnalyzer(TwitModel tweet) {
		System.out.println("twitAnalyzer");
		boolean find = false;
		for(int i=0; i<tempPlace.size(); i++) {
			if(tweet.getPlaceId().equals(tempPlace.get(i))){
				counters.set(i, counters.get(i)+1);
				find = true;
			}
		}
		if(!find) {
			tempPlace.add(tweet.getPlaceId());
			tempName.add(tweet.getName());
			counters.add(1);
		}
		System.out.println("Test");
	}
	
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

	@Override	
	// saving/updating JSON objects on txt files
	public void printFile(String clock) {
		try {
			String s = new String(); 
			System.out.println("try printFile");
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
			System.out.println("Test");
		}

		catch(IOException e) {			
			System.out.println("FOUND ERROR I/0");
			System.out.println(e);
		}
	}
	
	@Override
	// saving every hour / ongoing statistics
	public void saveEveryHour(){
		System.out.println("saveEveryHour");
		final Runnable saveEvHr = new Runnable() {
			public void run() { String clock = new String();
			
			fillVector();
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
			System.out.println("Saved");
			}
	};
	final ScheduledFuture<?> sHandle = s.scheduleAtFixedRate(saveEvHr,0,1,TimeUnit.MINUTES);
	s.schedule(new Runnable() {
		public void run() {sHandle.cancel(true);}
		},1,TimeUnit.MINUTES);
	}

/*
 * 
 */
	
	@Override
	public String getStats(){
		return statsMap.values().toString();
	}
}