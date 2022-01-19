package it.univpm.twitAnalizer.service;

import org.json.JSONObject;

import it.univpm.twitAnalizer.model.TwitModel;
/*
 * @author Nicholas Bradach
 * @author Andrea Colonnini
 * 
 */
public interface TwitService {
	/*
	 * @return JSONObject from TwitterAPI
	 */
	public abstract JSONObject getTwit();
	/*
	 * Creates twitModel from getTwit()
	 */
	public abstract void modelCreator();
	/*
	 * Creates JSONObjects {"PlaceId":String,"Percentage":String,"Name":String}
	 */
	public abstract void statistics();
	/*
	 * @param TwitModel tweet
	 * Analyzes the param to fill the three vectors tempPlace. tempName, tempCounters
	 * These vectors are called in modelCreator
	 */
	public abstract void twitAnalyzer(TwitModel tweet);
	/*
	 * @return String from file statisticsJSON.txt 
	 */
	public abstract String loadFile();
	/*
	 * clear file statisticsJSON.txt
	 */
	public abstract void clearFile();
	/*
	 * @param String clock is a time identifier for statisticsJSON.txt
	 * prints on statisticsJSON.txt statsMap values
	 */
	public abstract void printFile(String clock);
	/*
	 * Calls modelCreator(), statistics() and printFile() every hour 
	 */
	public abstract void saveEveryHour();
	/*
	 * @return String from statsMap()
	 */
	public abstract String getStats();
	/*
	 * @param String name of the city wanted
	 * @return String of the city information from statsMap
	 */
	public abstract String searchName(String name);
}