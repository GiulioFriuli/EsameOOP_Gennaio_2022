package it.univpm.twitAnalizer.model;

public class DateModel {
	private int anno;
	private String mese;
	private int giorno;
	private int ora;
	
	public DateModel(int anno, String mese, int giorno, int ora) {
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
		this.ora = ora;
	}
	
	public int getAnno() {
		return anno;
	}
	
	public String getMese() {
		return mese;
	}
	
	public int getGiorno() {
		return giorno;
	}
	
	public int getOra() {
		return ora;
	}

}
