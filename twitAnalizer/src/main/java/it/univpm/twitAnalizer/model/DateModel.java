package it.univpm.twitAnalizer.model;

/*
 * this class describes tweet's date information
 * @author Nicholas Bradach
 * @author Andrea Colonnini
 */
public class DateModel {
	private int anno;
	private String mese;
	private int giorno;
	private int ora;
	
	/*
	 * Object's builder
	 * @param anno is the year of tweet creation
	 * @param mese is the month of tweet creation
	 * @param giorno is the day of tweet creation
	 * @param ora is the hour of tweet creation
	 */
	public DateModel(int anno, String mese, int giorno, int ora) {
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
		this.ora = ora;
	}
	
	/*
	 * year's getter
	 * @return anno
	 */
	public int getAnno() {
		return anno;
	}
	
	/*
	 * month's getter
	 * @return mese
	 */
	public String getMese() {
		return mese;
	}
	
	/*
	 * day's getter
	 * @return giorno
	 */
	public int getGiorno() {
		return giorno;
	}
	
	/*
	 * hour's getter
	 * @return ora
	 */
	public int getOra() {
		return ora;
	}

}
